

/**
 * Created by tianjianwei on 16/10/21.
 */

package com.tianjianwei.afriendoftime;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import net.youmi.android.AdManager;

import java.util.HashMap;

public class MainActivity1 extends AppCompatActivity {
    protected static final String TAG = "afot";
    private static final boolean isEnableYoumiLog = true;
    private static final String appId = "a7f13425d31f1790";
    private static final String appSecret = "b9d63075c70a73c7";

    private HashMap<String, EventRecord> ers;
    private Context mContext;
    private PermissionHelper mPermissionHelper;
    private ListView listView=null;

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
        AFOT  a = (AFOT)getApplicationContext();
        a.loadData();
        listView=(ListView)findViewById(R.id.lv_content);
        listView.setAdapter(new MyAdapter(this, a.lers));

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
    protected void onDestroy() {
        super.onDestroy();
        AFOT a = (AFOT)getApplicationContext();
        a.saveData();
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
            startActivity(new Intent(mContext, SupportUsActivity.class));
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
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(mContext).init(appId, appSecret, isEnableYoumiLog);
        //设置开屏
        //setupSplashAd();
    }
    public void showAddView() {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_add, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("添加");
        alert.setView(layout);

        final EditText edt_event = (EditText) layout.findViewById(R.id.edt_event);
        final EditText edt_time_h = (EditText) layout.findViewById(R.id.edt_time_h);
        final EditText edt_time_m = (EditText) layout.findViewById(R.id.edt_time_m);
        alert.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                ers = new HashMap<String, EventRecord>();
                EventRecord er = new EventRecord();
                AFOT a = (AFOT)getApplicationContext();
                String event = edt_event.getText().toString();
                String time_h = edt_time_h.getText().toString();
                String time_m = edt_time_m.getText().toString();
                if (event.length() > 0 && (time_h.length() > 0 || time_m.length() > 0)) {
                    int timeh = 0, timem = 0;
                    er.setEvent(event);
                    er.setRecordTime(System.currentTimeMillis());
                    if (time_h.length() > 0) { timeh = Integer.parseInt(time_h); }
                    if (time_m.length() > 0) { timem = Integer.parseInt(time_m); }
                    er.setConsumeTime(timeh * 60 + timem);
                    ers.put(String.valueOf(a.lers.size()), er);
                    a.lers.add(ers);
                    a.saveData();
                    dialog.dismiss();
                }
            }
        });

        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                dialog.cancel();
            }
        });

        alert.show();
    }
}
