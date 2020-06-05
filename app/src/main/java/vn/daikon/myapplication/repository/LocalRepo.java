package vn.daikon.myapplication.repository;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.prefs.Preferences;

public class LocalRepo {
    SharedPreferences sharedPreferences = null;
    public LocalRepo(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public int getCount() {
        Integer count = 0 ;
        if (sharedPreferences.contains("daumanhtuan.count")){
            count = sharedPreferences.getInt("daumanhtuan.count", 0);
        }
        Log.d("phong", Integer.toString(count));
        return count;
    }
    public String[] getTop(int top){

        int count = getCount();
        String[] list = new String[Math.min(top,count)];
        for (int i = count; i > count - Math.min(top,count); i--){
            list[count - i ] = sharedPreferences.getString("daumanhtuan."+ Integer.toString(i), "");
        }
        return list;
    }
    public void update(String from , String to){
        int count = getCount();

        sharedPreferences.edit().putString(from, to).putString("daumanhtuan."+ Integer.toString(count+1),from)
                .putInt("daumanhtuan.count", count+1).apply();
    }
}
