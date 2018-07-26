package com.app.smc.Helper;

import android.content.SharedPreferences;

public class Constants {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    public static String BASE_URL = "http://shadowws.in/smc/api/user/";

    public  String parent="parent";
    public  String student="student";
    public  String staff="staff";


    public static String LOGIN = "login";
    public static String CLASS = "getclass";
    public static String SUBJECT = "getsubject";
    public static String STAFF_RESULT = "listmark";
    public static String STAFF_RESULT_STUDENT = "listmarkstudent";
    public static String STAFF_ADD_SCHEDULE = "addschedule";

    public static String id="id";
    public static String name="name";
    public static String mobile="mobile";
    public static String role="role";


}
