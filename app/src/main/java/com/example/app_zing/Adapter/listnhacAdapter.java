package com.example.app_zing.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.app_zing.Fragment.canhan_fragment;
import com.example.app_zing.R;
import com.example.app_zing.activity.manhinhgiaodien;
import com.example.app_zing.activity.manhinhnhaccanhan;
import com.example.app_zing.modul.listnhac;

import java.util.ArrayList;
import java.util.List;

public class listnhacAdapter extends BaseAdapter {

    manhinhnhaccanhan context;
    private int layout;
    private List<listnhac> listnhacList;

    public listnhacAdapter(manhinhnhaccanhan context, int layout, List<listnhac> listnhacList) {
        this.context = context;
        this.layout = layout;
        this.listnhacList = listnhacList;
    }


    @Override
    public int getCount() {
        return listnhacList.size();
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
        ImageView anhcasi, xoa,sua;
        TextView tennhac,tencasinhac;

    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Viewholder holder;

        if (view == null){
            holder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //có inflater roiif vào view gắn layout cho nó
            view = inflater.inflate(layout, null);
            holder.anhcasi = view.findViewById(R.id.anhcasi);
            holder.tennhac = view.findViewById(R.id.tennhac);
            holder.tencasinhac = view.findViewById(R.id.tencasinhac);
            holder.xoa = view.findViewById(R.id.xoa);
            holder.sua = view.findViewById(R.id.sua);
            //giữ ánh sạ ở biến view
            view.setTag(holder);
        }else {
            holder = (Viewholder) view.getTag();
        }

        final listnhac listnhac = listnhacList.get(i);
        //chuyển mảng byte qua bitmap
        byte[] hinh = listnhac.getHinhcasi();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
        holder.anhcasi.setImageBitmap(bitmap);
        holder.tennhac.setText(listnhac.getTenbaihat());
        holder.tencasinhac.setText(listnhac.getCasi());

        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Dialogsuasp(listnhac.getHinhcasi(),listnhac.getTenbaihat(),listnhac.getCasi(),listnhac.getId());
            }
        });


        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.xoabainhac(listnhac.getTenbaihat(),listnhac.getId());
            }
        });


        return view;
    }
}
