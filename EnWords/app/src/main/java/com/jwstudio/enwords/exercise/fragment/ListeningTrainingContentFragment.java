package com.jwstudio.enwords.exercise.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jwstudio.enwords.R;

import com.jwstudio.enwords.exercise.mode.IListeningTraining;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeningTrainingContentFragment extends Fragment implements View.OnClickListener {

    private TextView tvArticleContent;
    private ImageButton ibPlay;
    private ImageButton ibPause;
    private ImageButton ibStop;
    private ImageButton ibReturn;

    private Activity mActivity;

    private IListeningTraining listeningTraining;
    private String articleUrl;
    private String musicUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_train_content, container, false);
        init(view);
        return view;
    }

    public void setInfo(IListeningTraining listeningTraining, String articleUrl, String musicUrl) {
        this.listeningTraining = listeningTraining;
        this.articleUrl = articleUrl;
        this.musicUrl = musicUrl;

        loadArticleContent();
    }

    private void init(View view) {
        tvArticleContent = view.findViewById(R.id.tv_article_content);
        ibPlay = view.findViewById(R.id.ib_play);
        ibPause = view.findViewById(R.id.ib_pause);
        ibStop = view.findViewById(R.id.ib_stop);
        ibReturn = view.findViewById(R.id.ib_return);

        ibPlay.setOnClickListener(this);
        ibPause.setOnClickListener(this);
        ibStop.setOnClickListener(this);
        ibReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_play:
                listeningTraining.playMusic(musicUrl);
                break;
            case R.id.ib_pause:
                listeningTraining.pauseMusic();
                break;
            case R.id.ib_stop:
                listeningTraining.stopMusic();
                break;
            case R.id.ib_return:
                break;
            default:
                break;
        }
    }

    private void loadArticleContent() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(articleUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String content = response.body().string();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvArticleContent.setText(content);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listeningTraining.stopMusic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listeningTraining.stopMusic();
    }
}
