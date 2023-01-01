package com.icia.friend.store;

import static com.icia.friend.remote.MenuRemoteService.BASE_URL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.icia.friend.remote.MenuRemoteService;
import com.icia.friend.review.ReviewFragment;
import com.icia.friend.vo.MenuVO;
import com.icia.friend.vo.StoreVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StorePagerAdapter extends FragmentPagerAdapter {
    int count_tab; //탭수
    StoreVO vo;
    List<MenuVO> array;
    List<HashMap<String, Object>> array2;

    public StorePagerAdapter(FragmentManager fm, int tabs, StoreVO vo, List<MenuVO> array, List<HashMap<String, Object>> array2) {
        super(fm);
        this.count_tab=tabs;
        this.vo=vo;
        this.array=array;
        this.array2 = array2;
        System.out.println("StorePagerAdapter - getItem :: array(0): " + vo);
        System.out.println("StorePagerAdapter - ReviewAdapter : " + array2);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //정보 fragment에 vo전송
        Bundle bundle = new Bundle();
        bundle.putSerializable("StoreVO", vo);
        switch (position){
            case 0 :
//                System.out.println("StorePagerAdapter - getItem :: array(0): " + array.get(0));
                Fragment MenuFragment=new MenuFragment(array);
                return MenuFragment;

            case 1:
                InfoFragment info= new InfoFragment();
                info.setArguments(bundle);
                return info;

            case 2:
                ReviewFragment review= new ReviewFragment(array2);
                review.setArguments(bundle);
                return review;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count_tab;
    }
}
