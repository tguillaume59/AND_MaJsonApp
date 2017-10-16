package ma_json_app.tguillaume.com.majsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ma_json_app.tguillaume.com.majsonapp.wsManager.MJAWebServicesManager;

/**
 * @Project : AND_MaJsonApp
 *
 * MainActivity.java
 *
 * Created by TARTARA Guillaume on 11/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mLoginEditText;
    private EditText mMdpEditText;
    private Button mLoginBtn;
    private MJAWebServicesManager mWSManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instance du ws manager pour similuer le WS
        mWSManager = MJAWebServicesManager.getInstance(this);

        mLoginBtn = (Button)findViewById(R.id.activity_main_login_btn);
        mLoginEditText = (EditText)findViewById(R.id.activity_main_login_edittext);
        mMdpEditText = (EditText)findViewById(R.id.activity_main_mdp_edittext);

        mLoginBtn.setOnClickListener(this);

        //si tu es feneant comme moi et que tu veux pas toujours tout taper
        mLoginEditText.setText(getString(R.string.ws_manager_good_login));
        mMdpEditText.setText(getString(R.string.ws_manager_good_mdp));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /**
             * Clic sur le bouton connexion
             */
            case R.id.activity_main_login_btn:
                String tLogin = mLoginEditText.getText().toString();
                String tMdp = mMdpEditText.getText().toString();

                if(tLogin.isEmpty() || tMdp.isEmpty()){
                    Toast.makeText(this, "Merci de renseigner tous les champs", Toast.LENGTH_SHORT).show();
                    break;
                }

                //on appelle la fonction de créatin du JSON
                JSONObject tJsonLogin = createLoginJsonObject(tLogin,tMdp);
                //on appelle le WS <-- fictif ici
                String tResponse = mWSManager.startConnexionService(tJsonLogin);
                OnSuccessLoginResponse(tResponse);

                break;
        }
    }

    private JSONObject createLoginJsonObject(String sLogin, String sMdp){
        JSONObject rJsonObject = new JSONObject();
        try{
            rJsonObject.put(getString(R.string.json_login),sLogin);
            rJsonObject.put(getString(R.string.json_mdp),sMdp);
        }catch (Exception e){
            Log.e(TAG, "createLoginJsonObject : erreur --> " +e.getLocalizedMessage());
        }
        return rJsonObject;
    }

    private void OnSuccessLoginResponse(String sJsonReceive){
        try {
            JSONObject tJsonReceive = new JSONObject(sJsonReceive);
            String tMessage = tJsonReceive.getString("message");
            if((this.getString(R.string.ws_manager_success)).equals(tMessage)){
                //SUCCESS donc on passe à la vue suivante
                String tAgent = tJsonReceive.getString("agent");
                Intent tIntent = new Intent(this,MJAMissionActivity.class);
                tIntent.putExtra("BUNDLE_AGENT",tAgent);

                startActivity(tIntent);
            }else {
                //erreur donc on affiche le messagde d'erreur
                Toast.makeText(this, tMessage, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
