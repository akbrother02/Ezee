package com.ezee.Utils;

import android.app.Application;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezee.Models.DistrictModel;
import com.ezee.Models.ProductCategory;
import com.ezee.Models.StateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by info3 on 18-07-2017.
 */

public class BaseData extends Application {
    static boolean isLoggedIn;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    String Username="";
    static List<ProductCategory> Categorylist = new ArrayList<>();

    public static List<ProductCategory> getSub_CategortList() {
        return Sub_CategortList;
    }

    public static void setSub_CategortList(List<ProductCategory> sub_CategortList) {
        Sub_CategortList = sub_CategortList;
    }

    static List<ProductCategory> Sub_CategortList = new ArrayList<>();

    public static ArrayList<DistrictModel> getDistrictModels() {
        return districtModels;
    }

    public static void setDistrictModels(ArrayList<DistrictModel> districtModels) {
        BaseData.districtModels = districtModels;
    }

    static ArrayList<DistrictModel> districtModels=new ArrayList<>();


    public static void setStateModelList(ArrayList<StateModel> stateModelList) {
        BaseData.stateModelList = stateModelList;
    }

    public static ArrayList<StateModel> getStateModelList() {
        return stateModelList;
    }

    static ArrayList<StateModel> stateModelList=new ArrayList<>();

    static public List<ProductCategory> getCategorylist() {
        return Categorylist;
    }

    static public void setCategorylist(List<ProductCategory> meals) {
        BaseData.Categorylist = meals;
    }
    public void setToast(Toast toast) {
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(13);
    }

    static public void setLoggedInStatus(boolean status) {
        BaseData.isLoggedIn = status;
    }


}
