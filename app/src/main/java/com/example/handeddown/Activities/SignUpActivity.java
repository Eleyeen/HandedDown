package com.example.handeddown.Activities;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.bumptech.glide.Glide;
import com.example.handeddown.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.SmartLoginOption;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignUpActivity extends AppCompatActivity {
    ImageView btn_password;
    TextView btn_facebook;
    CallbackManager callbackManager;
    private LoginButton facebooklogin;
    TextInputEditText text_Email,text_password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    btn_facebook=findViewById(R.id.sign_In_loginScreen);
    btn_password=findViewById(R.id.password_btn_sign);
    text_Email =findViewById(R.id.email_loginEdit);
    text_password=findViewById(R.id.password_loginEdit);
    printHashKey();
    facebooklogin=findViewById(R.id.button_lgin_facebook);

    facebooklogin.setReadPermissions();
    facebooklogin.setReadPermissions();
    callbackManager = CallbackManager.Factory.create();


    facebooklogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>()  {
        @Override
        public void onSuccess(LoginResult loginResult) {
            SharedPreferences prefsd = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
            Boolean statusLockedP= prefsd.edit().putBoolean("lockedP", true).commit();
            prefsd.edit().putBoolean("lockedP", true).apply();

            Intent intent = new Intent(SignUpActivity.this, MyDiscoverRecipesActivity
                    .class);
            startActivity(intent);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {


        }
    });

        AccessTokenTracker  accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                if(currentAccessToken == null){

                    Toast.makeText(SignUpActivity.this, "logout", Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        };

        btn_facebook.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SignUpActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
    });

        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final ProgressDialog progressDialog  = new ProgressDialog(SignUpActivity.this);
//
//                progressDialog.setTitle("Loading...");
//                progressDialog.setMessage("Wait");
//                progressDialog.show();
//
//                final String url = "http://www.dahawwalur.org/staging/recipes/public/api/login";
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url
//                        , new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onResponse(String response) {
//                        try {
////                            JSONArray jsonArray= new JSONArray(response);
////                            JSONObject jsonObject = jsonArray.getJSONObject(0);
////                            String id = jsonObject.getString("id");
////                            Bundle bundle = new Bundle();
////                            bundle.putString("id",id);
//                            JSONObject jsonObject = new JSONObject(response);
//                            String message = jsonObject.getString("message");
//
//                            if(message.contains("Success")) {
//                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
//                                Boolean statusLocked = prefs.edit().putBoolean("locked", true).commit();
//                                prefs.edit().putBoolean("locked", true).apply();
//
//                                finishAffinity();
//                                Intent intent = new Intent(SignUpActivity.this, MyDiscoverRecipesActivity
//                                        .class);
//                                startActivity(intent);
//
//
//
//
//                            }else {
//                                progressDialog.dismiss();
//                                Toast.makeText(SignUpActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(SignUpActivity.this,"error"+ error.toString(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                }
//                ){
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String,String> params = new HashMap<>();
//
//                        params.put("email",text_Email.getText().toString().trim());
//                        params.put("password",text_password.getText().toString().trim());
//
//                        return params;
//                    }
//
//                };
//
//
//                RequestQueue mRequestQueue = Volley.newRequestQueue(SignUpActivity.this);
//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                mRequestQueue.add(stringRequest);

                Intent intent = new Intent(SignUpActivity.this,MyDiscoverRecipesActivity.class);
                startActivity(intent);

            }
        });

    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Key_Hashes", "your Hash Key =  " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Key_Hashes", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Key_Hashes", "printHashKey()", e);
        }
    }

}

