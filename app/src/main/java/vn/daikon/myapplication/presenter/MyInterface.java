package vn.daikon.myapplication.presenter;

import android.util.Pair;

import java.util.List;

import vn.daikon.myapplication.model.Translation;

public interface MyInterface {
    public interface IPresenter {
        public void translate(String text, String from, String to );
    }
    public interface View {
        public void updateView(String to, Translation translation);
    }
}
