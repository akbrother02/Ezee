package com.ezee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezee.R;
import com.ezee.Utils.BaseData;
import com.ezee.Utils.ConstantValues;

import org.json.JSONObject;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ContactNumber, Password;
    TextView ForgetPassword,createPassword;
    SharedPreferences loginSharedPreferences;
    SharedPreferences.Editor editor;
    BaseData basedata;
    boolean isUsernameCorrect = false, isPasswordCorrect = false;
    Button loginbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        WidgetInitialization();
        loginbutton.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);
        createPassword.setOnClickListener(this);

    }

    private void WidgetInitialization() {
        ContactNumber=(EditText)findViewById(R.id.input_contact);
        Password=(EditText)findViewById(R.id.input_password);
        loginbutton=(Button)findViewById(R.id.loginbutton);
        ForgetPassword=(TextView)findViewById(R.id.forgetpassword);
        createPassword=(TextView)findViewById(R.id.createpassword);
        loginSharedPreferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);
        editor = loginSharedPreferences.edit();
        basedata = (BaseData) getApplicationContext();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.loginbutton:
                if (Patterns.PHONE.matcher(ContactNumber.getText().toString()).matches() && ContactNumber.getText().toString().length() == 10) {
                    isUsernameCorrect = true;
                } else {
                    ContactNumber.requestFocus();
                    ContactNumber.setError("Enter a valid number");
                }

                if (!Password.getText().toString().equals("")) {
                    isPasswordCorrect = true;
                } else {
                    Password.requestFocus();
                    Password.setError("Wrong Password");
                }
                if (isPasswordCorrect == true && isUsernameCorrect == true) {
                    LoginAction();
                }
                break;
            case R.id.forgetpassword:
                break;
            case R.id.createpassword:
                Intent signupintent=new Intent(LoginActivity.this,SignupActivity.class);
                signupintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signupintent);
                break;
        }
    }

    private void LoginAction() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag","login");
            jsonObject.put("contact",ContactNumber.getText().toString());
            jsonObject.put("password",Password.getText().toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Perfecto.with(LoginActivity.this)
                .fromUrl(ConstantValues.BaseUrl)
                .ofTypePost(jsonObject)
                .connect(new OnNetworkRequest() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject loginresponse = new JSONObject(s);
                            JSONObject UsrResponse = null;
                            if (loginresponse.getString("msg").contains("successfully")) {
                                UsrResponse = loginresponse.getJSONObject("User");
                                editor.putString("number", ContactNumber.getText().toString());
                                editor.putBoolean("isLoggedIn", true);
                                if (UsrResponse.has("name")) {
                                    editor.putString("userName", UsrResponse.getString("name"));
                                    basedata.setUsername(UsrResponse.getString("name"));
                                }
                                if(UsrResponse.has("id"))
                                    editor.putString("id",UsrResponse.getString("id"));
                                if(UsrResponse.has("shopname")){
                                    editor.putString("shopname",UsrResponse.getString("shopname"));
                                }
                                if(UsrResponse.has("shopnumber"))
                                {
                                    editor.putString("shopnumber",UsrResponse.getString("shopnumber"));
                                }
                                if(UsrResponse.has("contact"))
                                {
                                    editor.putString("contact",UsrResponse.getString("contact"));
                                }
                                if(UsrResponse.has("district"))
                                {
                                    editor.putString("district",UsrResponse.getString("district"));
                                }
                                if(UsrResponse.has("state"))
                                {
                                    editor.putString("state",UsrResponse.getString("state"));
                                }
                                editor.apply();
                                Intent Productintent=new Intent(LoginActivity.this,MainCategoryActivity.class);
                                Productintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(Productintent);

                            }else
                            {
                                Toast.makeText(LoginActivity.this,"User Not Exist",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int i, String s, String s1) {

                    }
                });
    }
}
