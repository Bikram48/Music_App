package com.example.musicapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.musicapp.IndividualSongActivity;
import com.example.musicapp.Models.SongFiles;
import com.example.musicapp.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.musicapp.App.CHANNEL_ID;
import static com.example.musicapp.IndividualSongActivity.songFiles;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,MediaPlayer.OnInfoListener
{
    private static final String TAG = "MusicService";
    MediaPlayer mediaPlayer;
    Uri uri;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uri=Uri.parse(songFiles.get(IndividualSongActivity.position).getPath());
        Log.d(TAG,"Service working");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this );
    }

    //oncreate calls only for the first time you start a service whereas onstartcommand calls everytime you call the
    //startservice again. It let you set an action like play,stop,pause music.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Resets the mediaplayer to it's uninitialized state.
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(MusicService.this,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(false);
        Intent notificationIntent=new Intent(MusicService.this,IndividualSongActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(MusicService.this,0,notificationIntent,0);
        Notification builder=new NotificationCompat.Builder(MusicService.this,CHANNEL_ID).setSmallIcon(R.drawable.music)
                .setContentTitle("Music Playing").setContentText(songFiles.get(IndividualSongActivity.position).getTitle()).setContentIntent(pendingIntent).build();
        Toast.makeText(this, "Music started", Toast.LENGTH_SHORT).show();
        mediaPlayer.setOnPreparedListener(this::onPrepared);
        mediaPlayer.prepareAsync();
        startForeground(1,builder);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
        //releases all the memory that Mediaplayer used
        //it is called when the services are stopped using stopService() or stopself()
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}


