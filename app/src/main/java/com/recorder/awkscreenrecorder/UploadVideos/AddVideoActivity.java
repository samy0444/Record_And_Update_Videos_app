package com.recorder.awkscreenrecorder.UploadVideos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.recorder.awkscreenrecorder.R;
import com.recorder.awkscreenrecorder.videorec.TrimmerActivity;
import com.recorder.awkscreenrecorder.videorec.utils.FileUtils;
import com.recorder.hbrecorder.HBRecorder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;

public class AddVideoActivity extends AppCompatActivity {
    static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    private ActionBar actbar;
    private EditText titleEt;
    private VideoView videoview;
    private Button upload;
    private FloatingActionButton pickvid;
    private  static  final int VIDEO_PICK_GELLERY_CODE=100;
    private  static  final int VIDEO_PICK_Camera_CODE=101;
    private  static  final int CAMERA_REQUEST_CODE=102;
    private String[] camerapermiss;
    private Uri videoUri=null;
    private ProgressDialog progressDialog;
    private String  title;
    private HBRecorder hbRecorder;
    private String uritrim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        titleEt = findViewById(R.id.titleEt);
        videoview = findViewById(R.id.VideoView);
        upload = findViewById(R.id.uploadved);
        pickvid = findViewById(R.id.pickFabVid);
        //actbar = getSupportActionBar();
        //actbar.setTitle("Add New Video");
        //actbar.setDisplayHomeAsUpEnabled(true);
        //actbar.setDisplayHomeAsUpEnabled(true);

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("Uploading Video");
        progressDialog.setCanceledOnTouchOutside(false);


        Intent getIntent = getIntent();
        if (getIntent.hasExtra("uri")){
            //String titler = getIntent.getStringExtra("title");
            uritrim = getIntent.getStringExtra("uri");
            //Log.d("titleeee", titler);
            Log.d("uriiiitrimed", String.valueOf(Uri.fromFile(new File(uritrim))));



        }

        //title = titleEt.getText().toString().trim();

        camerapermiss= new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit = titleEt.getText().toString().trim();
                if(TextUtils.isEmpty(tit)){
                    Toast.makeText(AddVideoActivity.this,"Title is required......", Toast.LENGTH_SHORT).show();
                }
                else if(uritrim==null){
                    Toast.makeText(AddVideoActivity.this, "Pick a video before to upload...", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadVideoFirebase(tit);
//                    setOutputPath(Uri.fromFile(new File(uritrim)),tit);
//                    //setVideoToVideoView(uritrim);
//                    if (hbRecorder.wasUriSet()) {
//                        Toast.makeText(AddVideoActivity.this,"uploaded",Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(context, TrimmerActivity.class);
////                intent.putExtra("uri", FileUtils.getPath(context,mUri));
////                startActivity(intent);
//                        updateGalleryUri(Uri.fromFile(new File(uritrim)));
//                    } else {
//                        //showLongToast("saveddd222222");
//                        Toast.makeText(AddVideoActivity.this,"uploaded2222",Toast.LENGTH_SHORT).show();
//                        refreshGalleryFile();
//                    }

                }
            }
        });

        pickvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPickDialogue();
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void refreshGalleryFile() {
        MediaScannerConnection.scanFile(this,
                new String[]{hbRecorder.getFilePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    private void updateGalleryUri(Uri mUri) {
        contentValues.clear();
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0);
        this.getContentResolver().update(mUri, contentValues, null, null);
    }



    ContentResolver resolver;
    ContentValues contentValues;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setOutputPath(Uri mUri, String filename) {
        //String filename = generateFileName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            resolver = this.getContentResolver();
            contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "Screen Recorder");
            contentValues.put(MediaStore.Video.Media.TITLE, filename);
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
            mUri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            //FILE NAME SHOULD BE THE SAME
            hbRecorder.setFileName(filename);
            hbRecorder.setOutputUri(mUri);
        } else {
            createFolder();
            hbRecorder.setOutputPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "/Screen Recorder");
            //hbRecorder.setOutputPath(Constance.PathFileDirectory);
        }
    }

    private void createFolder() {
        File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "Screen Recorder");
        if (!f1.exists()) {
            if (f1.mkdirs()) {
                Log.i("Folder ", "created");
            }
        }

    }
    private void uploadVideoFirebase(String tit) {
        progressDialog.show();

        String timestamp = ""+System.currentTimeMillis();

        String filePathAddName = "Videos/" + "video_" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAddName);
        storageReference.putFile(Uri.fromFile(new File(uritrim)))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        if(uriTask.isSuccessful()){
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", "" + timestamp);
                            hashMap.put("title",""+ tit);
                            hashMap.put("timestamp",""+timestamp);
                            hashMap.put("videoUrl",""+ downloadUri);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                            reference.child(timestamp)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddVideoActivity.this, "video uploaded...", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddVideoActivity.this,"ye kya huaaa"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddVideoActivity.this,"ye kab huaa"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void videoPickDialogue() {

        //title = titleEt.getText().toString().trim();
//        if(TextUtils.isEmpty(title)){
//            Toast.makeText(AddVideoActivity.this,"Title is required......", Toast.LENGTH_SHORT).show();
//        }else{
        String[] options ={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Video From")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            if(!checkcamerapermiss()){
                                requestcamerapermiss();
                            }else{
                                videoPickCamera();
                            }
                        }else if(i==1){
                            videoPickGallery();
                        }
                    }
                })
                .show();
        //}


    }


    private void requestcamerapermiss(){
        ActivityCompat.requestPermissions(this, camerapermiss, CAMERA_REQUEST_CODE);
    }

    private boolean checkcamerapermiss(){
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WAKE_LOCK)== PackageManager.PERMISSION_GRANTED;
        return result1 && result2;
    }

    private  void videoPickGallery(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Videos"), VIDEO_PICK_GELLERY_CODE);

    }

    private void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,VIDEO_PICK_Camera_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1]== PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        videoPickCamera();
                    }else{
                        Toast.makeText(this, "Camera & storage permisssions are required", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.e("requestcodeee", String.valueOf(requestCode));
        Log.e("Resultcodeee", String.valueOf(resultCode));
        if(resultCode==RESULT_OK){
            if(requestCode==VIDEO_PICK_GELLERY_CODE){
                videoUri=data.getData();
                Log.e("uriiii", String.valueOf(videoUri));
                Intent intent = new Intent(this,TrimmerActivity.class);
                //intent.putExtra("title",title);
                intent.putExtra("uri",FileUtils.getPath(this, videoUri));
                startActivity(intent);
                //setVideoToVideoView();
            }
            else if(requestCode==VIDEO_PICK_Camera_CODE){
                videoUri= data.getData();
                //startTrimActivity(videoUri);
                Intent intent = new Intent(this,TrimmerActivity.class);
                //intent.putExtra("pickcode",VIDEO_PICK_Camera_CODE);
                intent.putExtra("uri",FileUtils.getPath(this, videoUri));
                startActivity(intent);
                //setVideoToVideoView();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startTrimActivity(@NonNull Uri uri) {
        Intent intent = new Intent(this, TrimmerActivity.class);
        intent.putExtra(EXTRA_VIDEO_PATH, FileUtils.getPath(this, uri));
        startActivity(intent);
    }



    private void setVideoToVideoView(String uri) {

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoview);
        //titleEt.setText(titler);
        videoview.setMediaController(mediaController);

        videoview.setVideoURI(Uri.fromFile(new File(uri)));
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoview.pause();
            }
        });
    }

    public boolean onSupportNavigateUP(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}