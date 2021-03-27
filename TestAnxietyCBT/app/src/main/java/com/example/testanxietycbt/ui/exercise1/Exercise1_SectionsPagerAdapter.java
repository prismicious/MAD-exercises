package com.example.testanxietycbt.ui.exercise1;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.testanxietycbt.Frag2;
import com.example.testanxietycbt.Frag3;
import com.example.testanxietycbt.Frag4;
import com.example.testanxietycbt.ui.exercise1.fragments.Frag5;
import com.example.testanxietycbt.R;
import com.example.testanxietycbt.ui.exercise1.fragments.Frag6;
import com.example.testanxietycbt.ui.exercise1.fragments.Frag7;
import com.example.testanxietycbt.ui.exercise1.fragments.Frag8;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class Exercise1_SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_3};
    private final Context mContext;

    public Exercise1_SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;

       switch (position){
           case 0:
               fragment = new Frag5();
               break;
           case 1:
               fragment = new Frag6();
               break;
           case 2:
               fragment = new Frag7();
               break;
           case 3:
               fragment = new Frag8();
       }
       return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}