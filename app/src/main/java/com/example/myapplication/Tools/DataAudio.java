package com.example.myapplication.Tools;

import java.io.Serializable;

public class DataAudio implements Serializable {

    private String judul;
    private String url;

    public DataAudio(){

    }

    public String getJudul(){
        return judul;
    }

    public void setJudul(String mJudul){
        this.judul=mJudul;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String mUrl){
        this.url=mUrl;
    }
}
