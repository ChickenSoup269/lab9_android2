package com.example.lab9;

public class Playlist {

    public Playlist(){

    }

    String title, artist, album, pictureLink;

    public Playlist(String title, String artist, String album, String pictureLink) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.pictureLink = pictureLink;
    }

    public String getTitle() {return title;}

    public String getArtist() {return artist;}

    public String getAlbum() {return album;}

    public String getPictureLink() {return pictureLink;}

    public void setTitle(String title) {this.title = title;}

    public void setArtist(String artist) {this.artist = artist;}

    public void setAlbum(String album) {this.album = album;}

    public void setPictureLink(String pictureLink) {this.pictureLink = pictureLink;}
}
