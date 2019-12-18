package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.myapplication.Adapter.AdapterAudio;
import com.example.myapplication.Tools.DataAudio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ArrayList<DataAudio> dataAudios;
    private RecyclerView rv_listAudio;
    private int indexOnPlay = -1;
    private int indexOnPause = -1;
    private int lengthAudio=0;
    private MediaPlayer mediaplayer;
    private DatabaseReference database;
    private AdapterAudio adapterAudio;
    private Boolean onFocus=false;
    private  ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");
        dialog.setCancelable(false);

        database = FirebaseDatabase.getInstance().getReference();

        rv_listAudio = findViewById(R.id.rv_listAudio);

        setShowLoading();
        getAudio();
    }

    public int getIndexOnPlay() {
        return indexOnPlay;
    }

    public int getIndexOnPause() {
        return indexOnPause;
    }

    public void playAudio(int index) {
        if (indexOnPlay == -1) {
            this.indexOnPlay = index;
        } else {
            stopAudio(indexOnPlay);
            this.indexOnPlay = index;
        }
        this.indexOnPause = -1;
        this.lengthAudio = 0;
        mediaplayer = new MediaPlayer();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaplayer.setDataSource(dataAudios.get(index).getUrl());
            mediaplayer.prepare();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                MainActivity.this.indexOnPlay = -1;
                setDataAudios();
            }
        });
        mediaplayer.start();
        setDataAudios();
    }

    public void stopAudio(int index) {
        if (indexOnPlay == index) {
            this.indexOnPlay = -1;
        }
        this.indexOnPause = -1;
        mediaplayer.stop();
        setDataAudios();
    }

    public void pauseAudio(int index) {
        this.indexOnPause = index;
        this.indexOnPlay=-1;
        this.lengthAudio = mediaplayer.getCurrentPosition();
        mediaplayer.pause();
        setDataAudios();
    }

    public void resumeAudio(int index){
        this.indexOnPlay=index;
        this.indexOnPause = -1;
        mediaplayer.seekTo(lengthAudio);
        mediaplayer.start();
        setDataAudios();
    }

    public int getDuration(){
        return mediaplayer.getDuration();
    }

    public void setShowLoading(){
        dialog.show();
    }

    public void setHideLoading(){
        dialog.dismiss();
    }

    private void getAudio() {
        database.child("audio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (onFocus==false){
                    dataAudios = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        DataAudio dataAudio = new DataAudio();
                        String getKey = ds.getKey();
                        dataAudio.setJudul(ds.child("judul").getValue().toString());
                        dataAudio.setUrl(ds.child("url").getValue().toString());

                        dataAudios.add(dataAudio);
                    }
                    setDataAudios();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDataAudios(){
        if (onFocus==false){
            rv_listAudio.setLayoutManager(new LinearLayoutManager(this));
            adapterAudio = new AdapterAudio(this,dataAudios);
            rv_listAudio.setAdapter(adapterAudio);
            adapterAudio.notifyDataSetChanged();
            onFocus=true;
            setHideLoading();
        }else{
            adapterAudio.notifyDataSetChanged();
        }
    }
}
