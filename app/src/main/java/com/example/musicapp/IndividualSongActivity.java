package com.example.musicapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicapp.Models.SongFiles;
import com.example.musicapp.RecyclerViewAdapter.RecyclerViewAdapter;
import com.example.musicapp.services.MusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class IndividualSongActivity extends AppCompatActivity{
    public static int  position;
    TextView songTitle,artist,title;
    public static ArrayList<SongFiles> songFiles;
    FloatingActionButton playButton;
    ImageView songImage;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_song);
        Intent intent=getIntent();
        initViews();
        position=intent.getIntExtra("position",0);
        songFiles= RecyclerViewAdapter.songFiles;
        setText();
        initializeListener();
    }

    private void initializeListener() {
        Intent intent1=new Intent(IndividualSongActivity.this, MusicService.class);
        intent1.putExtra("position",position);
        playButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (isMyServiceRunning(MusicService.class)) {
                    Toast.makeText(IndividualSongActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    stopService(intent1);
                }
                else {
                    Toast.makeText(IndividualSongActivity.this, "Hello Bikram", Toast.LENGTH_SHORT).show();
                    playButton.setImageResource(R.drawable.ic_baseline_pause_24);
                    startService(intent1);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void initViews(){
        songTitle=findViewById(R.id.songTitle);
        artist=findViewById(R.id.artist);
        songImage=findViewById(R.id.songImage);
        title=findViewById(R.id.title);
        playButton=findViewById(R.id.playButton);
        seekBar=findViewById(R.id.seekBar);
        title.setSelected(true);
        songTitle.setSelected(true);
        artist.setSelected(true);
    }

    public void setText(){
        title.setText(songFiles.get(position).getTitle());
        songTitle.setText(songFiles.get(position).getTitle());
        artist.setText(songFiles.get(position).getArtist());
        byte[] image=getAlbumArt(songFiles.get(position).getPath());
        if(image!=null){
            Glide.with(this).asBitmap().load(image).into(songImage);
        }
        else{
            Glide.with(this).load(R.drawable.crowd).into(songImage);
        }
    }


    public byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}