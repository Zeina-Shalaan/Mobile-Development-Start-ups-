package com.example.boundmusicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MyService extends Service {
    private final IBinder binder = new LocalBinder();
    private MediaPlayer mediaplayer ;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
    public void playMusic(){
        if(mediaplayer == null)
        {
            mediaplayer = MediaPlayer.create(this,R.raw.fayrouz_song);
        }
        if(mediaplayer != null){
            mediaplayer.start();
        }
    }

    public void pauseMusic(){
        if(mediaplayer != null && mediaplayer.isPlaying() == true){
            mediaplayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaplayer.release();
    }
}