package com.memegenerator.ultimatememes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {

    Button btn_classic1,btn_classic2,btn_classic3,btn_collage1,btn_collage2,btn_collage3,btn_collage4,btn_collage5,btn_collage6;
    private static final int PERMISSION_REQUEST = 1;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //set default template as main view
         showDefaultTemplate();

         //instantiate buttons
          getButtons();

          //deal with onclick events
           switchFragments();

           //handle the permissions
            permissionHandling();

    }

    private void permissionHandling() {

       if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                //request the permission
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST);

            }else{
                //request the permission
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST);
            }
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else {
                    Toast.makeText(this,"Permission denied!",Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

    private void switchFragments() {

        //classic1
        btn_classic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Classic1Template())
                        .commit();
            }
        });

        //classic2
        btn_classic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Classic2Template())
                        .commit();
            }
        });


        //collage1
        btn_collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Collage1Template())
                        .commit();
            }
        });

        //collage2
        btn_collage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Collage2Template())
                        .commit();
            }
        });



        //collage4
        btn_collage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Collage4Template())
                        .commit();
            }
        });

        //collage5
        btn_collage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_main_default,new Collage5Template())
                        .commit();
            }
        });


    }

    private void getButtons() {
        btn_classic1 = findViewById(R.id.btn_classic1);
        btn_classic2 = findViewById(R.id.btn_classic2);


        btn_collage1= findViewById(R.id.btn_collage1);
        btn_collage2= findViewById(R.id.btn_collage2);


        btn_collage4= findViewById(R.id.btn_collage4);
        btn_collage5= findViewById(R.id.btn_collage5);

    }

    public void showDefaultTemplate(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_main_default,new Classic1Template())
                .commit();
    }
}
