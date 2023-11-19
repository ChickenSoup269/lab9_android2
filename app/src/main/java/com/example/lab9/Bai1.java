package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bai1 extends AppCompatActivity {
    // Bài1
    FloatingActionButton fab1;
    ListView lvArtist;
    CustomAdapterArtist adapter;
    ArrayList<Artist> lvData=new ArrayList<>();
    ArrayList<Album> albumList = new ArrayList<>();
    ArrayList<Playlist> playlistList = new ArrayList<>();
    String artist = "https://soundiiz.com/data/fileExamples/artistsExport.json";
    String album = "https://soundiiz.com/data/fileExamples/albumsExport.json";
    String playlist = "https://soundiiz.com/data/fileExamples/playlistExport.json";
    Button btnArtist, btnAlbum, btnPlaylist;
    SearchView searchView;
    boolean showingArtist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        addControls();
        addEvents();
    }

    public void addControls(){
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        lvArtist= (ListView) findViewById(R.id.lvArtist);
        btnArtist = (Button) findViewById(R.id.btnArtist);
        btnAlbum = (Button) findViewById(R.id.btnAlbum);
        btnPlaylist = (Button) findViewById(R.id.btnPlaylist);
        searchView =  (SearchView) findViewById(R.id.searchView);
        getArtistData(artist);
        getAlbumData(album);
        getPlaylistData(playlist);
    }

    public void addEvents(){
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}
        });

        btnArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArtistData(artist);
                showingArtist = true;
            }
        });

        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlbumData(album);
                showingArtist = false;
            }
        });

        btnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPlaylistData(playlist);
                showingArtist = false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Trả lại dữ liệu khi người dùng sử dụng dấu "x" lần 2
                getArtistData(artist);
                getAlbumData(album);
                getPlaylistData(playlist);
                return false;
            }
        });
        // Sự kiện kéo lên xuống trên listView sẽ ẩn và hiện  FloatingActionButton
        lvArtist.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;
            boolean areButtonsVisible = true; // Biến để kiểm tra xem các button đã hiển thị hay ẩn

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    // ListView được kéo lên, ẩn các button
                    if (areButtonsVisible) {
                        fab1.hide();
                        searchView.setVisibility(View.GONE);
                        btnAlbum.setVisibility(View.GONE);
                        btnArtist.setVisibility(View.GONE);
                        btnPlaylist.setVisibility(View.GONE);
                        areButtonsVisible = false;
                    }
                } else if (mLastFirstVisibleItem > firstVisibleItem) {
                    // ListView được kéo xuống, hiển thị các button
                    if (!areButtonsVisible) {
                        fab1.show();
                        searchView.setVisibility(View.VISIBLE);
                        btnAlbum.setVisibility(View.VISIBLE);
                        btnArtist.setVisibility(View.VISIBLE);
                        btnPlaylist.setVisibility(View.VISIBLE);
                        areButtonsVisible = true;
                    }
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }

    private void filterData(String query) {
        if (showingArtist) {
            ArrayList<Artist> filteredArtists = new ArrayList<>();
            for (Artist artist : lvData) {
                if (artist.name.toLowerCase().contains(query.toLowerCase())) {
                    filteredArtists.add(artist);
                }
            }
            adapter.clear();
            adapter.addAll(filteredArtists);
            adapter.notifyDataSetChanged();
        } else {
            ArrayList<Album> filteredAlbums = new ArrayList<>();
            ArrayList<Playlist> filteredPlaylists = new ArrayList<>();

            for (Album album : albumList) {
                if (album.title.toLowerCase().contains(query.toLowerCase())) {
                    filteredAlbums.add(album);
                }
            }

            for (Playlist playlist : playlistList) {
                if (playlist.title.toLowerCase().contains(query.toLowerCase())) {
                    filteredPlaylists.add(playlist);
                }
            }

            if (lvArtist.getAdapter() instanceof CustomAdapterAlbum) {
                CustomAdapterAlbum albumAdapter = (CustomAdapterAlbum) lvArtist.getAdapter();
                albumAdapter.clear();
                albumAdapter.addAll(filteredAlbums);
                albumAdapter.notifyDataSetChanged();
            } else if (lvArtist.getAdapter() instanceof CustomAdapterPlaylist) {
                CustomAdapterPlaylist playlistAdapter = (CustomAdapterPlaylist) lvArtist.getAdapter();
                playlistAdapter.clear();
                playlistAdapter.addAll(filteredPlaylists);
                playlistAdapter.notifyDataSetChanged();
            }
        }
    }

    public void getArtistData (String url)
    {
        RequestQueue queue = Volley.newRequestQueue(Bai1.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJsonDataToArrayList(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void getAlbumData(String url) {
        RequestQueue queue = Volley.newRequestQueue(Bai1.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseAlbumJsonDataToArrayList(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error fetching album data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void getPlaylistData(String url) {
        RequestQueue queue = Volley.newRequestQueue(Bai1.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parsePlaylistJsonDataToArrayList(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error fetching playlist data!", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    // lấy dữ liệu trong file json response đưa vào lsData
    public void parseJsonDataToArrayList (String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        for(int i=0;i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Artist a = new Artist();
            a.name = jsonObject.getString("name");
            a.numberFans = jsonObject.getString("fans");
            a.pictureLink= jsonObject.getString("picture");
            lvData.add(a);
        }
        // gắn dữ liệu vào listView
        adapter= new CustomAdapterArtist(getApplicationContext(),
                R.layout.layout_item_artist,lvData);
        lvArtist.setAdapter(adapter);
    }

    public void parseAlbumJsonDataToArrayList(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Album album = new Album();
            album.title = jsonObject.getString("title");
            album.artist = jsonObject.getString("artist");

            if (jsonObject.has("nbTracks")) {
                album.numberOfTracks = jsonObject.getInt("nbTracks");
            } else {
                album.numberOfTracks = 0;
            }

            album.pictureLink = jsonObject.getString("picture");
            albumList.add(album);
        }

        if (!showingArtist) {
            CustomAdapterAlbum albumAdapter = new CustomAdapterAlbum(getApplicationContext(),
                    R.layout.layout_item_album, albumList);
            lvArtist.setAdapter(albumAdapter);
        }
    }
    public void parsePlaylistJsonDataToArrayList(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Playlist playlist = new Playlist();
            playlist.title = jsonObject.getString("title");
            playlist.artist = jsonObject.getString("artist");
            playlist.album = jsonObject.getString("album");
            playlist.pictureLink = jsonObject.getString("picture");
            playlistList.add(playlist);
        }

        if (!showingArtist) {
            CustomAdapterPlaylist playlistAdapter = new CustomAdapterPlaylist(getApplicationContext(),
                    R.layout.layout_item_playlist, playlistList);
            lvArtist.setAdapter(playlistAdapter);
        }
    }

}