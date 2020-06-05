package vn.daikon.myapplication.model.textrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextRequest {
    @Expose
    @SerializedName("text")
    public String text;
    public TextRequest(String text) {
        this.text = text;
    }
}
