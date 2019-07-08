package com.jwstudio.enwords.study;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.study.adapter.StudyRecycleViewAdapter;
import com.jwstudio.enwords.study.bean.WordsResFileInfo;
import com.jwstudio.enwords.study.utils.FileManagerUtil;
import com.jwstudio.enwords.study.view.MyRecycleView;
import com.jwstudio.enwords.study.view.PagingScrollHelper;
import com.jwstudio.enwords.utils.Constant;
import com.jwstudio.enwords.utils.RecordInfoState;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudyActivity extends AppCompatActivity implements View.OnClickListener {

    private MyRecycleView recycleView;
    private MaterialButton btnPre;
    private MaterialButton btnNext;
    private MaterialButton btnFinish;

    private StudyRecycleViewAdapter adapter;
    private FileManagerUtil fileManagerUtil;
    private PagingScrollHelper scrollHelper;
    private List<WordsResFileInfo> infos;

    private int corrent = 0; // 记录item的位置

    private MyHandler handler;
    private int type;
    private Bundle bundle;
    private String punchCardDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        init();

        Intent intent = getIntent();
        bundle = intent.getExtras();
        type = bundle.getInt("type");

        if (type == 0) {
            fileManagerUtil.setUrlInfo("1"); // 必须先执行该条语句，从服务器加载数据
        }

        if (type == 1) {
            fileManagerUtil.setUrlInfo("3", punchCardDate); // 必须先执行该条语句，从服务器加载数据
        }
    }

    private void init() {
        handler = new MyHandler();
        infos = new ArrayList<>();

        recycleView = findViewById(R.id.rv_study_content);
        btnPre = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnFinish = findViewById(R.id.btn_finish);

        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        scrollHelper = new PagingScrollHelper();
        fileManagerUtil = new FileManagerUtil(this, handler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(layoutManager);

        // 给RecycleView设置滑动工具类
        scrollHelper.setUpRecycleView(recycleView);
    }

    @Override
    public void onClick(View v) {
        Log.d(Constant.TAG, "infos.size:" + infos.size());
        switch (v.getId()) {
            case R.id.btn_previous:
                corrent--;
                if (corrent >= 0) {
                    scrollHelper.scrollToPosition(corrent);
                } else {
                    corrent = 0;
                    scrollHelper.scrollToPosition(0);
                }

                if (corrent == infos.size() - 2) {
                    btnFinish.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_next:
                corrent++;
                if (corrent < infos.size()) {
                    scrollHelper.scrollToPosition(corrent);
                } else {
                    corrent = infos.size() - 1;
                }

                if (corrent == infos.size() - 1) {
                    btnNext.setVisibility(View.INVISIBLE);
                    btnFinish.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_finish:
                List<String> sign = new ArrayList<>();
                Map<String, ?> dateMap = RecordInfoState.getPunchCard(this);
                if (!dateMap.isEmpty()) {
                    for (Map.Entry<String, ?> entry : dateMap.entrySet()) {
                        sign.add(entry.getKey());
                    }
                }

                // 如果该日期没打卡，则保存到服务器
                if (!sign.contains(punchCardDate)) {
                    punchCard();
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void punchCard() {
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder body = new FormBody.Builder();
        body.add("type", "2");
        body.add("name", RecordInfoState.getAccount(this));

        Request request = new Request.Builder()
                .url(Constant.RESOURCE_URL + "ResPathServlet")
                .post(body.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String success = response.body().string();
                    if ("success".equals(success)) {
                        Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String d = format.format(date);
                        RecordInfoState.recordPunchCard(StudyActivity.this, d);

                        startActivity(new Intent(StudyActivity.this, MainActivity.class));
                        StudyActivity.this.finish();
                    }
                }
            }
        });
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    WordsResFileInfo info = (WordsResFileInfo) msg.obj;
                    infos.add(info);

                    // 不断刷新数据
                    adapter.setAdapterData(info);
                    break;
                case 1:
                    // 该分支先一步执行，且只执行一次
                    List<String> resPath = (List<String>) msg.obj;
                    fileManagerUtil.getFilesInfo(resPath);
                    adapter = new StudyRecycleViewAdapter(resPath,fileManagerUtil);
                    recycleView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    }

}
