package com.tianjianwei.afriendoftime;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tianjianwei20 on 2017/6/11.
 */

public class AFOT extends Application {

    public List<HashMap<String, EventRecord>> lers;

    public void loadData() {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);

        Type type = new TypeToken<List<HashMap<String, EventRecord>>>(){}.getType();
        String json = spf.getString("eventDb", "");
        lers = new Gson().fromJson(json, type);
        if (lers == null) {
            HashMap<String, EventRecord> ers = new HashMap<String, EventRecord>();
            EventRecord er = new EventRecord();
            er.setConsumeTime(30);
            er.setRecordTime(System.currentTimeMillis());
            er.setEvent("看书");
            ers.put(String.valueOf(0), er);
            lers = new ArrayList<HashMap<String,EventRecord>>();
            lers.add(ers);
        }
    }

    public void saveData() {

        SharedPreferences spf = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = spf.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lers);
        prefsEditor.putString("eventDb", json);
        prefsEditor.commit();
    }
}
