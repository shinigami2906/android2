package vn.daikon.myapplication.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

import vn.daikon.myapplication.model.Translation;
import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;
import vn.daikon.myapplication.repository.LocalRepo;
import vn.daikon.myapplication.repository.TextApi;

public class Presenter implements MyInterface.IPresenter {
    MyInterface.View view ;
    LocalRepo localRepo;
    public Presenter(MyInterface.View view, LocalRepo localRepo){
        this.localRepo=  localRepo;
        this.view = view;
    }
    @Override
    public void translate(String text, String from, String to ) {
        TextApi textApi = new TextApi();
        try {
            TextResponse[] textResponses = textApi.execute(new TextRequest(text,from,to)).get();
            int max = localRepo.getID() +1;

            Translation translation = new Translation(max,text,textResponses[0].translation[0].text,from,to);
            localRepo.addTranslation(translation);
            view.updateView(textResponses[0].translation[0].text, translation);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
