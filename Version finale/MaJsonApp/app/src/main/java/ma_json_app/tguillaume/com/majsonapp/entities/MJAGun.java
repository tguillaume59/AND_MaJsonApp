package ma_json_app.tguillaume.com.majsonapp.entities;

import java.util.ArrayList;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAGun.java
 *
 * Created by TARTARA Guillaume on 13/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJAGun {

    private String mManufacter;
    private String mModel;
    private ArrayList<MJAGunEquipment> mListEquipment;

    public MJAGun(String sManufacter, String sModel, ArrayList<MJAGunEquipment> sListEquipment) {
        this.mManufacter = sManufacter;
        this.mModel = sModel;
        this.mListEquipment = sListEquipment;
    }

    public String getManufacter() {
        return mManufacter;
    }

    public String getModel() {
        return mModel;
    }

    public ArrayList<MJAGunEquipment> getListEquipment() {
        return mListEquipment;
    }
}
