package com.recorder.awkscreenrecorder.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.recorder.awkscreenrecorder.R;
import com.recorder.awkscreenrecorder.Utills.Constance;


public class FragmentAboutUs extends Fragment {
    Context context;
    View view;
    TextView tv_aboutus;
    AdView mAdView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        context = getContext();

        tv_aboutus = view.findViewById(R.id.tv_aboutus);


        tv_aboutus.setText(Constance.aboutUs);


        return view;
    }


}
