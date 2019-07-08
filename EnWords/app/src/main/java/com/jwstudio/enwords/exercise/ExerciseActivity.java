package com.jwstudio.enwords.exercise;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.exercise.presenter.ListeningTrainingPresenter;
import com.jwstudio.enwords.exercise.view.IListeningTrainingView;
import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.utils.Constant;

public class ExerciseActivity extends AppCompatActivity implements IListeningTrainingView {

    private FragmentManager fm;

    // 听力训练模块
    private static final int EXERCISE_TYPE_LISTEN_TRAIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        fm = getSupportFragmentManager();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int type = bundle.getInt("type");

        if (type == EXERCISE_TYPE_LISTEN_TRAIN) {
            ListeningTrainingPresenter listeningTrainingPresenter = new ListeningTrainingPresenter(this, fm);
            listeningTrainingPresenter.load();
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            int backStackEntryCount = fm.getBackStackEntryCount();
            if (backStackEntryCount > 1) {
                // 出栈
                fm.popBackStackImmediate();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

        return true;
    }
}
