package vn.daikon.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import vn.daikon.myapplication.adapter.TranslateAdapter;
import vn.daikon.myapplication.model.Translation;
import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;
import vn.daikon.myapplication.presenter.MyInterface;
import vn.daikon.myapplication.presenter.Presenter;
import vn.daikon.myapplication.repository.LocalRepo;
import vn.daikon.myapplication.repository.TextApi;
import vn.daikon.myapplication.service.FloatingViewService;
import vn.daikon.myapplication.service.MyService;

public class MainActivity extends AppCompatActivity implements MyInterface.View , AdapterView.OnItemSelectedListener {
    Spinner sp_from,sp_to;
    Button im_sw,im_clear,bt_speech;
    TextView tv_out;
    EditText et_inp;
    ListView lv;
    List<Translation> list ;
    String from = "en", to = "vi";
    LocalRepo localRepo;
    MyInterface.IPresenter presenter;
    Switch aSwitch;

    private final int REQ_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission();

        sp_from = (Spinner) findViewById(R.id.spinner_language_from);
        sp_to = (Spinner) findViewById(R.id.spinner_language_to);
        et_inp = (EditText) findViewById(R.id.text_input);
        tv_out = (TextView) findViewById(R.id.text_ouput);
        im_clear = (Button) findViewById(R.id.clear_text);
        lv = (ListView) findViewById(R.id.list_item);
        bt_speech = (Button) findViewById(R.id.speech_bt);
        aSwitch = (Switch) findViewById(R.id.sw);
        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (aSwitch.isChecked()) {

                    startService(new Intent(MainActivity.this, MyService.class));
                }
                else stopService(new Intent(MainActivity.this, MyService.class));
            }
        });
        localRepo = new LocalRepo(this);
        setAdapter();
        presenter = new Presenter(this,localRepo);
        sp_from.setOnItemSelectedListener(this);
        sp_to.setOnItemSelectedListener(this);
    }


    public void askPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }
    public void onClickClearButton(View view){
        tv_out.setText("");
        et_inp.setText("");
    }
    public void onClickSpeechButton(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry your device not supported",
                    Toast.LENGTH_SHORT).show();
        }
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
        list = localRepo.getAllTranslation();
        TranslateAdapter translateAdapter = new TranslateAdapter(list);
        lv.setAdapter(translateAdapter);
    }
    @Override
    public void updateView(String to, Translation translation) {
        tv_out.setText(to);
        list.add(translation);
    }
    public void onClickTransButton(View view){
        String inp = et_inp.getText().toString();
        if (inp != "") presenter.translate(inp,from,to);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_inp.setText(result.get(0).toString());
                    onClickTransButton(tv_out);
                }
                break;
            }
        }
    }

}
