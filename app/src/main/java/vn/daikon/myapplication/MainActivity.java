package vn.daikon.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;
import vn.daikon.myapplication.source.TextApi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.daikon);
    }

    @Override
    public void onClick(View v) {
        TextApi textApi = new TextApi();
        try {
            TextResponse[] textResponses = textApi.execute(new TextRequest("chair")).get();
            if (textResponses != null) {
                textView.setText(textResponses[0].translation[0].text);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
