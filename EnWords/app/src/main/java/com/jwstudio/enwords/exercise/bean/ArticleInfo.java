package com.jwstudio.enwords.exercise.bean;

public class ArticleInfo {

    private String name;
    private String music;
    private String article;

    public ArticleInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "name='" + name + '\'' +
                ", music='" + music + '\'' +
                ", article='" + article + '\'' +
                '}';
    }
}
