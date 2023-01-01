package com.icia.friend.store;

import static com.icia.friend.remote.MenuRemoteService.BASE_URL;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icia.friend.R;
import com.icia.friend.remote.MenuRemoteService;
import com.icia.friend.remote.StoreRemoteService;
import com.icia.friend.vo.MenuVO;
import com.icia.friend.vo.StoreVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    List<MenuVO> array;

    public MenuFragment(List<MenuVO> array) {
        this.array = array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        RecyclerView list = view.findViewById(R.id.menu_list);
        list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        System.out.println("MenuFragment - onCreateVIew : " + array);

        Menu_Adapter menu_adapter = new Menu_Adapter(getContext(), array);
        menu_adapter.notifyDataSetChanged();

        list.setAdapter(menu_adapter);

        return view;
    }

}