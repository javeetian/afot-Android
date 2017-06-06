/**
 * Created by tianjianwei20 on 16/10/21.
 */

package com.tianjianwei.afriendoftime;




import java.io.Serializable;

public class TimeLog implements Serializable {
    private int time_record;
    private String log;
    private int time_comsume_minutes;

    public TimeLog(){}
    public TimeLog(int time_record, String log, int time_comsume_minutes) {
        this.time_record = time_record;
        this.log = log;
        this.time_comsume_minutes = time_comsume_minutes;
    }

    public int getTimeRecord() {
        return time_record;
    }

    public String getLog() {
        return log;
    }

    public int getTimeComsumeMinutes() {
        return time_comsume_minutes;
    }

    public void seTtimeRecord(int time_record) {
        this.time_record = time_record;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setTimeComsumeMinutes(int time_comsume_minutes) {
        this.time_comsume_minutes = time_comsume_minutes;
    }
}
