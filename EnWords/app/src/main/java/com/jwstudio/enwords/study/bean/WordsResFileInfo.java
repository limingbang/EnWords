package com.jwstudio.enwords.study.bean;

/**
 * 记录RecycleView中item的数据
 */
public class WordsResFileInfo {
    private String word;
    private String wordAudio;
    private String imageFile;
    private String accent;
    private String meanCn;
    private String sentence;
    private String sentenceTrans;
    private String sentenceAudio;

    public WordsResFileInfo() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(String wordAudio) {
        this.wordAudio = wordAudio;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getAccent() {
        return accent;
    }

    public void setAccent(String accent) {
        this.accent = accent;
    }

    public String getMeanCn() {
        return meanCn;
    }

    public void setMeanCn(String meanCn) {
        this.meanCn = meanCn;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentenceTrans() {
        return sentenceTrans;
    }

    public void setSentenceTrans(String sentenceTrans) {
        this.sentenceTrans = sentenceTrans;
    }

    public String getSentenceAudio() {
        return sentenceAudio;
    }

    public void setSentenceAudio(String sentenceAudio) {
        this.sentenceAudio = sentenceAudio;
    }

    @Override
    public String toString() {
        return "WordsResFileInfo{" +
                "word='" + word + '\'' +
                ", wordAudio='" + wordAudio + '\'' +
                ", imageFile='" + imageFile + '\'' +
                ", accent='" + accent + '\'' +
                ", meanCn='" + meanCn + '\'' +
                ", sentence='" + sentence + '\'' +
                ", sentenceTrans='" + sentenceTrans + '\'' +
                ", sentenceAudio='" + sentenceAudio + '\'' +
                '}';
    }
}
