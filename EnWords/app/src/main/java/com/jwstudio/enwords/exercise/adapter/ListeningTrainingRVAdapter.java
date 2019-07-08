package com.jwstudio.enwords.exercise.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.exercise.bean.ArticleInfo;
import com.jwstudio.enwords.exercise.fragment.ListeningTrainingContentFragment;
import com.jwstudio.enwords.exercise.mode.IListeningTraining;
import com.jwstudio.enwords.exercise.view.IListeningTrainingView;
import com.jwstudio.enwords.study.bean.WordsResFileInfo;
import com.jwstudio.enwords.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ListeningTrainingRVAdapter extends RecyclerView.Adapter<ListeningTrainingRVAdapter.ViewHolder> {

    private IListeningTraining listeningTraining;
    private IListeningTrainingView listeningTrainingView;
    private List<ArticleInfo> infos;
    private List<String> paths;
    private ListeningTrainingContentFragment fragment;

    public ListeningTrainingRVAdapter(IListeningTraining listeningTraining, IListeningTrainingView listeningTrainingView, List<String> paths) {
        this.listeningTraining = listeningTraining;
        this.listeningTrainingView = listeningTrainingView;
        this.paths = paths;
        infos = new ArrayList<>();

        fragment = new ListeningTrainingContentFragment();
    }

    public void setAdapterData(ArticleInfo info) {
        infos.add(info);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_listen_train_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(infos.get(i).getArticle());

        final String articleUrl = Constant.RESOURCE_URL_ARTICLE + paths.get(i) + "/" + infos.get(i).getName();
        final String musicUrl = Constant.RESOURCE_URL_ARTICLE + paths.get(i) + "/" + infos.get(i).getMusic();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.setInfo(listeningTraining, articleUrl, musicUrl);
                listeningTrainingView.replaceFragment(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_article_title);
        }
    }

}
