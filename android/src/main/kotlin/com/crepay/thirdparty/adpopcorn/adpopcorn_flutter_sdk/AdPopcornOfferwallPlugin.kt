package com.crepay.thirdparty.adpopcorn.adpopcorn_flutter_sdk

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.annotation.NonNull
import com.igaworks.adpopcorn.Adpopcorn
import com.igaworks.adpopcorn.AdpopcornExtension
import com.igaworks.adpopcorn.style.ApStyleManager
import com.igaworks.adpopcorn.cores.common.APPopupAdError
import com.igaworks.adpopcorn.interfaces.IAPPopupAdEventListener
import com.igaworks.adpopcorn.interfaces.IAdPOPcornEventListener
import com.igaworks.adpopcorn.style.APSize
import io.flutter.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.HashMap

private const val CHANNEL_NAME = "adpopcorn_flutter_sdk"
private const val TAG: String = "[Android] AdPopcornOfferwallPlugin";

/** AdPopcornOfferwallPlugin */
class AdPopcornOfferwallPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var activity: Activity

    private val invokeResult = object : Result {
        override fun success(result: Any?) {
            Log.v(TAG, "invokeResult: success: '$result'")
        }

        override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
            Log.e(TAG, "invokeResult: error: $errorCode, $errorMessage, $errorDetails")
        }

        override fun notImplemented() {
            Log.e(TAG, "invokeResult: notImplemented:")
        }
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_NAME)
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "setUserId") {
            Adpopcorn.setUserId(activity, call.argument("userId"))
            result.success(null)
        } else if (call.method == "openOfferWall") {
            Adpopcorn.openOfferWall(activity)
            result.success(null)
        } else if (call.method == "useFlagShowWhenLocked") {
            val optionMap = HashMap<String, Any>()
            // 컬러코드는 RGB(ex : #RRGGBB)와 16진수(ex : 0xffee5555) 형식을 지원å

// 오퍼월 테마 컬러 변경

   optionMap.put(ApStyleManager.CustomStyle.OFFERWALL_THEME_COLOR, Color.parseColor("#A061F5"));
   optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_BG_COLOR, Color.parseColor("#000000"));
            // optionMap.put(ApStyleManager.CustomStyle.OFFERWALL_TITLE_TEXT, "오퍼월임");
//            optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_BG_COLOR, 0xffee5555);
//            optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_TEXT_COLOR, 0xffffffff);
//            optionMap.put(ApStyleManager.CustomStyle.OFFERWALL_BG_COLOR, 0xff2f3a3e);


// 오퍼월 탑바 배경 컬러 변경
//  optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_BG_COLOR, 0xff000000);
// 오퍼월 탑바 쉐도우 효과 변경 optionMap.put(ApStyleManager.CustomData.TOP_BAR_SHADOW, true);
// 탑바 닫기 버튼 이미지 변경
//    optionMap.put(ApStyleManager.CustomStyle.TOP_BAR_CLOSE_BTN_RESOURCE_ID, R.drawable.btn_1depth_close);
   optionMap.put(ApStyleManager.CustomStyle.OFFERWALL_TITLE_LOGO_RESOURCE_ID, R.drawable.logo);
// 탑바 커스텀 타이틀 로고 사이즈 설정
            optionMap[ApStyleManager.CustomStyle.OFFERWALL_TITLE_LOGO_RESOURCE_SIZE] = APSize(100, 20)
            // 세팅된 스타일 옵션 HashMap을 적용
            ApStyleManager.setCustomOfferwallStyle(optionMap)

            result.success(null)
        } else if (call.method == "openCSPage") {
            AdpopcornExtension.openCSPage(activity, call.argument("userId"))
            result.success(null)
        } else if (call.method == "getEarnableTotalRewardInfo") {
            AdpopcornExtension.getEarnableTotalRewardInfo(activity) { queryResult, totalCount, totalReward ->
                channel.invokeMethod(
                    "onGetEarnableTotalRewardInfo", mapOf(
                        "queryResult" to queryResult,
                        "totalCount" to totalCount,
                        "totalReward" to totalReward,
                    ), invokeResult
                )
            };
            result.success(null)
        } else if (call.method == "loadPopupAd") {
            Adpopcorn.loadPopupAd(activity, object : IAPPopupAdEventListener {
                override fun OnLoadPopupAdSuccess() {
                    Log.i(TAG, "OnLoadPopupAdSuccess:")
                    channel.invokeMethod("onLoadPopupAdSuccess", null, invokeResult)
                }

                override fun OnLoadPopupAdFailure(error: APPopupAdError) {
                    Log.e(
                        TAG,
                        "OnLoadPopupAdFailure: errorCode=${error.errorCode}, errorMessage=${error.errorMessage}"
                    )
                    channel.invokeMethod(
                        "onLoadPopupAdFailure", mapOf(
                            "errorCode" to error.errorCode,
                            "errorMessage" to error.errorMessage,
                        ), invokeResult
                    )
                }

                override fun OnShowPopupAdSuccess() {
                    Log.d(TAG, "OnShowPopupAdSuccess:")
                    channel.invokeMethod("onShowPopupAdSuccess", null, invokeResult)
                }

                override fun OnShowPopupAdFailure(error: APPopupAdError) {
                    Log.e(
                        TAG,
                        "OnShowPopupAdFailure: errorCode=${error.errorCode}, errorMessage=${error.errorMessage}"
                    )
                    channel.invokeMethod(
                        "onShowPopupAdFailure", mapOf(
                            "errorCode" to error.errorCode,
                            "errorMessage" to error.errorMessage,
                        ), invokeResult
                    )
                }

                override fun OnPopupAdClose() {
                    Log.d(TAG, "OnPopupAdClose:")
                    channel.invokeMethod("onPopupAdClose", null, invokeResult)
                }

            })
            result.success(null)
        } else if (call.method == "showPopupAd") {
            Adpopcorn.showPopupAd(activity)
            result.success(null)
        } else {
            result.notImplemented()
        }
    }

    private fun initEventListener() {
        Adpopcorn.setEventListener(activity, object : IAdPOPcornEventListener {
            // 오퍼월 진입 후, 개인 정보 수집 동의 시 이벤트 발생
            override fun OnAgreePrivacy() {
                channel.invokeMethod("onAgreePrivacy", null, invokeResult)
            }

            override fun OnDisagreePrivacy() {
                channel.invokeMethod("onDisagreePrivacy", null, invokeResult)
            }

            // 오퍼월이 종료될 때 이벤트 발생
            override fun OnClosedOfferWallPage() {
                channel.invokeMethod("onClosedOfferWallPage", null, invokeResult)
            }
        })
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivity() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
        initEventListener();
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }
}
