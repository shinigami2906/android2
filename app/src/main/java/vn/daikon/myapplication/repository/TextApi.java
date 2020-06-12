package vn.daikon.myapplication.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.daikon.myapplication.model.textrequest.TextRequest;
import vn.daikon.myapplication.model.textresponse.TextResponse;

public class TextApi extends AsyncTask<TextRequest,Void, TextResponse[]> {

    OkHttpClient client = null;
    String url = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public TextApi(){
        client = new OkHttpClient();
    }

    @Override
    protected TextResponse[] doInBackground(TextRequest... textRequests) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create( gson.toJson(textRequests), JSON );
        String from = textRequests[0].from;
        String to = textRequests[0].to;
        String url2 = url+"&from="+from+"&to="+to;
        Log.d("tuan", url2);
        Request request = new Request.Builder()
                .url(url2).addHeader("Ocp-Apim-Subscription-Key","d233c4cd0ae940858a9bfe1c676f160a")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            String x = response.body().string();
            Log.d("sssss",x);
            return   gson.fromJson(x, TextResponse[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
