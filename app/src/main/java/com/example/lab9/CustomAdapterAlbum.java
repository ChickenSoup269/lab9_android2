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

public class CustomAdapterAlbum extends ArrayAdapter<Album> {
    Context context;
    int resource;
    ArrayList<Album> albumList;

    public CustomAdapterAlbum(@NonNull Context context, int resource, ArrayList<Album> albumList) {
        super(context, resource, albumList);
        this.context = context;
        this.resource = resource;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Album album = albumList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }

        ImageView imgAlbum = convertView.findViewById(R.id.image_album);
        Picasso.with(context).load(album.getPictureLink())
                .resize(100, 100)
                .into(imgAlbum);

        TextView tvTitle = convertView.findViewById(R.id.text_album_title);
        tvTitle.setText(album.getTitle());

        TextView tvArtist = convertView.findViewById(R.id.text_album_artist);
        tvArtist.setText(album.getArtist());

        TextView tvTracks = convertView.findViewById(R.id.text_album_tracks);
        String tracksText = context.getResources().getQuantityString(R.plurals.numberOfTracks, album.getNumberOfTracks(), album.getNumberOfTracks());
        tvTracks.setText(tracksText);

        return convertView;
    }
}
