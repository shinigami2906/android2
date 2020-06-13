package vn.daikon.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.daikon.myapplication.adapter.TranslateAdapter;
import vn.daikon.myapplication.model.Translation;
import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;
import vn.daikon.myapplication.presenter.MyInterface;
import vn.daikon.myapplication.presenter.Presenter;
import vn.daikon.myapplication.repository.LocalRepo;
import vn.daikon.myapplication.repository.TextApi;

public class MainActivity extends AppCompatActivity implements MyInterface.View , AdapterView.OnItemSelectedListener {

    Spinner sp_from,sp_to;
    Button im_sw,im_clear;
    TextView tv_out;
    EditText et_inp;
    ListView lv;
    List<Translation> list ;
    String from = "en", to = "vi";
    LocalRepo localRepo;
    MyInterface.IPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_from = (Spinner) findViewById(R.id.spinner_language_from);
        sp_to = (Spinner) findViewById(R.id.spinner_language_to);
        et_inp = (EditText) findViewById(R.id.text_input);
        tv_out = (TextView) findViewById(R.id.text_ouput);
        im_clear = (Button) findViewById(R.id.clear_text);
        lv = (ListView) findViewById(R.id.list_item);
        localRepo = new LocalRepo(this);
        setAdapter();
        presenter = new Presenter(this,localRepo);
        sp_from.setOnItemSelectedListener(this);
        sp_to.setOnItemSelectedListener(this);


    }
    public void onClickClearButton(View view){
        tv_out.setText("");
        et_inp.setText("");
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

        presenter.translate(inp,from,to);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String x = "vi";
        if (position == 0) x = "vi";else x = "en";
        if (view.getId() == R.id.spinner_language_from) from = x; else to = x;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
