package vn.daikon.myapplication.model.textresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TextResponse {
    @Expose
    @SerializedName("detectedLanguage")
    public DetectedLanguage detectedLanguage;
    @Expose
    @SerializedName("translations")
    public Translation[] translation;
}
