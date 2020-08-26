package com.example.app_zing.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_zing.Adapter.listnhacAdapter;
import com.example.app_zing.R;
import com.example.app_zing.activity.manhinhgiaodien;
import com.example.app_zing.modul.listnhac;
import com.example.app_zing.modul.song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link canhan_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class canhan_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<song> arrayList;
    int position = 0;
    MediaPlayer mediaPlayer;
    TextView tenbaihat,tgbatdau,tgketthuc;
    SeekBar thoigianchay;
    ImageButton btnphat;
    ImageView dianhac;
    Animation animation;
    Animation animation1;



    public canhan_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment canhan_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static canhan_fragment newInstance(String param1, String param2) {
        canhan_fragment fragment = new canhan_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canhan_fragment, container, false);
        tenbaihat = view.findViewById(R.id.tenbaihat);
        tgbatdau = view.findViewById(R.id.tgbatdau);
        tgketthuc = view.findViewById(R.id.tgketthuc);
        ImageButton btnquaylai = view.findViewById(R.id.btnquaylai);
        btnphat = view.findViewById(R.id.btnphat);
        final ImageButton btnnext = view.findViewById(R.id.btnnext);
        thoigianchay = view.findViewById(R.id.thoigianchay);
        dianhac = view.findViewById(R.id.dianhac);
        addsong();
        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.xoaydia);
        animation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.dung);
        khoitao();


        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0){
                    position =  arrayList.size() - 1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitao();
                mediaPlayer.start();
                btnphat.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                thoigianbaihat();
                updatethoigian();
                dianhac.startAnimation(animation);

            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position > arrayList.size() - 1){
                    position = 0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                khoitao();
                mediaPlayer.start();
                btnphat.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                thoigianbaihat();
                updatethoigian();
                dianhac.startAnimation(animation);

            }
        });

        btnphat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    //nếu đang phát thì dừng + đổi hình
                    mediaPlayer.pause();
                    btnphat.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    dianhac.startAnimation(animation1);
                }else {
                    mediaPlayer.start();
                    btnphat.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    dianhac.startAnimation(animation);
                }
                thoigianbaihat();
                updatethoigian();
            }
        });

        thoigianchay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(thoigianchay.getProgress());
            }
        });


        return view;
    }

    private void thoigianbaihat(){
        SimpleDateFormat dinhdangtg = new SimpleDateFormat("mm:ss");
        tgketthuc.setText(dinhdangtg.format(mediaPlayer.getDuration()));
        // gán max
        thoigianchay.setMax(mediaPlayer.getDuration());

    }
    private void updatethoigian(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdangtg = new SimpleDateFormat("mm:ss");
                tgbatdau.setText(dinhdangtg.format(mediaPlayer.getCurrentPosition()));
                thoigianchay.setProgress(mediaPlayer.getCurrentPosition());
                //ktra thời gian bài hát nếu hết thì next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arrayList.size() - 1){
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        khoitao();
                        mediaPlayer.start();
                        btnphat.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        thoigianbaihat();
                        updatethoigian();

                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    private void khoitao() {
        mediaPlayer = MediaPlayer.create(getActivity(),arrayList.get(position).getFile());
        tenbaihat.setText(arrayList.get(position).getTitle());
    }

    private void addsong() {
        arrayList = new ArrayList<>();
        arrayList.add(new song("Bạc phận",R.raw.bacphan));
        arrayList.add(new song("Bên anh đêm nay",R.raw.benanhdemnay));
        arrayList.add(new song("Bigcityboi",R.raw.bigcitynoi));
        arrayList.add(new song("Chúng ta không thuộc về nhau",R.raw.chungtakhongthuocvenhau));
        arrayList.add(new song("Độ ta không độ nàng",R.raw.dotakhongdonang));
        arrayList.add(new song("Duyên trời lấy",R.raw.duyentroilay));
        arrayList.add(new song("Em của ngày hôm qua",R.raw.emcuangayhomqua));
        arrayList.add(new song("Hồng nhan",R.raw.hongnhan));
        arrayList.add(new song("1001 lý do",R.raw.motlydo));
        arrayList.add(new song("Nắng ấm xa dần",R.raw.nangamxadan));
        arrayList.add(new song("Nguyên team đi vào hết",R.raw.nguyenteamdivaohet));
        arrayList.add(new song("Tháng năm không quên",R.raw.thangnamkhongquen));
        arrayList.add(new song("Trú mưa",R.raw.trumua));
        arrayList.add(new song("Tướng quân",R.raw.tuongquan));

    }
}
