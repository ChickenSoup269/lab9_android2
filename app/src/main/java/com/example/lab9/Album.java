package com.example.lab9;

public class Album {

    public Album(){

    }
    String title, artist, pictureLink;
    int numberOfTracks;

    public Album(String title, String artist, String pictureLink, int numberOfTracks) {
        this.title = title;
        this.artist = artist;
        this.pictureLink = pictureLink;
        this.numberOfTracks = numberOfTracks;
    }

    public String getTitle() {return title;}

    public String getArtist() {return artist;}

    public String getPictureLink() {return pictureLink;}

    public int getNumberOfTracks() {return numberOfTracks;}

    public void setTitle(String title) {this.title = title;}

    public void setArtist(String artist) {this.artist = artist;}

    public void setPictureLink(String pictureLink) {this.pictureLink = pictureLink;}

    public void setNumberOfTracks(int numberOfTracks) {this.numberOfTracks = numberOfTracks;}
}
