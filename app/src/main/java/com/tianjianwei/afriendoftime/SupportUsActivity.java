package com.tianjianwei.afriendoftime;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import net.youmi.android.nm.bn.BannerManager;
import net.youmi.android.nm.bn.BannerViewListener;
import net.youmi.android.nm.cm.ErrorCode;
import net.youmi.android.nm.sp.SpotListener;
import net.youmi.android.nm.sp.SpotManager;
import net.youmi.android.nm.vdo.VideoAdListener;
import net.youmi.android.nm.vdo.VideoAdManager;
import net.youmi.android.nm.vdo.VideoAdSettings;

import static com.tianjianwei.afriendoftime.logUtils.logDebug;
import static com.tianjianwei.afriendoftime.logUtils.logError;
import static com.tianjianwei.afriendoftime.logUtils.logInfo;
import static com.tianjianwei.afriendoftime.logUtils.showShortToast;

public class SupportUsActivity extends AppCompatActivity {

    protected static final String TAG = "su";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_us);
        mContext = this;
        //设置广告条
        setupBannerAd();
        // 设置插屏广告
        setupSpotAd();
        // 设置视频广告
        setupVideoAd();
    }

    /**
     * 设置插屏广告
     */
    private void setupSpotAd() {
        // 设置插屏图片类型，默认竖图
        //		// 横图
        //		SpotManager.getInstance(mContext).setImageType(SpotManager
        // .IMAGE_TYPE_HORIZONTAL);
        // 竖图
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        //		// 无动画
        //		SpotManager.getInstance(mContext).setAnimationType(SpotManager
        // .ANIMATION_TYPE_NONE);
        //		// 简单动画
        //		SpotManager.getInstance(mContext).setAnimationType(SpotManager
        // .ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(mContext)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        Button btnShowSpotAd = (Button) findViewById(R.id.btn_show_spot_ad);

        btnShowSpotAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 展示插屏广告
                SpotManager.getInstance(mContext).showSpot(mContext, new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                        logInfo("插屏展示成功");
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        logError("插屏展示失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                showShortToast("网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                showShortToast("暂无插屏广告");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                showShortToast("插屏资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                showShortToast("请勿频繁展示");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                showShortToast("请设置插屏为可见状态");
                                break;
                            default:
                                showShortToast("请稍后再试");
                                break;
                        }
                    }

                    @Override
                    public void onSpotClosed() {
                        logDebug("插屏被关闭");
                    }

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                        logDebug("插屏被点击");
                        logInfo("是否是网页广告？%s", isWebPage ? "是" : "不是");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 点击后退关闭插屏广告
        if (SpotManager.getInstance(mContext).isSpotShowing()) {
            SpotManager.getInstance(mContext).hideSpot();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 插屏广告
        SpotManager.getInstance(mContext).onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 插屏广告
        SpotManager.getInstance(mContext).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 插屏广告
        SpotManager.getInstance(mContext).onDestroy();
    }

    /**
     * 设置广告条广告
     */
    private void setupBannerAd() {
        //		/**
        //		 * 普通布局
        //		 */
        //		// 获取广告条
        //		View bannerView = BannerManager.getInstance(mContext)
        //				.getBannerView(mContext, new BannerViewListener() {
        //					@Override
        //					public void onRequestSuccess() {
        //						logInfo("请求广告条成功");
        //					}
        //
        //					@Override
        //					public void onSwitchBanner() {
        //						logDebug("广告条切换");
        //					}
        //
        //					@Override
        //					public void onRequestFailed() {
        //						logError("请求广告条失败");
        //					}
        //				});
        //		// 实例化广告条容器
        //		LinearLayout bannerLayout = (LinearLayout) findViewById(R.id.ll_banner);
        //		// 添加广告条到容器中
        //		bannerLayout.addView(bannerView);

        /**
         * 悬浮布局
         */
        // 实例化LayoutParams
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置广告条的悬浮位置，这里示例为右下角
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        // 获取广告条
        final View bannerView = BannerManager.getInstance(mContext)
                .getBannerView(mContext, new BannerViewListener() {

                    @Override
                    public void onRequestSuccess() {
                        logInfo("请求广告条成功");

                    }

                    @Override
                    public void onSwitchBanner() {
                        logDebug("广告条切换");
                    }

                    @Override
                    public void onRequestFailed() {
                        logError("请求广告条失败");
                    }
                });
        // 添加广告条到窗口中
        ((Activity) mContext).addContentView(bannerView, layoutParams);
    }


    /**
     * 设置视频广告
     */
    private void setupVideoAd() {
        // 设置视频广告
        final VideoAdSettings videoAdSettings = new VideoAdSettings();
        videoAdSettings.setInterruptTips("退出视频播放将无法获得奖励" + "\n确定要退出吗？");

        //		// 只需要调用一次，由于在主页窗口中已经调用了一次，所以此处无需调用
        //		VideoAdManager.getInstance().requestVideoAd(mContext);

        Button btnShowVideoAd = (Button) findViewById(R.id.btn_show_video_ad);
        btnShowVideoAd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 展示视频广告
                VideoAdManager.getInstance(mContext)
                        .showVideoAd(mContext, videoAdSettings, new VideoAdListener() {
                            @Override
                            public void onPlayStarted() {
                                logDebug("开始播放视频");
                            }

                            @Override
                            public void onPlayInterrupted() {
                                showShortToast("播放视频被中断");
                            }

                            @Override
                            public void onPlayFailed(int errorCode) {
                                logError("视频播放失败");
                                switch (errorCode) {
                                    case ErrorCode.NON_NETWORK:
                                        logError("网络异常");
                                        break;
                                    case ErrorCode.NON_AD:
                                        logError("视频暂无广告");
                                        break;
                                    case ErrorCode.RESOURCE_NOT_READY:
                                        logError("视频资源还没准备好");
                                        break;
                                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                                        logError("视频展示间隔限制");
                                        break;
                                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                        logError("视频控件处在不可见状态");
                                        break;
                                    default:
                                        logError("请稍后再试");
                                        break;
                                }
                            }

                            @Override
                            public void onPlayCompleted() {
                                showShortToast("视频播放成功");
                            }
                        });
            }
        });
    }
}
