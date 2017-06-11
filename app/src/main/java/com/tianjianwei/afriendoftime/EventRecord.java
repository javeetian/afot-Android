package com.tianjianwei.afriendoftime;

/**
 * Created by tianjianwei on 17年6月10日.
 */

public class EventRecord {

    private long recordTime;
    private String event;
    private int consumeTime;  // minutes

    public void setRecordTime(long i) {
        recordTime = i;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setEvent(String e) {
        event = e;
    }

    public String getEvent() {
        return event;
    }
    public void setConsumeTime(int i) {
        consumeTime = i;
    }

    public int getConsumeTime() {
        return consumeTime;
    }

}
