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

public class Fragment_tab4 extends Fragment {

    private Context context;
    private ExpandableListView expListView;
    ExpandableListAdapter listAdapter;

    List<String> listDataParent;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = this.getActivity();
        return inflater.inflate(R.layout.fragment_tab4, container, false);
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
        listDataParent.add("아이디/비밀번호를 잊어버렸어요.");
        listDataParent.add("내 주문내역을 삭제할 수 있나요?");
        listDataParent.add("본인인증 문자가 안와요.");
        listDataParent.add("푸시 알림을 끄고 싶어요.");

        List<String> Vegetables = new ArrayList<String>();
        Vegetables.add("\n 아이디/비밀번호 찾기는 [로그인 > 아이디/비밀번호 찾기] 에서 가능합니다.\n");

        List<String> Drinks = new ArrayList<String>();
        Drinks.add("\nApp 내 [주문내역>주문상세]를 통해 삭제 가능합니다.\n");

        // Adding child data
        List<String> Fruits = new ArrayList<String>();
        Fruits.add("\n휴대폰의 스팸함을 확인 해주세요.\n" +
                "스팸함에도 없는 경우, 통신사 사정에 따라 인증발송/차단해제에 시간이 소요될 수 있으며 자세한 사항은 통신사로 문의해주세요.\n\n");

        List<String> Meats = new ArrayList<String>();
        Meats.add("\n[환경설정] 페이지에서 이벤트 혜택, 배달현황, 리뷰쓰기, 리뷰댓글, 라이브 방송 시작 수신 여부를 설정할 수 있습니다.\n" +
                "\n\n");

        listDataChild.put(listDataParent.get(0), Vegetables);
        listDataChild.put(listDataParent.get(1), Drinks);
        listDataChild.put(listDataParent.get(2), Fruits); // Header, Child data
        listDataChild.put(listDataParent.get(3), Meats);

        listAdapter = new ExpandableListAdapter(context, listDataParent, listDataChild);
        expListView.setAdapter(listAdapter);
    }

}
