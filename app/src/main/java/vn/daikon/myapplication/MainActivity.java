package vn.daikon.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;
import vn.daikon.myapplication.repository.LocalRepo;
import vn.daikon.myapplication.repository.TextApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    EditText et_from;
    TextView tv_to;
    TextView tv_list;
    SharedPreferences sharedPreferences;
    LocalRepo localRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_from = (EditText) findViewById(R.id.from);
        tv_to = (TextView) findViewById(R.id.to);
        tv_list = (TextView) findViewById(R.id.list);
        sharedPreferences = getSharedPreferences("Tuan",MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG,"tuan");
                localRepo = new LocalRepo(sharedPreferences);
                String x = localRepo.getTop(1)[0];
                tv_list.setText(x);
            }
        });
    }

    @Override
    public void onClick(View v) {
        TextApi textApi = new TextApi();
        String from = et_from.getText().toString();
        if (from != null && from !="") {
            try {
                TextResponse[] textResponses = textApi.execute(new TextRequest(from)).get();
                if (textResponses != null) {
                    tv_to.setText(textResponses[0].translation[0].text);
                    localRepo = new LocalRepo(sharedPreferences);
                    localRepo.update(from,textResponses[0].translation[0].text);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
