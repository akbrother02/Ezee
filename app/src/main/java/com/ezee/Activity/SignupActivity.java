package com.ezee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ezee.Adapter.DistrictAdpater;
import com.ezee.Adapter.StateAdapter;
import com.ezee.Models.DistrictModel;
import com.ezee.Models.StateModel;
import com.ezee.R;
import com.ezee.Utils.BaseData;
import com.ezee.Utils.ConstantValues;
import com.ezee.Utils.JsonParsing;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etUserName, etContact, etPassword, etShopname, etShopNumber;
    Spinner spState, spDistrict;
    BaseData baseData;
    TextView msg;
    Button submit;
    int Spinnervalue,spinnerDistrict;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<StateModel> StateList = new ArrayList<>();
    List<DistrictModel> DistrictList = new ArrayList<>();
    String name, number, password, shopname, shopnumber, spinnerstate;
    Boolean isCorrectName, isCorrectNumber, isCorrectPassword, isShopNumber, isShopName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        WidgetInitialization();
        GetSpinnerValue();
        spState.setOnItemSelectedListener(this);
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistrictList = BaseData.getDistrictModels();
                spinnerDistrict = DistrictList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(this);
    }

    private void GetSpinnerValue() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag", "get_state");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Perfecto.with(SignupActivity.this)
                .fromUrl(ConstantValues.BaseUrl)
                .ofTypePost(jsonObject)
                .connect(new OnNetworkRequest() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String s) {
                        String[] locationArray = null;
                        try {
                            StateList = JsonParsing.ofTypeLocation(s);
                            StateAdapter stateAdapter = new StateAdapter(SignupActivity.this, StateList);
                            spState.setAdapter(stateAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, String s, String s1) {

                    }
                });

    }

    private void WidgetInitialization() {
        etUserName = (EditText) findViewById(R.id.input_firstname);
        etContact = (EditText) findViewById(R.id.input_contact);
        etPassword = (EditText) findViewById(R.id.input_password);
        etShopname = (EditText) findViewById(R.id.input_shopname);
        etShopNumber = (EditText) findViewById(R.id.input_shopnumber);
        spState = (Spinner) findViewById(R.id.spinner_state);
        preferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        msg = (TextView) findViewById(R.id.txt);
        baseData = (BaseData) getApplicationContext();
        submit = (Button) findViewById(R.id.signupbutton);
        spDistrict = (Spinner) findViewById(R.id.spinner_distric);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        StateList = BaseData.getStateModelList();
        spinnerstate = StateList.get(position).getName();
        Spinnervalue = StateList.get(position).getId();
        getDistrict(spinnerstate, Spinnervalue);
    }

    private void getDistrict(String name, int value) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag", "get_district");
            jsonObject.put("state_id", value);
            jsonObject.put("state_name", name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Perfecto.with(SignupActivity.this)
                .fromUrl(ConstantValues.BaseUrl)
                .ofTypePost(jsonObject)
                .connect(new OnNetworkRequest() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            DistrictList = JsonParsing.ofTypeDistrict(s);
                            DistrictAdpater stateAdapter = new DistrictAdpater(SignupActivity.this, DistrictList);
                            spDistrict.setAdapter(stateAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int i, String s, String s1) {

                    }
                });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signupbutton:
                ValidateAndSubmit();
        }

    }

    private void ValidateAndSubmit() {
        name = etUserName.getText().toString();

        if (name.equals("") || name.equals(null)) {
            etUserName.requestFocus();
            etUserName.setError("Field Mandatory");
        } else if (name.matches("^[\\p{L} .'-]+$")) {
            Log.d("User Name", name);
            isCorrectName = true;
        } else {
            etUserName.requestFocus();
            etUserName.setError("Enter a valid userName");
        }

        number = etContact.getText().toString();
        if (number.equals("") || number.equals(null)) {
            etContact.requestFocus();
            etContact.setError("Field Mandatory");
        } else if (Patterns.PHONE.matcher(number).matches() && number.length() == 10) {
            Log.d("User Number", number);
            isCorrectNumber = true;
        } else {
            etContact.requestFocus();
            etContact.setError("Enter a valid number");
        }


        password = etPassword.getText().toString();
        if (password.equals("") || password.equals(null)) {
            etPassword.requestFocus();
            etPassword.setError("Field Mandatory");
        } else if (password.length() > 8 && password.length() < 20) {
            Log.d("User Password", password);
            isCorrectPassword = true;
        } else {
            etPassword.requestFocus();
            etPassword.setError("Length should be greater than 8");
        }


        shopnumber = etShopNumber.getText().toString();
        if (shopnumber.equals("") || shopnumber.equals(null)) {
            etShopNumber.requestFocus();
            etShopNumber.setError("Field Mandatory");
        } else {
            Log.d("User Shopnumber", shopnumber);
            isShopNumber = true;
        }

        shopname = etShopname.getText().toString();
        if (shopname.equals("") || shopname.equals(null)) {
            etShopname.requestFocus();
            etShopname.setError("Field Mandatory");
        } else {
            Log.d("User Shopnumber", shopname);
            isShopName = true;
        }

        if (isCorrectName && isCorrectNumber && isCorrectPassword && isShopName && isShopNumber) {
            Register(name, number, password, shopnumber, shopname, Spinnervalue, spinnerDistrict);
        }
    }

    private void Register(String name, String number, String password, String shopnumber, String shopname, int spinnerstate, int spinnerDistrict) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("tag", "user_register");
            jsonObject.put("fullname", name);
            jsonObject.put("shopname", shopname);
            jsonObject.put("contact", number);
            jsonObject.put("password", password);
            jsonObject.put("shop_number", shopnumber);
            jsonObject.put("district", spinnerDistrict);
            jsonObject.put("state", spinnerstate);
            Perfecto.with(SignupActivity.this)
                    .fromUrl(ConstantValues.BaseUrl)
                    .ofTypePost(jsonObject)
                    .connect(new OnNetworkRequest() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            try {
                                Log.d("User response ",s);
                                JSONObject signupresponse = new JSONObject(s);
                                String fullname = "", Usershopname = "", otp = "",id="";
                                if (signupresponse.getString("msg").contains("OTP generated")) {
                                    JSONObject userJson=signupresponse.getJSONObject("User");
                                    if(userJson.has("id"))
                                        id=userJson.getString("id");
                                    if(userJson.has("otp"))
                                        otp=userJson.getString("otp");
                                    editor.putString("id",id);
                                    editor.putString("otp",otp);
                                    editor.commit();
                                    Toast toast = Toast.makeText(SignupActivity.this, "Thank you for filling out your information!", Toast.LENGTH_SHORT);//.show();
                                    baseData.setToast(toast);
                                    toast.show();
                                    Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }








//                                if (signupresponse.getString("msg").contains()) {
//                                    JSONObject userDetails = signupresponse.getJSONObject("User");
//                                    if (userDetails.has("fullname"))
//                                        fullname = userDetails.getString("fullname");
//                                    if (userDetails.has("shopname"))
//                                        Usershopname = userDetails.getString("shopname");
//                                    if (userDetails.has("contact"))
//                                        contact = userDetails.getString("contact");
//                                    if(userDetails.has("id"))
//                                        id=userDetails.getString("id");
//                                    editor.putString("userName", fullname);
//                                    editor.putString("email", Usershopname);
//                                    editor.putString("number", contact);
//                                    editor.putString("id",id);
//                                    editor.putBoolean("isLoggedIn", true);
//                                    editor.commit();
//                                    Toast toast = Toast.makeText(SignupActivity.this, "Thank you for filling out your information!", Toast.LENGTH_SHORT);//.show();
//                                    baseData.setToast(toast);
//                                    toast.show();
//                                    Intent intent = new Intent(SignupActivity.this, MainCategoryActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                } else if (signupresponse.getString("msg").contains("already existed")) {
//                                    Toast.makeText(SignupActivity.this, "Already Registered User", Toast.LENGTH_LONG).show();
//                                    msg.setVisibility(View.VISIBLE);
//                                    msg.setText("Already Registered User");
//                                }


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

}
