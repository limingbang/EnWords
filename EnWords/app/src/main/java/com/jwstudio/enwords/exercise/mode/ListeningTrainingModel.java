package com.jwstudio.enwords.exercise.mode;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jwstudio.enwords.exercise.bean.ArticleInfo;
import com.jwstudio.enwords.utils.Constant;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeningTrainingModel implements IListeningTraining {

    private Handler mHandler;
    private MediaPlayer mediaPlayer;

    public ListeningTrainingModel(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void loadArticlePath() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Constant.RESOURCE_URL + "ArticleServlet")
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

                    // // 初步数据加载完成，通知RecycleView进行相应的初始化
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = resPath;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    @Override
    public void loadArticleInfo(List<String> articlePaths) {
        List<String> metaPath = getReadPath(articlePaths);
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
                        ArticleInfo info = parseJson(mateJson);
                        Log.d(Constant.TAG, "ArticleInfo:" + info.toString());

                        // 每解析完一项数据就发送一次，通知RecycleView更新数据
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = info;
                        mHandler.sendMessage(message);
                    }
                }
            });

        }
    }

    @Override
    public void playMusic(String url) {
        mediaPlayer = getMediaPlayerInstance();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopMusic() {
        getMediaPlayerInstance().stop();
    }

    @Override
    public void pauseMusic() {
        getMediaPlayerInstance().pause();
    }

    @Override
    public void release() {
        getMediaPlayerInstance().release();
    }

    private MediaPlayer getMediaPlayerInstance() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        return mediaPlayer;
    }

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

    // read.json的路径
    private List<String> getReadPath(List<String> paths) {
        List<String> path = new ArrayList<>();
        List<String> t = paths;
        for (int j = 0; j < t.size(); j++) {
            String k = Constant.RESOURCE_URL_ARTICLE + t.get(j) + "/read.json";
            path.add(k);
        }
        return path;
    }

    // 解析read.json文件
    private ArticleInfo parseJson(String json) {
        ArticleInfo info = new ArticleInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            info.setName(jsonObject.getString("name"));
            info.setMusic(jsonObject.getString("music"));
            info.setArticle(jsonObject.getString("article"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
}
