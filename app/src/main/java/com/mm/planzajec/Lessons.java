package com.mm.planzajec;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;


public class Lessons implements Serializable, Parcelable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("supervisor")
    @Expose
    private String supervisor;
    @SerializedName("schedule")
    @Expose
    private List<Schedule> schedule = null;
    public final static Parcelable.Creator<Lessons> CREATOR = new Creator<Lessons>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Lessons createFromParcel(Parcel in) {
            return new Lessons(in);
        }

        public Lessons[] newArray(int size) {
            return (new Lessons[size]);
        }

    };
    private final static long serialVersionUID = -4916457578812788858L;

    protected Lessons(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.room = ((String) in.readValue((String.class.getClassLoader())));
        this.supervisor = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.schedule, (com.mm.planzajec.Schedule.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Lessons() {
    }

    /**
     * @param schedule
     * @param type
     * @param room
     * @param supervisor
     */
    public Lessons(String type, String room, String supervisor, List<Schedule> schedule) {
        super();
        this.type = type;
        this.room = room;
        this.supervisor = supervisor;
        this.schedule = schedule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("room", room).append("supervisor", supervisor).append("schedule", schedule).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(room);
        dest.writeValue(supervisor);
        dest.writeList(schedule);
    }

    public int describeContents() {
        return 0;
    }

}
