package com.ezee.Utils;

import android.util.Log;

import com.ezee.Models.DistrictModel;
import com.ezee.Models.StateModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by info3 on 19-07-2017.
 */

public class JsonParsing {
    public static ArrayList<StateModel> ofTypeLocation(String json) {
        ArrayList<StateModel> locationList= new ArrayList<>();
        try {
            JSONObject responseJson = new JSONObject(json);
            if (responseJson.getString("msg").contains("successfully")) {
                JSONArray docsArray = responseJson.getJSONArray("State");

                for (int i = 0; i < docsArray.length(); i++) {

                    JSONObject jsonObject=docsArray.getJSONObject(i);
                    StateModel stateModel=new StateModel();
                    String name=jsonObject.getString("name");
                    int id=jsonObject.getInt("id");
                    stateModel.setName(name);
                    stateModel.setId(id);
                    locationList.add(stateModel);
                }
                BaseData.setStateModelList(locationList);
            } else {
                Log.d("Error", "Success in not 1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locationList;
    }

    public static ArrayList<DistrictModel> ofTypeDistrict(String json) {
        ArrayList<DistrictModel> locationList= new ArrayList<>();
        try {
            JSONObject responseJson = new JSONObject(json);
            if (responseJson.getString("msg").contains("successfully")) {
                JSONArray docsArray = responseJson.getJSONArray("District");

                for (int i = 0; i < docsArray.length(); i++) {

                    JSONObject jsonObject=docsArray.getJSONObject(i);
                    DistrictModel stateModel=new DistrictModel();
                    String name=jsonObject.getString("name");
                    int id=jsonObject.getInt("id");
                    stateModel.setName(name);
                    stateModel.setId(id);
                    locationList.add(stateModel);
                }
                BaseData.setDistrictModels(locationList);
            } else {
                Log.d("Error", "Success in not 1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return locationList;
    }
}
