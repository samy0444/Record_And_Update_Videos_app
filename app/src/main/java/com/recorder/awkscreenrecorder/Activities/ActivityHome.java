package com.recorder.awkscreenrecorder.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.recorder.awkscreenrecorder.Fragment.FragmentAboutUs;
import com.recorder.awkscreenrecorder.Fragment.FragmentGallery;
import com.recorder.awkscreenrecorder.Fragment.FragmentHome;
import com.recorder.awkscreenrecorder.Fragment.FragmentMyRecording;
import com.recorder.awkscreenrecorder.Fragment.FragmentPrivacyPolicy;
import com.recorder.awkscreenrecorder.Fragment.FragmentSetting;
import com.recorder.awkscreenrecorder.Fragment.VtrimFragment;
import com.recorder.awkscreenrecorder.R;
import com.recorder.awkscreenrecorder.SettingsActivity;
import com.recorder.awkscreenrecorder.Utills.Constance;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener {

    Context context;
    //AdView mAdView;
    public InterstitialAd mInterstitialAd;
    public static ActivityHome instance = null;
    Timer timer;
    FragmentTransaction fragmentTransaction;
    TimerTask hourlyTask;
    private final String TAG = ActivityHome.class.getSimpleName();
    LinearLayout facbook_ad_banner;
    public com.facebook.ads.InterstitialAd interstitialFacbookAd;

    public ActivityHome() {
        instance = ActivityHome.this;
    }

    public static synchronized ActivityHome getInstance() {
        if (instance == null) {
            instance = new ActivityHome();
        }
        return instance;
    }

    DrawerLayout drawerLayout;
    ImageView iv_menuhome;
    RelativeLayout rl_toolbar;
    LinearLayout ll_maincontentlayout;
    TextView tv_titletoolbar;
    TextView tv_nav_home, tv_nav_aboutus, tv_nav_shareapp, tv_nav_moreapp, tv_nav_rateapp, tv_nav_privacypolicy, tv_nav_englishvideo, tv_nav_hindivideo, tv_nav_settings;
    int selectedcolor, unselectedcolor;

    String check_fragmentname;
    LinearLayout toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = ActivityHome.this;
        check_fragmentname = String.valueOf(getIntent().getStringExtra("check_fragmentname"));

        init();
//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new FragmentHome()).commit();
//        }
//        if(Constance.adType.equals("Ad Mob"))
//        {
//            foradvertise();
//
//        }
//        else {
//            facebookAd();
//        }
//        for_mInterstitialAd();
        selectedcolor = Color.parseColor("#E416A9");
        unselectedcolor = Color.parseColor("#66BC24");
        Log.d("check_intent", "homeactivity");

        tv_titletoolbar.setText("Home");
        //loadFragment(new FragmentHome());
        loadFragment(new VtrimFragment());
        iv_menuhome.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawerLayout.isDrawerOpen(Gravity.START))
                    drawerLayout.openDrawer(Gravity.START);
                else drawerLayout.closeDrawer(Gravity.END);
            }
        });


        if (check_fragmentname.equals("fragment_myrecording")) {
            tv_titletoolbar.setText("My Recording");
            loadFragment(new FragmentMyRecording());
        } else if (check_fragmentname.equals("FragmentGallery")) {
            tv_titletoolbar.setText("Gallery");
            Log.d("mfmfmf","ActivityHome");
            loadFragment(new FragmentGallery());
        } else {
            Log.d("aaaaaaa", "Invalid Fragment");
        }

    }



//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_home:
//                            selectedFragment = new FragmentHome();
//                            break;
//                        case R.id.nav_favorites:
//                            //toolbar.setVisibility(View.GONE);
//                            selectedFragment = new FragmentSetting();
//                            break;
//
//                    }
//
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment).commit();
//
//                    return true;
//                }
//            };
//

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_nav_home:
                tv_titletoolbar.setText("Home");
                loadFragment(new VtrimFragment());
//                if (Constance.AllowToOpenAdvertise) {
//                    if (Constance.adType.equals("Ad Mob")) {
//                        displayAdMob();
//                        Log.d("ADssss", "Ad Mob");
//                    } else {
//                        interstitialFacbookAd();
//                        Log.d("ADssss", "Facebook");
//                    }
//                }
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_nav_myrecording:
                tv_titletoolbar.setText("My Recordings");

                loadFragment(new FragmentMyRecording());
//                if (Constance.AllowToOpenAdvertise) {
//                    if (Constance.adType.equals("Ad Mob")) {
//                        displayAdMob();
//                        Log.d("ADssss", "Ad Mob");
//                    } else {
//                        interstitialFacbookAd();
//                        Log.d("ADssss", "Facebook");
//                    }
//                }
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_nav_mygallery:
                tv_titletoolbar.setText("Gallery");

                loadFragment(new FragmentGallery());
//                if (Constance.AllowToOpenAdvertise) {
//                    if (Constance.adType.equals("Ad Mob")) {
//                        displayAdMob();
//                        Log.d("ADssss", "Ad Mob");
//                    } else {
//                        interstitialFacbookAd();
//                        Log.d("ADssss", "Facebook");
//                    }
//                }
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_nav_aboutus:
                tv_titletoolbar.setText("About Us");
                loadFragment(new FragmentAboutUs());
//                if (Constance.AllowToOpenAdvertise) {
//                    if (Constance.adType.equals("Ad Mob")) {
//                        displayAdMob();
//                        Log.d("ADssss", "Ad Mob");
//                    } else {
//                        interstitialFacbookAd();
//                        Log.d("ADssss", "Facebook");
//                    }
//                }
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_nav_shareapp:
/*
                if (Constance.AllowToOpenAdvertise) {
                    displayAdMob();
                }*/
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, Constance.shareapp_url + " " + context.getPackageName());
                intent.setType("text/plain");
                context.startActivity(intent);
                drawerLayout.closeDrawers();


                break;
//            case R.id.rl_nav_moreapp:
//
//                try {
//                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
//                    Intent intentmoreapp = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intentmoreapp);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(Constance.rateapp)));
//                }
//                drawerLayout.closeDrawers();
//
//                break;
//            case R.id.rl_nav_rateapp:
//
//               /* if (Constance.AllowToOpenAdvertise) {
//                    displayAdMob();
//                }*/
//
//
//                try {
//                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
//                    Intent intentrate = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intentrate);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(Constance.rateapp)));
//                }
//                drawerLayout.closeDrawers();
//                break;
//            case R.id.rl_nav_privacypolicy:
//                tv_titletoolbar.setText("Privacy Policy");
//                loadFragment(new FragmentPrivacyPolicy());
//
////                if (Constance.AllowToOpenAdvertise) {
////                    if (Constance.adType.equals("Ad Mob")) {
////                        displayAdMob();
////                        Log.d("ADssss", "Ad Mob");
////                    } else {
////                        interstitialFacbookAd();
////                        Log.d("ADssss", "Facebook");
////                    }
////                }
//
//                drawerLayout.closeDrawers();
//
//                break;
            case R.id.rl_nav_setting:
                tv_titletoolbar.setText("Settings");

               // startActivity(new Intent(context, SettingsActivity.class));
                loadFragment(new FragmentSetting());

//                if (Constance.AllowToOpenAdvertise) {
//                    if (Constance.adType.equals("Ad Mob")) {
//                        displayAdMob();
//                        Log.d("ADssss", "Ad Mob");
//                    } else {
//                        interstitialFacbookAd();
//                        Log.d("ADssss", "Facebook");
//                    }
//                }

                drawerLayout.closeDrawers();

                break;

            default:
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

//    public void for_mInterstitialAd() {
//        //// AdMob Ad-------------------------
//
//
//        mInterstitialAd = new InterstitialAd(context);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_full_screen));
//        mInterstitialAd.loadAd(new AdRequest.Builder()
//                .addTestDevice("6A2D2B68A7166B0DE00868C6F74E8DB9")
//                .addTestDevice("88045C0A4BBC3C24FABBF3D543FC7C8C")
//                .addTestDevice("3BCC9944F0D7A19C3D3BEFCD7D8B3EDE")
//                .addTestDevice("D3662558A58B055494404223B20E0CA8")
//                .build());
//
//        if (mInterstitialAd != null) {
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//            }
//        }
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                /*if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }*/
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//
//                Constance.AllowToOpenAdvertise = false;
//
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//                Constance.AllowToOpenAdvertise = false;
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//                Constance.AllowToOpenAdvertise = false;
//
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//                Constance.AllowToOpenAdvertise = false;
//
//
//                mInterstitialAd.loadAd(new AdRequest.Builder()
//                        .addTestDevice("6A2D2B68A7166B0DE00868C6F74E8DB9")
//                        .addTestDevice("88045C0A4BBC3C24FABBF3D543FC7C8C")
//                        .addTestDevice("3BCC9944F0D7A19C3D3BEFCD7D8B3EDE")
//                        .addTestDevice("D3662558A58B055494404223B20E0CA8")
//                        .build());
//            }
//        });
//
//
//
//        AudienceNetworkAds.initialize(this);
//
//
//        //AdSettings.setIntegrationErrorMode(INTEGRATION_ERROR_CRASH_DEBUG_MODE);
//        // Toast.makeText(ActivityNewsPapersCategory.this,"id"+getResources().getString(R.string.facebook_interstitial_Ad),Toast.LENGTH_LONG).show();
//        interstitialFacbookAd = new com.facebook.ads.InterstitialAd(this, getResources().getString(R.string.facebook_interstitial_Ad));
//        AdSettings.setDebugBuild(true);
//        // AdSettings.addTestDevice("HASHED ID");
//        interstitialFacbookAd.setAdListener(new InterstitialAdListener() {
//            @Override
//            public void onInterstitialDisplayed(Ad ad) {
//                // Interstitial ad displayed callback
//                Log.e(TAG, "Interstitial ad displayed.");
//            }
//
//            @Override
//            public void onInterstitialDismissed(Ad ad) {
//                // Interstitial dismissed callback
//                Log.e(TAG, "Interstitial ad dismissed.");
//            }
//
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                // Ad error callback
//                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//                // Interstitial ad is loaded and ready to be displayed
//                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
//                // Show the ad
//                interstitialFacbookAd.show();
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                // Ad clicked callback
//                Log.d(TAG, "Interstitial ad clicked!");
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//                // Ad impression logged callback
//                Log.d(TAG, "Interstitial ad impression logged!");
//            }
//        });
//    }



    public void StartTimer() {
        timer = new Timer();
        hourlyTask = new TimerTask() {
            @Override
            public void run() {
                if (!Constance.isFirstTimeOpen) {
                    Constance.AllowToOpenAdvertise = true;
                    stopTask();
                } else {
                    Constance.isFirstTimeOpen = false;
                }
            }
        };

        Constance.isFirstTimeOpen = true;
        if (timer != null) {
            timer.schedule(hourlyTask, 0, 1000 * 60);
        }
    }

    public void stopTask() {
        if (hourlyTask != null) {

            Log.d("TIMER", "timer canceled");
            hourlyTask.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            final AlertDialog alertDialog = builder.create();
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setTitle("Close App ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();

                        }
                    })

                    .show();
        }
    }

    public void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menuhome = findViewById(R.id.iv_menuhome);
        tv_titletoolbar = findViewById(R.id.tv_titletoolbar);
        tv_nav_home = findViewById(R.id.tv_nav_home);
        tv_nav_aboutus = findViewById(R.id.tv_nav_aboutus);
        tv_nav_settings = findViewById(R.id.tv_nav_setting);
        tv_nav_shareapp = findViewById(R.id.tv_nav_shareapp);

//        tv_nav_moreapp = findViewById(R.id.tv_nav_moreapp);
//        tv_nav_rateapp = findViewById(R.id.tv_nav_rateapp);
//        tv_nav_privacypolicy = findViewById(R.id.tv_nav_privacypolicy);

//        mAdView = findViewById(R.id.adView);
//        facbook_ad_banner = findViewById(R.id.facbook_ad_banner);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("mxmxmxm", "onnewintent");
        checkAction(intent);
        super.onNewIntent(intent);
    }

    private void checkAction(Intent intent) {
        String action = intent.getAction();
        Log.d("mxmxmxm", "action: " + action);
        switch (action) {
            case "record_intent":
                Constance.notificationrecordclick=true;
                tv_titletoolbar.setText("Home");
                loadFragment(new FragmentHome());
                Log.d("mxmxmxm", "record_intent");
                // FragmentHome.exitNotificationclick();
                break;

        }

    }
//    public void foradvertise() {
//
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
//                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
//                .build();
//
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//        });
//
//        mAdView.loadAd(adRequest);
//    }

//    public void facebookAd() {
//
//        mAdView.setVisibility(View.GONE);
//        facbook_ad_banner.setVisibility(View.VISIBLE);
//        com.facebook.ads.AdView adFaceView = new com.facebook.ads.AdView(context, getResources().getString(R.string.facebook_banner_id), AdSize.BANNER_HEIGHT_50);
//
//        AdSettings.setDebugBuild(true);
//        //AdSettings.addTestDevice("HASHED ID");
//        // Find the Ad Container
//
//
//        // Add the ad view to your activity layout
//        facbook_ad_banner.addView(adFaceView);
//
//        // Request an ad
//        adFaceView.loadAd();
//
//    }
//    public void displayAdMob() {
//        if (ActivityHome.getInstance().mInterstitialAd != null) {
//            if (ActivityHome.getInstance().mInterstitialAd.isLoaded()) {
//                Log.d("shsjks","sdhsjkhd");
//                ActivityHome.getInstance().mInterstitialAd.show();
//            } else {
//            }
//        } else {
//
//        }
//    }
//
//    public void interstitialFacbookAd() {
//
//        if (ActivityHome.getInstance().interstitialFacbookAd != null) {
//            if (!ActivityHome.getInstance().interstitialFacbookAd.isAdLoaded()) {
//
//                AdSettings.setDebugBuild(true);
//                ActivityHome.getInstance().interstitialFacbookAd.loadAd();
//            } else {
//
//            }
//        } else {
//
//        }
//    }
}
