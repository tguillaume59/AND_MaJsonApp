package ma_json_app.tguillaume.com.majsonapp.entities;

/**
 * @Project : AND_MaJsonApp
 *
 * MJACar.java
 *
 * Created by TARTARA Guillaume on 13/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJACar {

    private String mBrand;
    private String mModel;

    public MJACar(String sBrand, String sModel) {
        this.mBrand = sBrand;
        this.mModel = sModel;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String sBrand) {
        this.mBrand = sBrand;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String sModel) {
        this.mModel = sModel;
    }
}
