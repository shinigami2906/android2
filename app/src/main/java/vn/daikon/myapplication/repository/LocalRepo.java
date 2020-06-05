package vn.daikon.myapplication.repository;

import android.content.Intent;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

public class LocalRepo {
    SharedPreferences sharedPreferences = null;
    public LocalRepo(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public int getCount() {
        int count = sharedPreferences.getInt("daumanhtuan.count", 0);
        return count;
    }
    public String[] getTop(int top){

        int count = getCount();
        String[] list = new String[Math.min(top,count)];
        for (int i = 0;i< Math.min(top, count); i++){
            list[i] = sharedPreferences.getString("daumanhtuan."+ Integer.toString(i), "");
        }
        return list;
    }
    public void update(String from , String to){
        int count = getCount();
        sharedPreferences.edit().putString(from, to).putString("daumanhtuan."+ Integer.toString(count+1),from)
                .putString("daumanhtuan.count", Integer.toString(count+1)).apply();
    }
}
