package com.icia.friend.customerService;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                return new Fragment_tab1();

            case 1:
                return new Fragment_tab2();

            case 2:
                return new Fragment_tab3();

            case 3:
                return new Fragment_tab4();

            case 4:
                return new Fragment_tab5();

            case 5:
                return new Fragment_tab6();

            default:
                assert false;
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "회원가입";
        } else if (position == 1) {
            title = "바로결제";
        } else if (position == 2) {
            title = "리뷰관리";
        } else if (position == 3) {
            title = "이용문의";
        } else if (position == 4) {
            title = "불편문의";
        } else if (position == 5) {
            title = "결제";
        }
        return title;
    }

}