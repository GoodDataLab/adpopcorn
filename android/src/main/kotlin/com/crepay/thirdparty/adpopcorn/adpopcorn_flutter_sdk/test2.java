package com.crepay.thirdparty.adpopcorn.adpopcorn_flutter_sdk;

import com.igaworks.adpopcorn.style.APSize;
import com.igaworks.adpopcorn.style.ApStyleManager;

import java.util.HashMap;

public class test2 {
    // 스타일 옵션 HashMap 생성
    void test () {
    HashMap <String, Object> optionMap = new HashMap<String, Object>();
// 컬러코드는 RGB(ex : #RRGGBB)와 16진수(ex : 0xffee5555) 형식을 지원å

// 오퍼월 테마 컬러 변경

//    optionMap.put(ApStyleManager.CustomData.OFFERWALL_THEME_COLOR, Color.parseColor("#EE5555"));


// 오퍼월 탑바 배경 컬러 변경 optionMap.put(ApStyleManager.CustomData.TOP_BAR_BG_COLOR, 0xffee5555);
// 오퍼월 탑바 쉐도우 효과 변경 optionMap.put(ApStyleManager.CustomData.TOP_BAR_SHADOW, true);
// 탑바 닫기 버튼 이미지 변경
//    optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_CLOSE_BTN_RESOURCE_ID, R.drawable.btn_1depth_close);
// 탑바 커스텀 타이틀 로고 사이즈 설정
    optionMap.put(ApStyleManager.CustomStyle. OFFERWALL_TITLE_LOGO_RESOURCE_SIZE, new APSize(100, 100));
// 세팅된 스타일 옵션 HashMap을 적용
 ApStyleManager.setCustomOfferwallStyle(optionMap);
}
}
