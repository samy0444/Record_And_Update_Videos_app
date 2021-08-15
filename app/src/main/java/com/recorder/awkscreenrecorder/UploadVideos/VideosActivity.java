package com.recorder.awkscreenrecorder.UploadVideos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recorder.awkscreenrecorder.Adapters.AdapterVideo;
import com.recorder.awkscreenrecorder.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {

    FloatingActionButton addvedio;

    private ArrayList<ModelVideo> videoArrayList;
    private RecyclerView videosRv;
    private AdapterVideo adapterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        setTitle("Videos");
        addvedio=findViewById(R.id.addvido);
        videosRv = findViewById(R.id.videosRv);

        loadVediosfromfirebase();
        addvedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideosActivity.this,AddVideoActivity.class));
            }
        });
    }

    private void loadVediosfromfirebase() {
        videoArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                videoArrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    ModelVideo modelVideo = ds.getValue(ModelVideo.class);
                    Log.d("modelvideo", String.valueOf(modelVideo));

                    videoArrayList.add(modelVideo);
                }
                Log.d("arraylistvd", String.valueOf(videoArrayList));
                adapterVideo= new AdapterVideo(VideosActivity.this,videoArrayList);
                videosRv.setAdapter(adapterVideo);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}