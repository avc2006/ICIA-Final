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

public class Fragment_tab5 extends Fragment {

    private Context context;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        return inflater.inflate(R.layout.fragment_tab5, container, false);
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
        listDataParent.add("불편문의");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n\n" +
                "안녕하세요 고객안심센터입니다\n" +
                "\n" +
                "배달의민족 안심하고 이용하세요.\n" +
                "고객 안심 센터는 배달의민족을 이용하면서 가게와의 갈등이나 부당한 대우로 불편을 겪은 고객님의 문제를 해결해 드립니다.\n" +
                "부정·불량식품은 1399로 신고 가능합니다.\n" +
                "\n" +
                "주문했던 가게에서\n" +
                "연락이 왔어요\n" +
                "음식 배달을 위해 가게로 전달된 주문정보는 개인정보보호법에 따라 배달완료 후 파기하는 것이 원칙입니다. 배달완료 후 전화, 문자, 메신저 등 연락이 지속된다면 고객 안심 센터로 신고해 주세요.\n" +
                "\n" +
                "저의 주소와 연락처가\n" +
                "공개되어 있어요\n" +
                "개인정보보호법에 따라 개인정보를 목적 외의 용도로 이용하거나 이를 제 3자에게 제공하여서는 안 됩니다. 고객님의 연락처나 주소 등 개인정보가 일부라도 리뷰에 노출되었을 시 즉시 고객 안심 센터로 신고해 주세요.\n" +
                "\n" +
                "가게와의\n" +
                "갈등이 있었어요\n" +
                "위협적인 가게의 태도에 공포심을 느꼈거나 배달 과정 중 불미스러운 일이 발생했다면, 주저하지 말고 고객 안심 센터로 신고해 주세요.\n" +
                "고객안심센터 신고 및 상담 절차\n" +
                "1 신고접수 2 사실조사 3 가게조치,개선권고 4 결과안내\n" +
                "신고 접수된 문의는 내부 운영정책 및 개인정보보호법에 따라 조사하게 되며, 위반사항이 있을 시 조치 후 결과를 알려드립니다.\n" +
                " 고객안심센터 연결하기\n" +
                "24시간 연중무휴 고객센터\t1600-9880\n" +
                "유의사항\n" +
                " 가게조치 및 개선 권고에 대한 결과는 고객의 요청에 따라 전화 또는 메일로 안내해 드립니다.\n" +
                " 주문 및 배달을 위해 가게로 전달된 주문정보는 배달이 완료되면 마스킹 처리됩니다.\n" +
                " 안심번호 적용 시 실제 연락처가 노출되지 않도록 임시 가상번호가 발급되며, 배달이 완료되면 자동 해지됩니다.\n" +
                " 배달의민족 회원이 아니거나 전화 주문의 경우 상담에 제한이 있을 수 있습니다.\n" +
                " 허위신고로 인해 피해 발생 시 법적 처벌을 받을 수 있습니다.\n" +
                " 고객 안심 센터 상담 시 통화내용은 서비스 품질 향상을 위해 녹음됩니다.\n");

        listDataChild.put(listDataParent.get(0), Vegetables);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }

}
