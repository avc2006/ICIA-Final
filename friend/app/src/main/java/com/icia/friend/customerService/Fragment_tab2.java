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

public class Fragment_tab2 extends Fragment {

    private Context context;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        return inflater.inflate(R.layout.fragment_tab1, container, false);
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
        listDataParent.add("음식을 주문하려면 어떻게 해야 하나요?");
        listDataParent.add("주문 취소는 어떻게 해야 하나요?");
        listDataParent.add("주문 후 메뉴 혹은 수량을 변경하고 싶어요.");
        listDataParent.add("주문한 내역은 어디서 확인할 수 있나요?");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n1. 배달의민족 App 결제 방식은 아래의 방법 중 하나를 선택할 수 있습니다. \n" +
                "\n" +
                "① 신용카드 \n" +
                "\n" +
                "② 휴대폰 \n" +
                "\n" +
                "③ 배민페이 : 최초 1회 카드 정보 및 비밀번호를 설정하면, 이후 사용에는 비밀번호만으로 편리하게 사용 가능한 결제방식 \n" +
                "\n" +
                "④ 배민페이계좌이체 : 본인 인증 후 비밀번호 설정으로 손쉽게 계좌 결제를 할 수 있는 간편 결제방식 \n" +
                "\n" +
                "⑤ 배민페이머니 : 배민페이 계좌를 통해 충전하여 사용 가능한 결제방식 \n" +
                "\n" +
                "⑥ 카카오페이, PAYCO, 네이버페이, 토스 : 별도 해당 서비스 가입 후 결제하는 방식 \n" +
                "\n" +
                "\n" +
                "\n" +
                "2. [만나서결제]는 음식 수령 후 배달기사에게 '카드 또는 현금'으로 직접 결제하는 방식입니다. \n" +
                "\n" +
                "(배민1(one)의 경우 만나서결제 이용이 불가하며, 이외 배달가게도 설정에 따라 만나서결제 이용이 제한될 수 있습니다.) \n");

        List<String> Drinks = new ArrayList<String>();
        Drinks.add("\n1. 가게에서 주문을 접수하기 전\n" +
                "\n" +
                " - App에서 직접 취소할 수 있습니다.\n" +
                " ● 경로 : 주문내역 > '취소할 주문' 클릭 > '주문 취소' 버튼 클릭\n" +
                "\n" +
                "2. 가게에서 주문을 접수한 후 \n" +
                " - 주문한 가게로 연락하여 취소가 가능한지 확인할 수 있습니다. \n" +
                " ● 경로 : 주문내역 > '취소할 주문' 클릭 > 가게명 옆 '전화' 버튼 클릭\n" +
                " \n" +
                " 단, 배민1(one), B마트, 배민스토어는 고객센터로 연락 해주세요.\n");

        // Adding child data
        List<String> Fruits = new ArrayList<String>();
        Fruits.add("\n주문 접수가 완료되었다는 알림 톡을 받으셨다면, 결제한 내용에 대한 변경이 어렵습니다.\n" +
                "\n" +
                "아직 알림 톡이 오지 않았다면, 주문하신 가게 또는 고객센터로 연락 주시기 바랍니다.\n");

        List<String> Meats = new ArrayList<String>();
        Meats.add("\n결제가 완료 된 주문내역은 [주문내역] 메뉴에서 확인 가능합니다.\n");

        listDataChild.put(listDataParent.get(0), Vegetables);
        listDataChild.put(listDataParent.get(1), Drinks);
        listDataChild.put(listDataParent.get(2), Fruits); // Header, Child data
        listDataChild.put(listDataParent.get(3), Meats);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }

}