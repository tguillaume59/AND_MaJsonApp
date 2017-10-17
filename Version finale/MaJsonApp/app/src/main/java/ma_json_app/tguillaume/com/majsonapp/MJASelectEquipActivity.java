package ma_json_app.tguillaume.com.majsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ma_json_app.tguillaume.com.majsonapp.entities.MJACar;
import ma_json_app.tguillaume.com.majsonapp.entities.MJAGun;
import ma_json_app.tguillaume.com.majsonapp.entities.MJAGunEquipment;
import ma_json_app.tguillaume.com.majsonapp.wsManager.MJAWebServicesManager;

/**
 * @Project : AND_MaJsonApp
 *
 * MJASelectEquipActivity.java
 *
 * Created by TARTARA Guillaume on 13/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJASelectEquipActivity extends AppCompatActivity implements OnClickListener{

    private static final String TAG = MJASelectEquipActivity.class.getSimpleName();
    private LinearLayout mGun1Layout,mGun2Layout,mGun3Layout, mGun1TitleLayout,mGun2TitleLayout,mGun3TitleLayout;
    private LinearLayout mCar1Layout,mCar2Layout,mCar3Layout,mCar1TitleLayout,mCar2TitleLayout,mCar3TitleLayout;

    private LinearLayout mCurrentCarLayoutSelect, mCurrentCarLayoutTitleSelect,mCurrentGunLayoutSelect, mCurrentGunLayoutTitleSelect;
    private Button mStartMissionBtn;
    private MJAGun mCurrentGunSelected;
    private MJACar mCurrentCarSelected;

    private String mMissionName, mAgentId;
    private int mMissionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_equip);

        Bundle tBundle = this.getIntent().getExtras();
        if(tBundle != null){
            mMissionId = tBundle.getInt("MISSION_ID");
            mMissionName = tBundle.getString("MISSION_NAME");
            mAgentId = tBundle.getString("AGENT_ID");
        }

        mGun1Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon1_layout);
        mGun2Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon2_layout);
        mGun3Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon3_layout);
        mGun1TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon1_title_layout);
        mGun2TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon2_title_layout);
        mGun3TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_weapon3_title_layout);

        mCar1Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_car1_layout);
        mCar2Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_car2_layout);
        mCar3Layout = (LinearLayout)findViewById(R.id.activity_equipment_select_car3_layout);
        mCar1TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_car1_title_layout);
        mCar2TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_car2_title_layout);
        mCar3TitleLayout = (LinearLayout)findViewById(R.id.activity_equipment_select_car3_title_layout);

        mStartMissionBtn = (Button)findViewById(R.id.activity_equipment_start_mission_btn);
        mStartMissionBtn.setOnClickListener(this);

        mGun1Layout.setOnClickListener(this);
        mGun2Layout.setOnClickListener(this);
        mGun3Layout.setOnClickListener(this);

        mCar1Layout.setOnClickListener(this);
        mCar2Layout.setOnClickListener(this);
        mCar3Layout.setOnClickListener(this);

    }

    /**
     * Permet de gerer l'affichage du layout selectionné selon l'arme choisie
     */
    private void selectItemGun(LinearLayout sContainerLayout, LinearLayout sTitleLayout){
        //on remet l'ancienne selection en blanc
        if(mCurrentGunLayoutSelect != null) {
            mCurrentGunLayoutSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_layout));
        }
        if(mCurrentGunLayoutTitleSelect != null) {
            mCurrentGunLayoutTitleSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_background_title_item_mission_layout));
        }
        mCurrentGunLayoutSelect = sContainerLayout;
        mCurrentGunLayoutTitleSelect = sTitleLayout;

        //on selectionne le nouveau layout
        mCurrentGunLayoutSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_select_border_layout));
        mCurrentGunLayoutTitleSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_select_background_title_item_mission_layout));
    }

    /**
     * Permet de gerer l'affichage du layout selectionné selon la voiture choisie
     */
    private void selectItemCar(LinearLayout sContainerLayout, LinearLayout sTitleLayout){
        //on remet l'ancienne selection en blanc
        if(mCurrentCarLayoutSelect != null) {
            mCurrentCarLayoutSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_border_layout));
        }
        if(mCurrentCarLayoutTitleSelect != null) {
            mCurrentCarLayoutTitleSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_background_title_item_mission_layout));
        }
        mCurrentCarLayoutSelect = sContainerLayout;
        mCurrentCarLayoutTitleSelect = sTitleLayout;

        //on selectionne le nouveau layout
        mCurrentCarLayoutSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_select_border_layout));
        mCurrentCarLayoutTitleSelect.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_select_background_title_item_mission_layout));
    }

    /**
     * Permet de creer le JSON à envoyer au WS pour valider le choix de l'équipement par rapport
     * à la mission choisie
     * @return le json créé sous forme de String
     */
    private String createJsonDataEquipmentsSelected(){
        Log.i(TAG, "createJsonDataEquipmentsSelected");
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        //le json final
        HashMap<String, Object> tJsonMap = new HashMap<>();
        //on ajoute les données de premier niveau
        tJsonMap.put("agent_id",mAgentId);
        tJsonMap.put("mission_id",mMissionId);
        tJsonMap.put("mission_name",mMissionName);

        //on créé le niveau 2 : l'arme avec ses équipement
        HashMap<String, Object> tJsonGun = new HashMap<>();
        tJsonGun.put("gun_manufacter",mCurrentGunSelected.getManufacter());
        tJsonGun.put("gun_model",mCurrentGunSelected.getModel());
        tJsonGun.put("gun_equipments",mCurrentGunSelected.getListEquipment());
        //on ajoute le niveau 2 dans le niveau 1
        tJsonMap.put("weapon",tJsonGun);

        //on créé un objet voiture
        HashMap<String, Object> tJsonCar = new HashMap<>();
        tJsonCar.put("car_brand",mCurrentCarSelected.getBrand());
        tJsonCar.put("car_model",mCurrentCarSelected.getModel());
        //on ajoute la voiture dans le premier niveau
        tJsonMap.put("car",tJsonCar);

        //on transforme la HashMap en un string JSON
        String rJson = gson.toJson(tJsonMap);
        return rJson;

    }

    private void onSuccessSelectEquipementWS(String sResponseJson){

        try {
            JSONObject tJsonObject = new JSONObject(sResponseJson);
            String tMessage = tJsonObject.getString("message");
            if(getString(R.string.ws_manager_success).equals(tMessage)){
                //on affiche la vue finale
                Intent tIntent = new Intent(this,MJAMissionAcceptedActivity.class);
                startActivity(tIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_equipment_select_weapon1_layout:
                selectItemGun(mGun1Layout, mGun1TitleLayout);
                ArrayList<MJAGunEquipment> tListGun1 = new ArrayList<>();
                MJAGunEquipment tGun1Equip1 = new MJAGunEquipment("lampe","tactique");
                tListGun1.add(tGun1Equip1);
                mCurrentGunSelected = new MJAGun("Walter","PP9",tListGun1);
                break;

            case R.id.activity_equipment_select_weapon2_layout:
                selectItemGun(mGun2Layout, mGun2TitleLayout);
                ArrayList<MJAGunEquipment> tListGun2 = new ArrayList<>();
                MJAGunEquipment tGun2Equip1 = new MJAGunEquipment("poignée","tactique");
                MJAGunEquipment tGun2Equip2 = new MJAGunEquipment("RedDot","viseur");
                tListGun2.add(tGun2Equip1);
                tListGun2.add(tGun2Equip2);
                mCurrentGunSelected = new MJAGun("Colt","M4A1",tListGun2);
                break;

            case R.id.activity_equipment_select_weapon3_layout:
                selectItemGun(mGun3Layout, mGun3TitleLayout);
                ArrayList<MJAGunEquipment> tListGun3 = new ArrayList<>();
                MJAGunEquipment tGun3Equip1 = new MJAGunEquipment("silencieux","cache flamme");
                MJAGunEquipment tGun3Equip2 = new MJAGunEquipment("RedDot","viseur");
                tListGun3.add(tGun3Equip1);
                tListGun3.add(tGun3Equip2);
                mCurrentGunSelected = new MJAGun("Colt","M4A1",tListGun3);
                break;

            case R.id.activity_equipment_select_car1_layout:
                selectItemCar(mCar1Layout, mCar1TitleLayout);
                mCurrentCarSelected = new MJACar("Pfister","Comet");
                break;

            case R.id.activity_equipment_select_car2_layout:
                selectItemCar(mCar2Layout, mCar2TitleLayout);
                mCurrentCarSelected = new MJACar("Grotti","Bestia GTS");
                break;
            case R.id.activity_equipment_select_car3_layout:
                selectItemCar(mCar3Layout, mCar3TitleLayout);
                mCurrentCarSelected = new MJACar("Schafter","V12");
                break;

            case R.id.activity_equipment_start_mission_btn:
                if(mCurrentCarSelected == null || mCurrentGunSelected == null){
                    //aucun choix de fait
                    Toast.makeText(this, "Choisissez une arme et une voiture pour commencer la mission", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tJson = createJsonDataEquipmentsSelected();
                //envoie au WS normalement
                String tJsonResponse = MJAWebServicesManager.getInstance(this).startMissionEquipmentSelectedService(tJson);
                //si SUCCESS : mission validé donc on passe à la derniere vue
                onSuccessSelectEquipementWS(tJsonResponse);

                break;
        }
    }
}
