package vn.daikon.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements MyInterface.View {

    Spinner sp_from,sp_to;
    ImageView im_sw,im_clear;
    TextView tv_out;
    EditText et_inp;
    ListView lv;
    List<Translation> list ;
    String from, to ;
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
        im_sw = (ImageView) findViewById(R.id.image_swap);
        im_clear = (ImageView) findViewById(R.id.clear_text);
        lv = (ListView) findViewById(R.id.list_item);
        localRepo = new LocalRepo(this);
        setAdapter();
        presenter = new Presenter(this,localRepo);


    }
    void setAdapter(){
        list = localRepo.getAllTranslation();
        TranslateAdapter translateAdapter = new TranslateAdapter(list);
        lv.setAdapter(translateAdapter);
    }


    @Override
    public void updateView(String to, Translation translation) {
        tv_out.setText(to);
        list.add(translation);
    }
    public void onClickTransImage(View view){
        String inp = et_inp.getText().toString();

        presenter.translate(inp,"en","vi");
    }
}
