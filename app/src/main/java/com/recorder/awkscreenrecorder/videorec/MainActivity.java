package com.recorder.awkscreenrecorder.videorec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.recorder.awkscreenrecorder.R;
import com.recorder.awkscreenrecorder.UploadVideos.VideosActivity;


public class MainActivity extends Fragment{
    static Context context;
    View view;
    static final String EXTRA_VIDEO_PATH = "EXTRA_VIDEO_PATH";
    private static final String TAG = "MainActivityofvid";
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    private static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;


    LinearLayout uploadvideo, recordvideo;

//    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_vtrim, container, false);
        context = getContext();

        initViews();
        uploadvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, VideosActivity.class));
            }
        });

        recordvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(context, ActivityScreenR.class));
            }
        });
        return view;
    }

    private void initViews() {
        uploadvideo=view.findViewById(R.id.lytMain);
        recordvideo=view.findViewById(R.id.lytMain2);

    }



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_vtrim);
//
//        requestPermission();
//        Button galleryButton = (Button) findViewById(R.id.galleryButton);
//        if (galleryButton != null) {
//            galleryButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pickFromGallery();
//                }
//            });
//        }
//
//        Button recordButton = (Button) findViewById(R.id.cameraButton);
//        if (recordButton != null) {
//            recordButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openVideoCapture();
//                }
//            });
//        }
//    }
//
//    private void openVideoCapture() {
////        Intent videoCapture = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
////        startActivityForResult(videoCapture, REQUEST_VIDEO_TRIMMER);
//
//        final int durationLimit = 59;
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
//        startActivityForResult(intent, REQUEST_VIDEO_TRIMMER);
//    }
//
//    private void pickFromGallery() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.permission_read_storage_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//        } else {
//            Intent intent = new Intent();
//            intent.setTypeAndNormalize("video/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_VIDEO_TRIMMER) {
//                final Uri selectedUri = data.getData();
//                if (selectedUri != null) {
//                    startTrimActivity(selectedUri);
//                } else {
//                    Toast.makeText(MainActivity.this, R.string.toast_cannot_retrieve_selected_video, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//
//    private void startTrimActivity(@NonNull Uri uri) {
//        Intent intent = new Intent(this, TrimmerActivity.class);
//        intent.putExtra(EXTRA_VIDEO_PATH, FileUtils.getPath(this, uri));
//        startActivity(intent);
//    }
//
//    /**
//     * Requests given permission.
//     * If the permission has been denied previously, a Dialog will prompt the user to grant the
//     * permission, otherwise it is requested directly.
//     */
//    private void requestPermission(final String permission, String rationale, final int requestCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(getString(R.string.permission_title_rationale));
//            builder.setMessage(rationale);
//            builder.setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
//                }
//            });
//            builder.setNegativeButton(getString(R.string.label_cancel), null);
//            builder.show();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//        }
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    pickFromGallery();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

}
