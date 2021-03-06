package com.example.excecutiveschedulergo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * From https://stackoverflow.com/a/9193028
 * Has been heavily modified.
 */

/**
 * Stores auth token in sharedpreferences.  Provides methods to
 * get, set and delete token.
 */
public class TokenStore {

    private static final String PREF_NAME = "TOKEN";
    private static final String KEY = "token";

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        String token = prefs.getString(KEY, null);
        return token;
    }

    public static void setToken(String token, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE).edit();
        editor.putString(KEY, token);
        editor.apply();
    }

    public static void deleteToken(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE).edit();
        editor.remove(KEY);
        editor.apply();
    }

}
