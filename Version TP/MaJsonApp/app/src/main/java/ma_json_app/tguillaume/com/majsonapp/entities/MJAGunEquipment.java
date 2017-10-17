package ma_json_app.tguillaume.com.majsonapp.entities;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAGunEquipment.java
 *
 * Created by TARTARA Guillaume on 13/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJAGunEquipment {

    private String mName;
    private String mType;

    public MJAGunEquipment(String sName, String sType) {
        this.mName = sName;
        this.mType = sType;
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }
}
