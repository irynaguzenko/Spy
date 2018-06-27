package com.spy.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferences {
    private static final String PREF_USER_NAME = "username";

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName).apply();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear().apply();
    }

    private static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
//
//    public static void setUserName(Context ctx, String userName) {
//        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
//        editor.putString(PREF_USER_NAME, userName).apply();
//    }
//
//    public static String getUserName(Context ctx) {
//        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
//    }
//
//    public static void clearUserName(Context ctx) {
//        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
//        editor.clear().apply();
//    }
//
//    private static SharedPreferences getSharedPreferences(Context ctx) {
//        PreferenceManager.
//        return PreferenceManager.getDefaultSharedPreferences(ctx);
//    }
}
