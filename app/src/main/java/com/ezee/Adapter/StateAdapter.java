package com.ezee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ezee.Activity.SignupActivity;
import com.ezee.Models.DistrictModel;
import com.ezee.Models.StateModel;
import com.ezee.R;

import java.util.List;

import developer.shivam.perfecto.OnNetworkRequest;

/**
 * Created by info3 on 19-07-2017.
 */

public class StateAdapter extends BaseAdapter {
    Context context;
    List<StateModel> stateModels;
    List<DistrictModel> districtModels;



    public StateAdapter(Context context, List<StateModel> stateList) {
        this.context = context;
        this.stateModels = stateList;
    }

    public StateAdapter(SignupActivity context, List<DistrictModel> districtList) {
        this.context = context;
        this.districtModels = districtList;
    }


    @Override
    public int getCount() {
        return stateModels.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StateHolder holder;
        if (convertView == null) {
            holder = new StateHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.custom_spinner_layout, null);
            holder.name = (TextView) convertView.findViewById(R.id.localityName);
            convertView.setTag(holder);
        } else {
            holder = (StateHolder) convertView.getTag();
        }
        holder.name.setText(stateModels.get(position).getName().toString());

        return convertView;

    }


    static class StateHolder {
        TextView name;
    }
}
