package vn.daikon.myapplication.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.daikon.myapplication.MainActivity;
import vn.daikon.myapplication.R;
import vn.daikon.myapplication.adapter.TranslateAdapter;
import vn.daikon.myapplication.model.Translation;
import vn.daikon.myapplication.presenter.MyInterface;
import vn.daikon.myapplication.presenter.Presenter;
import vn.daikon.myapplication.repository.LocalRepo;

public class FloatingViewService extends Service  implements MyInterface.View, AdapterView.OnItemSelectedListener {

    private WindowManager mWindowManager;
    private View mFloatingView;
    ImageView close_bt,close_iv;
    Spinner sp_from,sp_to;
    LocalRepo localRepo;
    MyInterface.IPresenter presenter;
    String text;
    TextView tv_out;
    EditText et_inp;
    String from = "en", to = "vi";
    Button enter_text;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void setAdapter(){
        List<String> list2 = new ArrayList<>();
        list2.add("Vietnamese");list2.add("English");
        ArrayAdapter<String> adapterFrom  = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list2);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_from.setAdapter(adapterFrom);
        sp_to.setAdapter(adapterFrom);
        sp_from.setSelection(1);
        sp_to.setSelection(0);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int x = super.onStartCommand(intent, flags, startId);
        text = intent.getExtras().getString("from");

        return x;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        sp_from = (Spinner) mFloatingView.findViewById(R.id.spinner_language_from);
        sp_to = (Spinner) mFloatingView.findViewById(R.id.spinner_language_to);
        close_iv = (ImageView) mFloatingView.findViewById(R.id.close_iv);
        close_bt = (ImageView) mFloatingView.findViewById(R.id.close_bt);
        et_inp = (EditText)         mFloatingView.findViewById(R.id.text_input);
        tv_out = (TextView)         mFloatingView.findViewById(R.id.text_ouput);

        enter_text = (Button) mFloatingView.findViewById(R.id.enter_text);
        localRepo = LocalRepo.getInstance();
        setAdapter();
        presenter = new Presenter(this,localRepo);


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                                onClickTransButton(enter_text);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:

                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
        close_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
    }

    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
    public void onClickTransButton(View view){
        et_inp.setText(text);
        if (text != "") presenter.translate(text,from,to);

    }
    @Override
    public void updateView(String to, Translation translation) {
        tv_out.setText(to);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String x = "vi";
        if (position == 0) x = "vi";
        else x = "en";

        if (parent.getId() == R.id.spinner_language_from) from = x; else to = x;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
