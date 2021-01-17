package com.mm.planzajec;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class Schedule implements Serializable, Parcelable {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("weeks")
    @Expose
    private String weeks;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("subgroup")
    @Expose
    private String subgroup;
    public final static Parcelable.Creator<Schedule> CREATOR = new Creator<Schedule>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        public Schedule[] newArray(int size) {
            return (new Schedule[size]);
        }

    };
    private final static long serialVersionUID = 3403968080613044976L;

    protected Schedule(Parcel in) {
        this.day = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.startTime = ((String) in.readValue((String.class.getClassLoader())));
        this.duration = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.weeks = ((String) in.readValue((String.class.getClassLoader())));
        this.group = ((String) in.readValue((String.class.getClassLoader())));
        this.subgroup = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Schedule() {
    }

    /**
     * @param duration
     * @param weeks
     * @param subgroup
     * @param startTime
     * @param day
     * @param group
     */
    public Schedule(Integer day, String startTime, Integer duration, String weeks, String group, String subgroup) {
        super();
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
        this.weeks = weeks;
        this.group = group;
        this.subgroup = subgroup;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("day", day).append("startTime", startTime).append("duration", duration).append("weeks", weeks).append("group", group).append("subgroup", subgroup).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(startTime);
        dest.writeValue(duration);
        dest.writeValue(weeks);
        dest.writeValue(group);
        dest.writeValue(subgroup);
    }

    public int describeContents() {
        return 0;
    }

}
