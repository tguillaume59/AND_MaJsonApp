package ma_json_app.tguillaume.com.majsonapp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ma_json_app.tguillaume.com.majsonapp.R;
import ma_json_app.tguillaume.com.majsonapp.entities.MJAMission;

/**
 * @Project : AND_MaJsonApp
 *
 * MJAListMissionAdapter.java
 *
 * Created by TARTARA Guillaume on 13/10/2017
 * Copyright © 2017 tguillaume. All rights reserved.
 */

public class MJAListMissionAdapter extends ArrayAdapter<MJAMission> {

    private final static String TAG =MJAListMissionAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<MJAMission> mListMissions;

    private TextView mIdTextview,
    mNameTextview,
    mDateTextview,
    mTownTextview,
    mCountryTextview;


    public MJAListMissionAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<MJAMission> objects) {
        super(context, resource, objects);
        mContext = context;
        mListMissions = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rView = inflater.inflate(R.layout.item_listview_mission, parent, false);

        mIdTextview = (TextView)rView.findViewById(R.id.item_listview_mission_id_textview);
        mNameTextview = (TextView)rView.findViewById(R.id.item_listview_mission_name_textview);
        mDateTextview = (TextView)rView.findViewById(R.id.item_listview_mission_date_textview);
        mTownTextview = (TextView)rView.findViewById(R.id.item_listview_mission_town_textview);
        mCountryTextview = (TextView)rView.findViewById(R.id.item_listview_mission_country_textview);

        mIdTextview.setText(mContext.getString(R.string.item_list_mission_id)+ " " +mListMissions.get(position).getId());
        mNameTextview.setText(mContext.getString(R.string.item_list_mission_name)+ " " +mListMissions.get(position).getNom());
        mDateTextview.setText(mContext.getString(R.string.item_list_mission_date)+ " " +mListMissions.get(position).getDate());
        mTownTextview.setText(mContext.getString(R.string.item_list_mission_place)+ " " +mListMissions.get(position).getVille());
        mCountryTextview.setText("("+mListMissions.get(position).getPays()+")");


        return rView;
    }

}
