package com.ezee.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezee.R;
import com.ezee.Utils.BaseData;
import com.ezee.Utils.ConstantValues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

/**
 * Created by info3 on 24-07-2017.
 */

public class OtpActivity extends Activity implements View.OnClickListener {
    SharedPreferences preferences;
    EditText editText;
    BaseData baseData;
    TextView msg;
    Button submit;
    String text;
    String id;
    public static String tag = "LOGIN_DETAILS";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverify_layout);
        editText = (EditText) findViewById(R.id.etOtp);
        submit = (Button) findViewById(R.id.btSubmit);
        msg = (TextView) findViewById(R.id.txt);
        preferences = getSharedPreferences(tag, Context.MODE_PRIVATE);
        editor = preferences.edit();
        id = preferences.getString("id", null);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit:
                ValidateAndSend();
                break;
        }
    }

    private void ValidateAndSend() {
         text = editText.getText().toString();
        id = preferences.getString("id", null);
        if (text.equals(null) && text.length() > 5 && text.length() < 5)
            Toast.makeText(OtpActivity.this, "Please Enter the Correct OTP", Toast.LENGTH_LONG).show();
        else
        {
            SubmitOtp();
        }


//            try {
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("tag", "otp_user_registration");
//                jsonObject.put("user_id", id);
//                jsonObject.put("otp", text);
//                Log.d("UserRequest",jsonObject.toString());
//                Perfecto.with(OtpActivity.this)
//                        .fromUrl(ConstantValues.BaseUrl)
//                        .ofTypePost(jsonObject)
//                        .connect(new OnNetworkRequest() {
//
//                            @Override
//                            public void onStart() {
//
//                            }
//
//                            @Override
//                            public void onSuccess(String s) {
//                                try {
//                                    String fullname = "", Usershopname = "", contact = "",UserShopnumber="",DistrictName="",StateName="",StateId="",DistrictId="";
//                                    JSONObject response = new JSONObject(s);
//                                    if (response.has("msg")) {
//                                        if (response.getString("msg").contains("User Registered successfully")) {
//                                            JSONObject userDetails = response.getJSONObject("User");
//                                            if (userDetails.has("fullname"))
//                                                fullname = userDetails.getString("fullname");
//                                            if (userDetails.has("shopname"))
//                                                Usershopname = userDetails.getString("shopname");
//                                            if (userDetails.has("shopnumber"))
//                                                UserShopnumber = userDetails.getString("shopnumber");
//                                            if (userDetails.has("district_name"))
//                                                DistrictName = userDetails.getString("district_name");
//                                            if(userDetails.has("state_name"))
//                                                StateName=userDetails.getString("state_name");
//                                            if(userDetails.has("state_id"))
//                                                StateId=userDetails.getString("state_id");
//                                            if(userDetails.has("district_id"))
//                                                DistrictId=userDetails.getString("district_id");
//                                            if (userDetails.has("contact"))
//                                                contact = userDetails.getString("contact");
//                                            if (userDetails.has("id"))
//                                                id = userDetails.getString("id");
//                                            editor.putString("userName", fullname);
//                                            editor.putString("email", Usershopname);
//                                            editor.putString("number", contact);
//                                            editor.putString("district_id",DistrictId);
//                                            editor.putString("district_name",DistrictName);
//                                            editor.putString("shopnumber",UserShopnumber);
//                                            editor.putString("state_name",StateName);
//                                            editor.putString("state_id",StateId);
//                                            editor.putString("id", id);
//                                            editor.putBoolean("isLoggedIn", true);
//                                            editor.commit();
//                                            Toast toast = Toast.makeText(OtpActivity.this, "Thank you for filling out your information!", Toast.LENGTH_SHORT);//.show();
//                                            baseData.setToast(toast);
//                                            toast.show();
//                                            Intent intent = new Intent(OtpActivity.this, MainCategoryActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            startActivity(intent);
//                                        } else if (response.getString("msg").contains("already existed")) {
//                                            Toast.makeText(OtpActivity.this, "Already Registered User", Toast.LENGTH_LONG).show();
//                                            msg.setVisibility(View.VISIBLE);
//                                            msg.setText("Already Registered User");
//                                        }
//
//                                    }
//
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(int i, String s, String s1) {
//
//                            }
//                        });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }







    private void SubmitOtp() {


        Map<String, String> map = new HashMap<String, String>();
        map.put("tag", "otp_user_registration");
        map.put("user_id", id);
        map.put("otp", text);
        Log.e("REQUEST>>>>", map.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ConstantValues.BaseUrl, new JSONObject(map),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                                    String fullname = "", Usershopname = "", contact = "",UserShopnumber="",DistrictName="",StateName="",StateId="",DistrictId="";

                                    if (response.has("msg")) {
                                        if (response.getString("msg").contains("OTP verified")) {
                                            JSONObject userDetails = response.getJSONObject("User");
                                            if (userDetails.has("name"))
                                                fullname = userDetails.getString("name");
                                            if (userDetails.has("shopname"))
                                                Usershopname = userDetails.getString("shopname");
                                            if (userDetails.has("shopnumber"))
                                                UserShopnumber = userDetails.getString("shopnumber");
                                            if (userDetails.has("district_name"))
                                                DistrictName = userDetails.getString("district_name");
                                            if(userDetails.has("state_name"))
                                                StateName=userDetails.getString("state_name");
                                            if(userDetails.has("state_id"))
                                                StateId=userDetails.getString("state_id");
                                            if(userDetails.has("district_id"))
                                                DistrictId=userDetails.getString("district_id");
                                            if (userDetails.has("contact"))
                                                contact = userDetails.getString("contact");
                                            if (userDetails.has("id"))
                                                id = userDetails.getString("id");
                                            editor.putString("userName", fullname);
                                            editor.putString("email", Usershopname);
                                            editor.putString("number", contact);
                                            editor.putString("district_id",DistrictId);
                                            editor.putString("district_name",DistrictName);
                                            editor.putString("shopnumber",UserShopnumber);
                                            editor.putString("state_name",StateName);
                                            editor.putString("state_id",StateId);
                                            editor.putString("id", id);
                                            editor.putBoolean("isLoggedIn", true);
                                            editor.commit();
                                            Toast toast = Toast.makeText(OtpActivity.this, "Thank you for filling out your information!", Toast.LENGTH_SHORT);//.show();

                                            toast.show();
                                            Intent intent = new Intent(OtpActivity.this, MainCategoryActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        } else if (response.getString("msg").contains("already existed")) {
                                            Toast.makeText(OtpActivity.this, "Already Registered User", Toast.LENGTH_LONG).show();
                                            msg.setVisibility(View.VISIBLE);
                                            msg.setText("Already Registered User");
                                        }

                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();

                                }



                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR>>>>", error.toString());

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);

        requestQueue.add(jsonObjReq);
    }
}


