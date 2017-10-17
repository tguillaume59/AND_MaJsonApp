package ma_json_app.tguillaume.com.majsonapp.wsManager;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAWebServicesManager.java
 *
 * Created by TARTARA Guillaume on 11/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ma_json_app.tguillaume.com.majsonapp.R;

/**
 * Cette classe permet de similuer le comportement du web service
 * nous allons passer par celle ci pour interroger notre web service fictif et obtenir une
 * réponse.
 */

public class MJAWebServicesManager {

    private final static String TAG = MJAWebServicesManager.class.getSimpleName();
    private static MJAWebServicesManager mInstance;
    private Context mContext;

    private MJAWebServicesManager(Context sContext){
        mContext = sContext;
    }

    public static MJAWebServicesManager getInstance(Context sContext){
        if(mInstance == null){
            mInstance = new MJAWebServicesManager(sContext);
        }
        return mInstance;
    }


    public String startConnexionService(JSONObject sJsonLogin) {

        String tLogin = "",tMdp ="", rMessage = "utilisateur ou mot de passe incorrect",rAgent = "999",rJson = "";
        int rCode = 400;

        try {
            if(sJsonLogin != null){
                tLogin = sJsonLogin.getString(mContext.getString(R.string.json_login));
                tMdp = sJsonLogin.getString(mContext.getString(R.string.json_mdp));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(tLogin.equals(mContext.getString(R.string.ws_manager_good_login))){
            if(tMdp.equals(mContext.getString(R.string.ws_manager_good_mdp))){
                rCode = 200;
                rMessage = mContext.getString(R.string.ws_manager_success);
                rAgent = "007";
            }
        }
        rJson = "{\"code\":"+rCode+",\"message\":\""+rMessage+"\",\"agent\":\""+rAgent+"\"}";
        return rJson;
    }


    /**
     * simule l'appel au WS pour recuperer les missions pour un agent donné
     * @param sAgentId l'id de l'agent envoyé de type POST sur un vrai WS
     * @return le string de retour contenant toutes les missions
     */
    public String startGetMissionsService(String sAgentId){
        String rJson = "{\"message\":\"SUCCESS\",\"code\":200,\"agent\":\"007\", \"missions\":["
        +"{\"id\":1,\"nom\":\"Spectre\",\"date\": 2015,\"ville\":\"Rome\",\"pays\":\"Italie\""
        +"},{\"id\":2,\"nom\":\"Skyfall\",\"date\": 2012,\"ville\":\"Londres\",\"pays\":\"Angleterre\""
        +"},{\"id\":3,\"nom\":\"Quantum of Solace\",\"date\": 2008,\"ville\":\"Bregenz\",\"pays\":\"Autriche\""
        + "},{\"id\":4,\"nom\":\"Casino Royale\",\"date\": 2006,\"ville\":\"Nassau\",\"pays\":\"Bahmas\"}]}";

        return rJson;
    }

    public String startMissionEquipmentSelectedService(String sJson){
        String tAgentId = "",tMissionName ="", rMessage = "équipement non configuré correctement",rJson = "";
        int rCode = 400;
        JSONObject tCar = null, tGun=null;

        try {
            JSONObject tJsonObject = new JSONObject(sJson);
            if(tJsonObject != null){
                tAgentId = tJsonObject.getString("agent_id");
                tMissionName = tJsonObject.getString("mission_name");
                tCar = tJsonObject.getJSONObject("car");
                tGun = tJsonObject.getJSONObject("weapon");
            }
            if(tAgentId != null && tMissionName != null && tCar != null && tGun != null){
                // ici doit se trouver un
                // gros algorithme digne du MI6
                // qui va determiner le taux de deception de Q
                // quand Bond aura encore détruit la voiture
                // et celui de M quand il fera autre chose avec une femme
                // que de completer sa mission ;)

                rMessage = mContext.getString(R.string.ws_manager_success);
                rCode = 200;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rJson = "{\"code\":"+rCode+",\"message\":\""+rMessage+"\"}";
        return rJson;
    }
}
