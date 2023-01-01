package com.icia.friend.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.icia.friend.R;
import com.icia.friend.vo.StoreVO;

import java.util.HashMap;
import java.util.List;

public class ReviewFragment extends Fragment implements View.OnClickListener {

    StoreVO vo = new StoreVO();

    Context context;
    ImageButton add_Review;

    List<HashMap<String, Object>> array2;

    public ReviewFragment(List<HashMap<String, Object>> array2){
        this.array2 = array2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        RecyclerView list = view.findViewById(R.id.review_list);
        list.setLayoutManager(new LinearLayoutManager(context));

        context = container.getContext();

        Review_Adapter review_adapter = new Review_Adapter(getContext(), array2);
        review_adapter.notifyDataSetChanged();
        list.setAdapter(review_adapter);

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_review, container, false);
        StoreVO vo = (StoreVO) this.getArguments().getSerializable("StoreVO");
        System.out.println("ReviewFragment - onCreateView : " + vo);
        add_Review = viewGroup.findViewById(R.id.add_Review);

        add_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                intent.putExtra("StoreVO", vo);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.add_Review:
                Intent intent = new Intent(getActivity(),ReviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

}