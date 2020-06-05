package vn.daikon.myapplication.model.textresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectedLanguage {
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("score")
    @Expose
    public String score;
}
