package com.example.musicapp.FragmentAdapter;

import android.content.Context;
import android.database.Cursor;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musicapp.Models.SongFiles;

import java.util.ArrayList;

public class FragmentsAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<String> pageTitles=new ArrayList<>();
    public static ArrayList<SongFiles> songFiles=new ArrayList<>();
    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment fragment,String title){
        fragments.add(fragment);
        pageTitles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public static ArrayList<SongFiles> getAllAudio(Context context){
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection=new String[]{MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION};
        Cursor c=context.getContentResolver().query(uri,projection,null,null,null);
        if(c.moveToFirst()) {
            while (c.moveToNext()) {
                String title=c.getString(0);
                String path=c.getString(1);
                String album=c.getString(2);
                String artist=c.getString(3);
                String duration=c.getString(4);

                SongFiles songList=new SongFiles(title,path,album,artist,duration);
                songFiles.add(songList);
            }
            c.close();
        }
        return songFiles;
    }
}
