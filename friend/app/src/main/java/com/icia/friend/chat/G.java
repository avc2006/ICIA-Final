package com.icia.friend.chat;

public class G {

    //입장 버튼을 누르면 내 phone에 저장(내폰에 저장된 값으로 로그인을 계속 안하기 위해)
    // Firebase에 저장하는 코드를 작성하자.

    //내 phone에 저장(내폰에 저장된 값으로 로그인을 계속 안하기 위해) 위해선
    // 그 데이터 값을 계속 갖고 있을 전역 변수가 필요하므로..
    // G라는 class를 만들어서 매개 변수로 값을 저장하겠다.

    public static String nickName;
    public static String profileUrl;
}
