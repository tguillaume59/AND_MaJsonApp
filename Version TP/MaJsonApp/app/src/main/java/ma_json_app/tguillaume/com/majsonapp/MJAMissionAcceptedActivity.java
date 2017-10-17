package ma_json_app.tguillaume.com.majsonapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAMissionAcceptedActivity.java
 *
 * Created by TARTARA Guillaume on 15/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJAMissionAcceptedActivity extends AppCompatActivity {

    private final static String TAG = MJAMissionAcceptedActivity.class.getSimpleName();
    private TextView mTimerTextview;
    private String mTimerFirstPartMessage;
    private String mTimerEndPartMessage;

    private int mTimer = 15;

    //timer
    private final int interval = 1000; // 1 Second
    private Handler mHandler ;
    private Runnable mRunnable = new Runnable(){
        String tMessage;
        public void run() {
            mTimer --;
            tMessage = mTimerFirstPartMessage + " " +mTimer+ " " +mTimerEndPartMessage;
            if(mTimer > 1){
                tMessage+="s";
            }
            mTimerTextview.setText(tMessage);
            if(mTimer == 0){
                mHandler.removeCallbacks(mRunnable);
                backToLoginPage();
            }
            if(mTimer > 0){
                mHandler.postDelayed(this, interval);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_accepted);
        mTimerTextview = (TextView)findViewById(R.id.activity_mission_accepted_subtitle);
        mTimerFirstPartMessage = getString(R.string.activity_mission_accepted_subtitle);
        mTimerEndPartMessage = getString(R.string.activity_mission_accepted_subtitle_second);


        //mise en marche du timer
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable,interval);
    }

    private void backToLoginPage(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
