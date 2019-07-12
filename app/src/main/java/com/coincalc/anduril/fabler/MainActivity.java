package com.coincalc.anduril.fabler;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static boolean backable_a = true, backable_b = true;
    ConstraintLayout layout;
    AnimationDrawable animationDrawable;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            setContentView(R.layout.activity_main);
            layout = (ConstraintLayout) findViewById(R.id.layout);
        } else {
            setContentView(R.layout.activity_main_alt);   // simple 'View Stories' button
            layout = (ConstraintLayout) findViewById(R.id.layout_alt);
        }

        animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        MobileAds.initialize(this, getResources().getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void viewStories(View view)
    {
        Intent intent = ViewStories.makeIntent(MainActivity.this);
        startActivity(intent);
    }

    public void signUp(View view)
    {
        Intent intent = SignUpActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }

    public void signIn(View view)
    {
        Intent intent = SignInActivity.makeIntent(MainActivity.this);
        startActivity(intent);
    }

    public void signOut(View view)
    {
        FirebaseAuth.getInstance().signOut();
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "You've been signed out!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            setContentView(R.layout.activity_main_alt);
        }
    }

    @Override
    public void onBackPressed() {
        if(backable_a && backable_b)
        {
            super.onBackPressed();
        }
    }
}
