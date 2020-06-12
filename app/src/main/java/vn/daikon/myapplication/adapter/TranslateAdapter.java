package vn.daikon.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import java.util.List;

import vn.daikon.myapplication.R;
import vn.daikon.myapplication.model.Translation;

public class TranslateAdapter extends BaseAdapter {
    List<Translation> list ;
    public TranslateAdapter(List<Translation> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.translate_listview, null);
        } else viewProduct = convertView;

        //Bind sữ liệu phần tử vào View
        Translation translation = (Translation) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.tv_from)).setText(translation.getFrom());
        ((TextView) viewProduct.findViewById(R.id.tv_to)).setText( translation.getTo());
        ((TextView) viewProduct.findViewById(R.id.tv_fromto)).setText( translation.getFrom2()+"-"+translation.getTo2());


        return viewProduct;

    }
}
