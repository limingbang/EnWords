package com.jwstudio.enwords.study.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.study.bean.WordsResFileInfo;
import com.jwstudio.enwords.study.utils.FileManagerUtil;
import com.jwstudio.enwords.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * StudyActivity中MyRecycleView的适配器
 */
public class StudyRecycleViewAdapter extends RecyclerView.Adapter<StudyRecycleViewAdapter.ViewHolder> {

    private List<WordsResFileInfo> infos;
    private List<String> paths;
    private FileManagerUtil fileManagerUtil;
    private WordsResFileInfo fileInfo;
    private int p;

    public StudyRecycleViewAdapter(List<String> paths, FileManagerUtil fileManagerUtil) {
        this.fileManagerUtil = fileManagerUtil;
        infos = new ArrayList<>();
        this.paths = paths;
    }

    // 更新数据
    public void setAdapterData(WordsResFileInfo info) {
        infos.add(info);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_study_item,
                parent, false);
        ViewHolder holer = new ViewHolder(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        p = position;
        fileInfo = infos.get(position);
        holder.tvWord.setText(fileInfo.getWord());
        holder.tvAccent.setText(fileInfo.getAccent());
        holder.tvMeanCn.setText(fileInfo.getMeanCn());
        holder.tvSentence.setText(fileInfo.getSentence());
        holder.tvSentenceTrans.setText(fileInfo.getSentenceTrans());

        // 设置图片
        fileManagerUtil.loadImageFile(Constant.RESOURCE_URL_WORDS
                        + paths.get(position) + "/" + fileInfo.getImageFile(),
                holder.ivImageFile);


        // 播放单词音频
        holder.ibWordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG,"单词音频");
                fileManagerUtil.player(Constant.RESOURCE_URL_WORDS + paths.get(p) + "/" + fileInfo.getWordAudio());
            }
        });

        // 播放语句音频
        holder.ibSentenceAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG, "语句音频");
                fileManagerUtil.player(Constant.RESOURCE_URL_WORDS + paths.get(p) + "/" + fileInfo.getSentenceAudio());
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvWord; // 单词
        TextView tvAccent; // 单词音标
        TextView tvMeanCn; // 单词的中文意思
        TextView tvSentence; // 例句
        TextView tvSentenceTrans; // 例句中文的意思
        ImageButton ibWordAudio; // 单词的音频
        ImageButton ibSentenceAudio; // 例句的音频
        ImageView ivImageFile; // 单词对应的图片

        public ViewHolder(View itemView) {
            super(itemView);
            tvWord = itemView.findViewById(R.id.tv_word);
            tvAccent = itemView.findViewById(R.id.tv_accent);
            tvMeanCn = itemView.findViewById(R.id.tv_mean_cn);
            tvSentence = itemView.findViewById(R.id.tv_sentence);
            tvSentenceTrans = itemView.findViewById(R.id.tv_sentence_trans);
            ibWordAudio = itemView.findViewById(R.id.ib_word_audio);
            ibSentenceAudio = itemView.findViewById(R.id.ib_sentence_audio);
            ivImageFile = itemView.findViewById(R.id.iv_image_file);
        }
    }

}
