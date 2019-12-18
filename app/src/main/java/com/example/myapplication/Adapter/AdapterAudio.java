package com.example.myapplication.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Tools.DataAudio;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAudio extends RecyclerView.Adapter<AdapterAudio.PageHolder> {

    private ArrayList<DataAudio> data;
    private Context context;

    public AdapterAudio(Context context,ArrayList<DataAudio> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.item_audio,parent,false);
        return new PageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, final int position) {
        final DataAudio dataAudio = data.get(position);

        holder.tv_judul.setText(dataAudio.getJudul());

        if (position%2==0){
            holder.ll_parent.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            holder.ll_parent.setBackgroundColor(Color.parseColor("#FFF7F7F7"));
        }

        int onPlay = ((MainActivity)context).getIndexOnPlay();
        int onPause = ((MainActivity)context).getIndexOnPause();

        if (onPlay==position){
            holder.rl_indicator.setVisibility(View.VISIBLE);
            holder.rl_indicator.setBackgroundColor(Color.parseColor("#4CAF50"));
        }else if(onPause==position){
            holder.rl_indicator.setVisibility(View.VISIBLE);
            holder.rl_indicator.setBackgroundColor(Color.parseColor("#F44336"));
        }else{
            holder.rl_indicator.setVisibility(View.INVISIBLE);
        }

        if (onPlay==position){
            holder.img_play.setImageResource(R.drawable.ic_pause_black_24dp);
        }else{
            holder.img_play.setImageResource(R.drawable.ic_play_black_24dp);
        }

        holder.img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cekStatusPlay = ((MainActivity)context).getIndexOnPlay();
                int cekStatusPause = ((MainActivity)context).getIndexOnPause();
                if (cekStatusPlay==position){
                    ((MainActivity)context).pauseAudio(position);
                }else if(cekStatusPause==position){
                    ((MainActivity)context).resumeAudio(position);
                }else{
                    ((MainActivity)context).playAudio(position);
                }
            }
        });

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cekStatusPlay = ((MainActivity)context).getIndexOnPlay();
                int cekStatusPause = ((MainActivity)context).getIndexOnPause();
                if (cekStatusPlay==position){
                    ((MainActivity)context).pauseAudio(position);
                }else if(cekStatusPause==position){
                    ((MainActivity)context).resumeAudio(position);
                }else{
                    ((MainActivity)context).playAudio(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class PageHolder extends RecyclerView.ViewHolder{

        private TextView tv_judul,tv_duration;
        private ImageView img_play;
        private LinearLayout ll_parent;
        private ProgressBar pb_duration;
        private RelativeLayout rl_indicator;

        public PageHolder(View itemView){
            super(itemView);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            img_play = itemView.findViewById(R.id.img_play);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            pb_duration = itemView.findViewById(R.id.pb_duration);
            rl_indicator = itemView.findViewById(R.id.rl_indicator);
        }
    }
}
