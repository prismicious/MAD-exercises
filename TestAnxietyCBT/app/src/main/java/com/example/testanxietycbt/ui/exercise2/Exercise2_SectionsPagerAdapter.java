package com.example.testanxietycbt.ui.exercise2;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.testanxietycbt.R;
import com.example.testanxietycbt.ui.exercise2.fragments.Frag10;
import com.example.testanxietycbt.ui.exercise2.fragments.Frag11;
import com.example.testanxietycbt.ui.exercise2.fragments.Frag12;
import com.example.testanxietycbt.ui.exercise2.fragments.Frag9;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class Exercise2_SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_3};
    private final Context mContext;

    public Exercise2_SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;

       switch (position){
           case 0:
               fragment = new Frag9();
               break;
           case 1:
               fragment = new Frag10();
               break;
           case 2:
               fragment = new Frag11();
               break;
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
        return 3;
    }
}