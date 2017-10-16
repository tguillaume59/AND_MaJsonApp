package ma_json_app.tguillaume.com.majsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ma_json_app.tguillaume.com.majsonapp.adapter.MJAListMissionAdapter;
import ma_json_app.tguillaume.com.majsonapp.entities.MJAMission;
import ma_json_app.tguillaume.com.majsonapp.wsManager.MJAWebServicesManager;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAMissionActivity.java
 *
 * Created by TARTARA Guillaume on 11/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJAMissionActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private final static String  TAG = MJAMissionActivity.class.getSimpleName();
    private ListView mListviewMission;
    private Button mLogoutBtn;
    private String mAgentId;

    private ArrayList<MJAMission> mListMissions;
    private MJAListMissionAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        mListviewMission = (ListView)findViewById(R.id.activity_mission_listview);
        mLogoutBtn = (Button)findViewById(R.id.activity_mission_logout_btn);

        Bundle tBundle = this.getIntent().getExtras();
        if(tBundle != null){
            mAgentId = tBundle.getString("BUNDLE_AGENT");
        }

        //on link avec les listener
        mLogoutBtn.setOnClickListener(this);
        mListviewMission.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMissions(mAgentId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_mission_logout_btn:
                //on retourne sur la page de connexion
                this.onBackPressed();
                break;
        }
    }

    private void getMissions(String sAgentId){
        String tJsonMissions = MJAWebServicesManager.getInstance(this).startGetMissionsService(sAgentId);
        onReceiveResponse(tJsonMissions);
    }

    /**
     * simulation de la reponse à l'appel web
     * @param sJson le json recu
     */
    private void onReceiveResponse(String sJson){
        //on reinitialise la liste
        mListMissions = null;
        mListMissions = new ArrayList<>();

        try {
            JSONObject tJsonObjectReceive = new JSONObject(sJson);
            String tMessage = tJsonObjectReceive.getString(getString(R.string.json_message));

            if(getString(R.string.ws_manager_success).equals(tMessage)){
                //on recupere le tableau contenant toutes les missions
                JSONArray tJsonArrayMission = tJsonObjectReceive.getJSONArray(getString(R.string.json_mission_array));
                JSONObject tObjectRead;

                //on créé une liste contenant toutes les missions
                for(int i = 0 ; i <  tJsonArrayMission.length() ; i++){
                    //On récupere toutes les missions dans le json
                    tObjectRead = tJsonArrayMission.getJSONObject(i);
                    MJAMission tMission = new MJAMission(
                            tObjectRead.getInt(this.getString(R.string.json_mission_id)),
                            tObjectRead.getString(this.getString(R.string.json_mission_name)),
                            tObjectRead.getInt(this.getString(R.string.json_mission_date)),
                            tObjectRead.getString(this.getString(R.string.json_mission_town)),
                            tObjectRead.getString(this.getString(R.string.json_mission_country))

                    );
                    mListMissions.add(tMission);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "onReceiveResponse : erreur --> "+e.getLocalizedMessage());
        }
        mArrayAdapter = new MJAListMissionAdapter(this,R.layout.item_listview_mission,mListMissions);
        mListviewMission.setAdapter(mArrayAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent tIntent = new Intent(this, MJASelectEquipActivity.class);
        MJAMission tMission = mListMissions.get(i);
        tIntent.putExtra("MISSION_NAME", tMission.getNom());
        tIntent.putExtra("MISSION_ID",tMission.getId());
        tIntent.putExtra("AGENT_ID",mAgentId);

        startActivity(tIntent);
    }
}
