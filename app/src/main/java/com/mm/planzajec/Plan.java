package com.mm.planzajec;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;


public class Plan implements Serializable, Parcelable {

    @SerializedName("courses")
    @Expose
    private List<Courses> courses = null;
    public final static Parcelable.Creator<Plan> CREATOR = new Creator<Plan>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        public Plan[] newArray(int size) {
            return (new Plan[size]);
        }

    };
    private final static long serialVersionUID = -4047854629604104921L;

    protected Plan(Parcel in) {
        in.readList(this.courses, (com.mm.planzajec.Courses.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Plan() {
    }

    /**
     * @param courses
     */
    public Plan(List<Courses> courses) {
        super();
        this.courses = courses;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("courses", courses).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(courses);
    }

    public int describeContents() {
        return 0;
    }

}

