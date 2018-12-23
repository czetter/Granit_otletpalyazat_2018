package com.example.czettergbor.nagyhf_mobweb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.czettergbor.nagyhf_mobweb.Fragments.FragmentSpendingTracker;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapterMenu  extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentNames = new ArrayList<>();

    public FragmentAdapterMenu(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment f,String title){
        fragments.add(f);
        fragmentNames.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
