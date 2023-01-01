package com.icia.friend.customerService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.icia.friend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Fragment_tab1 extends Fragment {

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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expListView = (ExpandableListView) view.findViewById(R.id.expListView);

        createListData();

        // Expandable Listview on group click listerner
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // TODO GroupClickListener work
                return false;
            }
        });

        // Expandable Listview Group Expanded Listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO GroupExpandListener work
            }
        });

        // Expandable Listview Group Collapsed listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                // TODO GroupCollapseListener work
            }
        });

        // Expandable Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(context,
                        "Awesome, I clicked:  " + Objects.requireNonNull(listDataChild.get(listDataParent.get(groupPosition))).get(childPosition),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void createListData() {
        listDataParent = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataParent.add("회원가입은 어떻게 하나요?");
        listDataParent.add("회원가입할 때 본인인증 실패됩니다.");
        listDataParent.add("휴먼 전환 안내 메일을 받았어요. 해제 방법은 어떻게 되나요?");
        listDataParent.add("회원 탈퇴를 하고 싶어요.");

        // Adding child data
        List<String> Fruits = new ArrayList<String>();
        Fruits.add("\n1. e-mail 계정으로 가입 \n2. 네이버 아이디로 가입 \n3.페이스북 아이디로 가입 \n4. Apple 아이디로 가입\n");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n생년월일 8자리, 성별, 내국인/외국인 여부, 인증 시 휴대폰 번호를 정확히 입력해야 인증이 가능합니다." +
                "\n\n\n [오류코드별 내용]\n\n- 오류 0097, 0098 : 생년월일 오류로 생년월일 8자리를 입력해주세요.\n" +
                "(Ex: 19970301)\n\n- 오류 0013 : 통신사의 식별 정보 오류로 통신사 고객센터에 문의해주세요.\n");

        List<String> Meats = new ArrayList<String>();
        Meats.add("\n1년간 로그인 등 이용 기록이 없는 이용자의 개인 정보는 즉시 삭제됩니다.\n\n메일에 안내드린 날짜까지 사용하시던 아이디로 로그인을" +
                "하시면 휴면상태를 해제하실 수 있습니다.\n\n탈되 계정은 서비스 이용 및 복구가 불가하오니, 중요한 계정일 경우 휴면으로 인한 계정" +
                "삭제가 되지 않도록 확인 부탁드립니다.\n\n휴면계정으로 전환된 아이디로 로그인을 하시면 휴면상태를 해제하실 수 있습니다.\n");

        List<String> Drinks = new ArrayList<String>();
        Drinks.add("\n회원 탈퇴 시 배달의 민족 App 이용이 불가능하며 포인트 및 주문내역 등 모든 정보가 사라집니다.\n\n탈퇴를 원하신다면, " +
                "[내 정보 '닉네임 클릭'>'회원 탈퇴']를 통해 가능합니다.\n");

        listDataChild.put(listDataParent.get(0), Fruits); // Header, Child data
        listDataChild.put(listDataParent.get(1), Vegetables);
        listDataChild.put(listDataParent.get(2), Meats);
        listDataChild.put(listDataParent.get(3), Drinks);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }
    
}