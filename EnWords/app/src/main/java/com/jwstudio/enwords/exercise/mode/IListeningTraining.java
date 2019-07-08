package com.jwstudio.enwords.exercise.mode;

import java.util.List;

public interface IListeningTraining {

    // 加载听力材料的路径
    void loadArticlePath();

    // 加载听力材料的内容
    void loadArticleInfo(List<String> articlePaths);

    void playMusic(String url);

    void stopMusic();

    void pauseMusic();

    void release();

}
