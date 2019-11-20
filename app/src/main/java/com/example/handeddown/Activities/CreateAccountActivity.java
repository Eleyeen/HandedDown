package com.example.handeddown.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.handeddown.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    ImageView btn_pass_Create;
    TextView btnsignin;
  private   TextInputLayout   name_layout,email_lay,password_lay,mobile_lay;
    EditText name1 ,email,password,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnsignin=findViewById(R.id.sign_In_create);
        btn_pass_Create=findViewById(R.id.password_btn_create);
        name_layout=findViewById(R.id.name_login);
        name1=findViewById(R.id.text_create_name);
        email=findViewById(R.id.email_text_create);
        password=findViewById(R.id.password_text_create);
        mobile=findViewById(R.id.mobile_num_create);




        btn_pass_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog  = new ProgressDialog(CreateAccountActivity.this);

                progressDialog.setTitle("Loading...");
                progressDialog.setMessage("Wait");
                progressDialog.show();

                final String url = "http://www.dahawwalur.org/staging/recipes/public/api/register";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url
                        , new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if(message.contains("success")) {

                                Toast.makeText(CreateAccountActivity.this, "success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateAccountActivity.this, SignUpActivity
                                        .class);
                                startActivity(intent);
                                finishAffinity();

                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(CreateAccountActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateAccountActivity.this,"error"+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();

                        params.put("name",name1.getText().toString().trim());
                        params.put("email",email.getText().toString().trim());
                        params.put("mobile",mobile.getText().toString().trim());
                        params.put("password",password.getText().toString().trim());

                        return params;
                    }

                };


                RequestQueue mRequestQueue = Volley.newRequestQueue(CreateAccountActivity.this);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue.add(stringRequest);


            }
        });
                btnsignin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CreateAccountActivity.this, SignUpActivity
                                .class);
                        startActivity(intent);
                    }
                });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
