package com.example.lab9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterPlaylist extends ArrayAdapter<Playlist> {
    Context context;
    int resource;
    ArrayList<Playlist> playlistList;

    public CustomAdapterPlaylist(@NonNull Context context, int resource, ArrayList<Playlist> playlistList) {
        super(context, resource, playlistList);
        this.context = context;
        this.resource = resource;
        this.playlistList = playlistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Playlist playlist = playlistList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }

        ImageView imgPlaylist = convertView.findViewById(R.id.image_playlist);
        Picasso.with(context).load(playlist.getPictureLink())
                .resize(100, 100)
                .into(imgPlaylist);

        TextView tvTitle = convertView.findViewById(R.id.text_playlist_title);
        tvTitle.setText(playlist.getTitle());

        TextView tvArtist = convertView.findViewById(R.id.text_playlist_artist);
        tvArtist.setText(playlist.getArtist());

        TextView tvAlbum = convertView.findViewById(R.id.text_playlist_album);
        tvAlbum.setText(playlist.getAlbum());

        return convertView;
    }
}

