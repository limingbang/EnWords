package com.jwstudio.enwords.exercise.presenter;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;

import com.jwstudio.enwords.exercise.adapter.ListeningTrainingRVAdapter;
import com.jwstudio.enwords.exercise.bean.ArticleInfo;
import com.jwstudio.enwords.exercise.fragment.ListeningTrainingShowFragment;
import com.jwstudio.enwords.exercise.mode.IListeningTraining;
import com.jwstudio.enwords.exercise.mode.ListeningTrainingModel;
import com.jwstudio.enwords.exercise.view.IListeningTrainingView;

import java.util.List;

/**
 * 听力训练模块-Presenter
 */
public class ListeningTrainingPresenter {

    private IListeningTrainingView listeningTrainingView;
    private IListeningTraining listeningTraining;
    private FragmentManager fm;

    private ListeningTrainingShowFragment fragment;
    private ListeningTrainingRVAdapter adapter;

    private MyHandler myHandler = new MyHandler();

    public ListeningTrainingPresenter(IListeningTrainingView listeningTrainingView, FragmentManager fm) {
        this.listeningTrainingView = listeningTrainingView;
        this.fm = fm;
        listeningTraining = new ListeningTrainingModel(myHandler);

        fragment = new ListeningTrainingShowFragment();
    }

    public void load() {
        listeningTraining.loadArticlePath(); // 初始化数据
        listeningTrainingView.replaceFragment(fragment);
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ArticleInfo info = (ArticleInfo) msg.obj;
                    adapter.setAdapterData(info); // 更新数据
                    break;
                case 1:
                    // 该分支先一步执行
                    List<String> resPath = (List<String>) msg.obj;
                    listeningTraining.loadArticleInfo(resPath);
                    adapter = new ListeningTrainingRVAdapter(listeningTraining, listeningTrainingView, resPath);
                    fragment.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    }

}
