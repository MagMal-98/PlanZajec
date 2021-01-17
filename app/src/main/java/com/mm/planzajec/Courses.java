package com.mm.planzajec;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class Courses implements Serializable, Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("classes")
    @Expose
    private List<Lessons> aClasses = null;
    public final static Parcelable.Creator<Courses> CREATOR = new Creator<Courses>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Courses createFromParcel(Parcel in) {
            return new Courses(in);
        }

        public Courses[] newArray(int size) {
            return (new Courses[size]);
        }

    };
    private final static long serialVersionUID = 7928110068060658528L;

    protected Courses(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.aClasses, (Lessons.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Courses() {
    }

    /**
     * @param aClasses
     * @param name
     */
    public Courses(String name, List<Lessons> aClasses) {
        super();
        this.name = name;
        this.aClasses = aClasses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lessons> getaClasses() {
        return aClasses;
    }

    public void setaClasses(List<Lessons> aClasses) {
        this.aClasses = aClasses;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("classes", aClasses).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeList(aClasses);
    }

    public int describeContents() {
        return 0;
    }

}

