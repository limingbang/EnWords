package com.jwstudio.enwords.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jwstudio.enwords.home.fragment.ExerciseFragment;
import com.jwstudio.enwords.home.fragment.HomeFragment;
import com.jwstudio.enwords.home.fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        fragments.add(new HomeFragment());
        fragments.add(new ExerciseFragment());
        fragments.add(new PersonalFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
