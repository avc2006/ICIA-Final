package com.icia.friend.customerService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.icia.friend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_tab6 extends Fragment {

    private Context context;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        return inflater.inflate(R.layout.fragment_tab6, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expListView = (ExpandableListView) view.findViewById(R.id.expListView);

        createListData();

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO GroupClickListener work
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO GroupExpandListener work
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                // TODO GroupCollapseListener work
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(context,
                        "wow, this is - " + listDataChild.get(listDataParent.get(groupPosition)).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void createListData() {
        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataParent.add("음식에서 이물질이 나왔는데 고객안심센터로 연락하면 되나요?");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n네, 맞습니다. 고객안심센터는 배달의민족을 이용하며 발생된 가게와 관련된 불편사항을 접수하여 문제를 해결해드립니다.\n" +
                "\n" +
                "\n" +
                "또한, 고객안심센터 및 배달의민족 고객센터로 직접 접수된 이물 신고 중 개인정보(이름, 연락처 등) 제공에 동의하실 경우 식품의약품안전처로 전달됩니다. \n" +
                "이물 통보 접수 후 조사결과는 최대 15일 이내 관할 지자체에서 고객님께 직접 회신해 드립니다.\n" +
                "\n\n");

        listDataChild.put(listDataParent.get(0), Vegetables);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }

}
