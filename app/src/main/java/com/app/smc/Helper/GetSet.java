package com.app.smc.Helper;

public class GetSet {

    private static String id = null;
    private static String name = null;
    private static String mobile = null;
    private static String role = null;

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        GetSet.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        GetSet.name = name;
    }

    public static String getMobile() {
        return mobile;
    }

    public static void setMobile(String mobile) {
        GetSet.mobile = mobile;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        GetSet.role = role;
    }

    public static void reset() {

        GetSet.setMobile(null);
        GetSet.setName(null);
        GetSet.setRole(null);
        GetSet.setId(null);

    }
}
