package com.example.musicapp.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.IndividualSongActivity;
import com.example.musicapp.Models.SongFiles;
import com.example.musicapp.R;
import com.example.musicapp.SongsFragment;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<SongFiles> songFiles;
    Context context;
    public RecyclerViewAdapter(Context context){
        this.context=context;
        songFiles=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.music_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songTitle.setText(songFiles.get(position).getTitle());
        byte[] image=getAlbumArt(songFiles.get(position).getPath());
        if(image!=null){
            Glide.with(context).asBitmap().load(image).into(holder.songImage);
        }
        else{
            Glide.with(context).load(R.drawable.music).into(holder.songImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, IndividualSongActivity.class);
                context.startActivity(intent);
            }
        });
    }
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art=retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }

    public void setSongFiles(ArrayList<SongFiles> songFiles){
        this.songFiles=songFiles;
    }



    @Override
    public int getItemCount() {
        return songFiles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView songImage;
        TextView songTitle;
        LinearLayout songItemsLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songImage=itemView.findViewById(R.id.songImage);
            songTitle=itemView.findViewById(R.id.songTitle);
            songItemsLayout=itemView.findViewById(R.id.songItemsLayout);
        }
    }
}
