package com.tianjianwei.afriendoftime;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tianjianwei20 on 2017/6/11.
 */

public class MyAdapter extends BaseAdapter {

    private List<HashMap<String, EventRecord>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public MyAdapter(Context context, List<HashMap<String, EventRecord>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }
    /**
     * 组件集合，对应list.xml中的控件
     * @author Administrator
     */
    public final class Zujian{
        public TextView recordTime;
        public TextView event;
        public TextView consumeTime;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if(convertView == null){
            zujian=new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.list, null);
            zujian.recordTime = (TextView)convertView.findViewById(R.id.tv_record_time);
            zujian.event = (TextView)convertView.findViewById(R.id.tv_event);
            zujian.consumeTime = (TextView)convertView.findViewById(R.id.tv_consume_time);
            convertView.setTag(zujian);
        }else{
            zujian = (Zujian)convertView.getTag();
        }
        //绑定数据

        zujian.recordTime.setText(DateFormat.format("yyyy年MM月dd日,kk:mm", data.get(position).get(String.valueOf(position)).getRecordTime()));
        zujian.event.setText(data.get(position).get(String.valueOf(position)).getEvent());
        int m = data.get(position).get(String.valueOf(position)).getConsumeTime();
        boolean h = m > 59 ? true : false;
        if (h) {
            if (m % 60 == 0) {
                zujian.consumeTime.setText(String.format("%d 小时", m / 60));
            }
            else {
                zujian.consumeTime.setText(String.format("%d 小时 %d 分钟", m / 60, m % 60));
            }
        }
        else {
            zujian.consumeTime.setText(String.format("%d 分钟", m));
        }
        return convertView;
    }

}
