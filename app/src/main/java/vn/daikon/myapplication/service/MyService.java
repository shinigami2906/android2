package vn.daikon.myapplication.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.telecom.Call;
import android.util.Log;


import androidx.annotation.Nullable;

import javax.security.auth.callback.Callback;

import vn.daikon.myapplication.MainActivity;

public class MyService  extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                ClipData data = clipboardManager.getPrimaryClip();
                String x = data.getItemAt(0).getText().toString();
                Intent intent = new Intent(MyService.this, FloatingViewService.class).putExtra("from",x);
                startService(intent);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
