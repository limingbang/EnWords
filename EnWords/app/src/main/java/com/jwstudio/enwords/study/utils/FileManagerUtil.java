package com.jwstudio.enwords.study.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;


import com.jwstudio.enwords.R;
import com.jwstudio.enwords.study.bean.WordsResFileInfo;
import com.jwstudio.enwords.utils.Constant;
import com.jwstudio.enwords.utils.RecordInfoState;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用于读取assets目录下的资源文件
 */
public class FileManagerUtil {

    private Context context;
    private List<WordsResFileInfo> infos = new ArrayList<>();
    private Handler handler;

    public FileManagerUtil(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    // 首次请求单词学习的资源
    public void setUrlInfo(String type) {
        FormBody.Builder body = new FormBody.Builder();
        body.add("type", type);
        body.add("plan", RecordInfoState.getPlan(context));
        body.add("name", RecordInfoState.getAccount(context));

        temp(body);
    }

    // 根据打卡日期请求单词学习资源
    public void setUrlInfo(String type, String date) {
        FormBody.Builder body = new FormBody.Builder();
        body.add("type", type);
        body.add("name", RecordInfoState.getAccount(context));
        body.add("date", date);

        temp(body);
    }

    private void temp(FormBody.Builder body) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Constant.RESOURCE_URL + "ResPathServlet")
                .post(body.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String pathJson = response.body().string();
                    List<String> resPath = parsePathJson(pathJson);

                    // 初步数据加载完成，通知RecycleView进行相应的初始化
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = resPath;
                    handler.sendMessage(message);
                }
            }
        });
    }

    // 解析资源路径
    private List<String> parsePathJson(String pathJson) {
        List<String> paths = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(pathJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    paths.add(jsonObject.getString("path"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return paths;
    }

    // meta.json的路径
    private List<String> getMetaPath(List<String> paths) {
        List<String> path = new ArrayList<>();
        List<String> t = paths;
        for (int j = 0; j < t.size(); j++) {
            String k = Constant.RESOURCE_URL_WORDS + t.get(j) + "/meta.json";
            path.add(k);
        }
        return path;
    }

    // 解析meta.json文件
    private WordsResFileInfo parseJson(String json) {
        WordsResFileInfo info = new WordsResFileInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            info.setWord(jsonObject.getString("word"));
            info.setWordAudio(jsonObject.getString("word_audio"));
            info.setImageFile(jsonObject.getString("image_file"));
            info.setAccent(jsonObject.getString("accent"));
            info.setMeanCn(jsonObject.getString("mean_cn"));
            info.setSentence(jsonObject.getString("sentence"));
            info.setSentenceAudio(jsonObject.getString("sentence_audio"));
            info.setSentenceTrans(jsonObject.getString("sentence_trans"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    // 获取图片资源
    public void loadImageFile(String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .error(R.drawable.load_error)
                .into(imageView);
    }

    // 播放音频
    public void player(String url) {
        Log.d(Constant.TAG, "url:" + url);
        MediaPlayer myMediaPlayer = new MediaPlayer();
        try {
            myMediaPlayer.setDataSource(url);
            myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            myMediaPlayer.prepareAsync();

            myMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFilesInfo(List<String> paths) {
        List<String> metaPath = getMetaPath(paths);
        OkHttpClient client = new OkHttpClient();
        for (int i = 0; i < metaPath.size(); i++) {
            Request request = new Request.Builder()
                    .url(metaPath.get(i))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String mateJson = response.body().string();
                        WordsResFileInfo info = parseJson(mateJson);
                        Log.d(Constant.TAG, "info:" + info.toString());

                        // 每解析完一项数据就发送一次，通知RecycleView更新数据
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = info;
                        handler.sendMessage(message);
                    }
                }
            });

        }
    }

    public List<WordsResFileInfo> getInfos() {
        return infos;
    }

}
