package com.mm.planzajec;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class PlanItem {

    private String mHour;
    private String mName;
    private String mSupervisor;
    private String mRoom;
    private int mDay;
    private String mGroup;
    private String mSubgroup;
    private String mWeeks;

    public PlanItem(String hour, String name, String supervisor, String room, int day, String group, String subgroup, String weeks) {
        mHour = hour;
        mName = name;
        mSupervisor = supervisor;
        mRoom = room;
        mDay = day;
        mGroup = group;
        mSubgroup = subgroup;
        mWeeks = weeks;
    }

    public String getHour() {
        return mHour;
    }

    public String getName() {
        return mName;
    }

    public String getSupervisor() {
        return mSupervisor;
    }

    public String getRoom() {
        return mRoom;
    }

    public int getDay() {
        return mDay;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getSubgroup() {
        return mSubgroup;
    }

    public String getWeeks() {
        return mWeeks;
    }

    public static Comparator<PlanItem> StuNameComparator = new Comparator<PlanItem>() {

        public int compare(PlanItem s1, PlanItem s2) {
            String StudentName1 = s1.getHour().toUpperCase();
            String StudentName2 = s2.getHour().toUpperCase();

            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            try {
                Date d1 = dateFormat.parse(StudentName1);
                Date d2 = dateFormat.parse(StudentName2);

                assert d1 != null;
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 1;
            }
        }
    };
}