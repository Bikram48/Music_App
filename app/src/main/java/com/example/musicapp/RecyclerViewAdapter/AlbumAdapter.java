package com.example.musicapp.RecyclerViewAdapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Models.SongFiles;
import com.example.musicapp.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    ArrayList<SongFiles> songFiles;
    Context context;
    public AlbumAdapter(Context context){
        this.context=context;
        songFiles=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.album_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.textView1.setText(songFiles.get(position).getAlbum());
        byte[] image=getAlbumArt(songFiles.get(position).getPath());
        if(image!=null){
            Glide.with(context).asBitmap().load(image).into(holder.imageView);
        }
        else{
            Glide.with(context).load(R.drawable.music).into(holder.imageView);
        }
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
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
            layout=itemView.findViewById(R.id.layout);
        }
    }
}
