package vn.daikon.myapplication.model.textresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {
    @Expose
    @SerializedName("to")
    public String to;
    @Expose
    @SerializedName("text")
    public String text;
}
