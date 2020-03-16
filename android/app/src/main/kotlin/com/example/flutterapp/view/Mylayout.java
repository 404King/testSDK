package com.example.flutterapp.view;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformView;
import android.widget.LinearLayout;
import java.util.Map;
import android.graphics.Color;
import android.view.View;
import android.util.Log;
import android.widget.FrameLayout;
import android.content.Context;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.example.flutterapp.MainActivity;
import com.example.flutterapp.R;

public class Mylayout implements PlatformView {
    public FrameLayout frameLayout;
    private BinaryMessenger messenger;

    private String mCodeId = "887308245";
    private boolean mIsExpress = false; //是否请求模板广告
    private static final int AD_TIME_OUT = 10000;
    private TTAdNative mTTAdNative;
    private static final String TAG = "Mylayout";
    public Context mContext;

    Mylayout(android.content.Context context, BinaryMessenger messenger, int id, Map<String, Object> params) {
        this.messenger = messenger;
        mContext = context;
//        FrameLayout mLinearLayout = new FrameLayout(context);
//        mLinearLayout.setBackgroundColor(Color.rgb(100, 200, 100));
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(1080, 1920);
//        mLinearLayout.setLayoutParams(lp);
//        frameLayout = mLinearLayout;

    }

    @java.lang.Override
    public android.view.View getView() {
        mTTAdNative = TTAdManagerHolder.get().createAdNative(mContext);
        loadSplashAd();
        if(frameLayout == null){
            Log.d(TAG, "frameLayout == null");
        } else {
            Log.d(TAG, "frameLayout");
        }
        return frameLayout;
    }

    @java.lang.Override
    public void dispose() {

    }

    private void loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
//            float expressViewWidth = UIUtils.getScreenWidthDp(this);
//            float expressViewHeight = UIUtils.getHeight(this);
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
//                    .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(mCodeId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
//            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, code + "");
                Log.d(TAG, String.valueOf(message));
            }

            @Override
//            @MainThread
            public void onTimeout() {
                Log.d(TAG, "开屏广告加载超时");
            }

            @Override
//            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
//                if (ad == null) {
//                    return;
//                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null ) {
                    FrameLayout mLinearLayout = new FrameLayout(mContext);
                    mLinearLayout.setBackgroundColor(Color.rgb(100, 200, 100));
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1080,1920);
                    mLinearLayout.setLayoutParams(layoutParams);
                    frameLayout = mLinearLayout;
                    frameLayout.addView(view);
//                    mSplashContainer.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
//                    mSplashContainer.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                    Log.d(TAG, "已获取到View");
                }else {
                    Log.d(TAG, "获取不到View");
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                    }
                });
                if(ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {


                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        }, AD_TIME_OUT);

    }
}
