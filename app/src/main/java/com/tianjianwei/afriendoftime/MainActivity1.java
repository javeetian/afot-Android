

/**
 * Created by tianjianwei on 16/10/21.
 */

package com.tianjianwei.afriendoftime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import net.youmi.android.AdManager;
import net.youmi.android.nm.bn.BannerManager;
import net.youmi.android.nm.bn.BannerViewListener;

public class MainActivity1 extends AppCompatActivity {
    protected static final String TAG = "afot";
    private static final boolean isEnableYoumiLog = true;
    private static final String appId = "a7f13425d31f1790";
    private static final String appSecret = "b9d63075c70a73c7";

    private Context mContext;
    private PermissionHelper mPermissionHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main1);

        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
            Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runApp();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runApp();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }


        // 初始化视图
        //initView();
        // 预加载数据
        preloadData();
        // 检查广告配置
        //checkAdSettings();
        //设置广告条
        setupBannerAd();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                showAddView();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(mContext, SpotAdActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 预加载数据
     */
    private void preloadData() {
        // 设置服务器回调 userId，一定要在请求广告之前设置，否则无效
        //VideoAdManager.getInstance(mContext).setUserId("userId");
        // 请求视频广告
        //VideoAdManager.getInstance(mContext).requestVideoAd(mContext);
    }

    /**
     * 检查广告配置
     */
 /*   private void checkAdSettings() {
        Button btnCheckAdSettings = (Button) findViewById(R.id.btn_main_check_ad_settings);
        btnCheckAdSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = VideoAdManager.getInstance(mContext).checkVideoAdConfig();
                Toast.makeText(mContext, String.format("配置 %s", result ? "正确" : "不正确，请对照文档检查是否存在遗漏"), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

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
                        Log.d(TAG, "请求广告条成功");

                    }

                    @Override
                    public void onSwitchBanner() {
                        Log.d(TAG, "广告条切换");
                    }

                    @Override
                    public void onRequestFailed() {
                        Log.e(TAG, "请求广告条失败");
                    }
                });
        // 添加广告条到窗口中
        ((Activity) mContext).addContentView(bannerView, layoutParams);
    }
    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(mContext).init(appId, appSecret, isEnableYoumiLog);
        //设置开屏
        //setupSplashAd();
    }
    public void showAddView() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                //Editable YouEditTextValue = edittext.getText();
                //OR
                String YouEditTextValue = edittext.getText().toString();
            }
        });

        alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }
}
