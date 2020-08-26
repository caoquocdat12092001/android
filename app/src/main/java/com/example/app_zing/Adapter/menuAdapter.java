package com.example.app_zing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_zing.R;
import com.example.app_zing.modul.itemmenu;

import java.util.List;

public class menuAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<itemmenu> itemmenuList;

    public menuAdapter(Context context, int layout, List<itemmenu> itemmenuList) {
        this.context = context;
        this.layout = layout;
        this.itemmenuList = itemmenuList;
    }

    @Override
    public int getCount() {
        return itemmenuList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class Viewholder{
        ImageView iconmenu;
        TextView tenmenu;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Viewholder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder = new Viewholder();
//            holder.iconmenu = view.findViewById(R.id.iconmn);
//            holder.tenmenu = view.findViewById(R.id.tenmn);
            view.setTag(holder);
        }else {
            holder = (Viewholder) view.getTag();
        }

        holder.tenmenu.setText(itemmenuList.get(i).tenitem);
        holder.iconmenu.setImageResource(itemmenuList.get(i).iconitem);
        return view;
    }
}
