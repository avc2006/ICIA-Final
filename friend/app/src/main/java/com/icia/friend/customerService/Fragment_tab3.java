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

public class Fragment_tab3 extends Fragment {

    private Context context;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        return inflater.inflate(R.layout.fragment_tab3, container, false);
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
        listDataParent.add("리뷰 작성은 어떻게 하나요?");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n주문 내역에서 리뷰를 작성하고자 하는 주문 건 하단의 [리뷰 쓰기] 버튼을 클릭하면 리뷰를 작성할 수 있습니다.\n" +
                "작성한 리뷰는 [리뷰관리] 메뉴에서 확인할 수 있습니다.\n" +
                "\n" +
                "● 리뷰 작성 조건\n" +
                "\n" +
                "1. 리뷰 작성은 배달의민족 회원에게 한하여 가능하며, 비회원은 작성이 불가합니다. \n" +
                "2. 배달완료 후 3일 이내에 작성이 가능하며, 주문번호 기준으로 1개의 리뷰 작성이 가능합니다.\n" +
                "3. 주문이 취소된 경우 리뷰 작성이 불가합니다. \n" +
                "4. 전화주문 이용 시에는 리뷰 작성이 불가합니다.\n");

        listDataChild.put(listDataParent.get(0), Vegetables);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }

}
