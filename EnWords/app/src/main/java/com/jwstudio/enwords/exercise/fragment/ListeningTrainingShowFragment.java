package com.jwstudio.enwords.exercise.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.exercise.adapter.ListeningTrainingRVAdapter;

public class ListeningTrainingShowFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListeningTrainingRVAdapter adapter;
    private Activity mActivity;

    // 记录RecycleView是否是第一次加载数据
    private boolean isFirst = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_train_show, container, false);
        recyclerView = view.findViewById(R.id.rv_listen_train);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity,DividerItemDecoration.VERTICAL));

        if (isFirst) {
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    public void setAdapter(ListeningTrainingRVAdapter adapter) {
        this.adapter = adapter;
        isFirst = true;
        recyclerView.setAdapter(adapter);
    }

}
