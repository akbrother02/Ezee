package com.ezee.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ezee.Models.Product;
import com.ezee.R;
import com.ezee.Utils.ConstantValues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

/**
 * Created by info3 on 25-07-2017.
 */

public class OrderActivity extends Activity {
    ArrayList<Product> orderHistories = new ArrayList<>();
    ListView orderlist;
    SharedPreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        orderlist = (ListView) findViewById(R.id.listView);
        preferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);
        getOrderHistory();


    }

    private void getOrderHistory() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag", "get_order_details");
            jsonObject.put("user_id", preferences.getString("id",null));
            jsonObject.put("cat_id", "2");

            Perfecto.with(OrderActivity.this)
                    .fromUrl(ConstantValues.BaseUrl)
                    .ofTypePost(jsonObject)
                    .connect(new OnNetworkRequest() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            try {
                                JSONObject OrderResponse = new JSONObject(s);
                                if (OrderResponse.getString("msg").contains("Order listing successfully")) {
                                    JSONArray OrderJson = OrderResponse.getJSONArray("Order");
                                    for (int i = 0; i < OrderJson.length(); i++) {
                                        JSONObject jsonObject1 = OrderJson.getJSONObject(i);
                                        Product product = new Product();
                                        if (jsonObject1.has("order_id"))
                                            product.setOrderID(jsonObject1.getString("order_id"));
                                        if (jsonObject1.has("order_status"))
                                            product.setOrderStatus(jsonObject1.getString("order_status"));
                                        if (jsonObject1.has("order_by"))
                                            product.setOrderBy(jsonObject1.getString("order_by"));
                                        if (jsonObject1.has("order_date"))
                                            product.setDate(jsonObject1.getString("order_date"));
                                        if (jsonObject1.has("time"))
                                            product.setOrderTime(jsonObject1.getString("time"));
                                        orderHistories.add(product);
                                        orderlist.setAdapter(new OrderAdapter(OrderActivity.this, orderHistories));


                                    }

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s, String s1) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OrderAdapter extends BaseAdapter {

        Context context;
        ArrayList<Product> OrderHistories;

        public OrderAdapter(Context context, ArrayList<Product> orderHistories) {
            this.context = context;
            this.OrderHistories = orderHistories;
        }

        @Override
        public int getCount() {
            return OrderHistories.size();
        }

        @Override
        public Object getItem(int position) {
            return OrderHistories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ProductHolder holder;

            if (convertView == null) {
                holder = new ProductHolder();
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                convertView = inflater.inflate(R.layout.order_row_item, null);
                holder.itemName = (TextView) convertView.findViewById(R.id.items);
                holder.itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
                holder.dateTime = (TextView) convertView.findViewById(R.id.itemDate);
                holder.itemId = (TextView) convertView.findViewById(R.id.itemId);
                holder.itemStatus = (TextView) convertView.findViewById(R.id.ordStatus);
                convertView.setTag(holder);
        } else {
            holder = (ProductHolder) convertView.getTag();
        }

            holder.itemId.setText(OrderHistories.get(position).getOrderID());
            // holder.itemName.setText(name[0].replace("[", "").toString().replace('"', ' ') + " (" + name[1] + ")");
            ;
            holder.itemName.setText(OrderHistories.get(position).getProductName());
            holder.dateTime.setText(OrderHistories.get(position).getDate()+"   "+ OrderHistories.get(position).getOrderTime());
            holder.itemPrice.setText(convertView.getResources().getString(R.string.Rupee) +" "+ OrderHistories.get(position).getProductPrice());
            holder.itemStatus.setText(OrderHistories.get(position).getOrderStatus());



            return convertView;
        }
    }

    static class ProductHolder {
        TextView itemPrice;
        TextView dateTime;
        TextView itemId;
        TextView itemName;
        TextView itemStatus;

    }
}

