package com.example.handeddown.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.handeddown.Adapters.IngredientAdapter;
import com.example.handeddown.Adapters.Recipes_Name_Adepter;
import com.example.handeddown.Models.IngredientModel;
import com.example.handeddown.Models.RecipeNameModel;
import com.example.handeddown.R;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.share.internal.ShareConstants.VIDEO_URL;

public class RecipeNameActivity extends AppCompatActivity {
    ImageView imageView , imageViewRecipe;
    TextView title_Instruction;
    private List<RecipeNameModel> recipeModelList_name;
    Recipes_Name_Adepter adapter_name;
    RecyclerView recyclerView_name, ingredientRecycle;
    List<String> result;

    IngredientAdapter ingredientAdapter;
    private List<IngredientModel> ingredientModels;
    List<String> ingredientResult;


    TextView keyOne,valueOne,keyTwo,valuetwo,keythree,valueThree,keyFour,valueFour;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_name);

        SharedPreferences sharedPreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        String ids = sharedPreferences.getString("id" ,"");
        Toast.makeText(RecipeNameActivity.this, ids, Toast.LENGTH_SHORT).show();


        final String url = "http://www.dahawwalur.org/staging/recipes/public/api/privaterecipes/"+ids;


        imageView = findViewById(R.id.back_iconImage);
        imageViewRecipe = findViewById(R.id.frame_image_recipe);
        title_Instruction = findViewById(R.id.title_Ints);
        recyclerView_name = findViewById(R.id.Recycle_View_instruction);
        ingredientRecycle = findViewById(R.id.recycle_ingredient);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeNameActivity.this, MyDiscoverRecipesActivity.class);
                startActivity(intent);
            }
        });


        recyclerView_name.setHasFixedSize(true);
        recyclerView_name.setLayoutManager(new LinearLayoutManager(this));
        recipeModelList_name = new ArrayList<RecipeNameModel>();
        adapter_name = new Recipes_Name_Adepter(RecipeNameActivity.this, recipeModelList_name);
        recyclerView_name.setAdapter(adapter_name);


        ingredientRecycle.setHasFixedSize(true);
        ingredientRecycle.setLayoutManager(new GridLayoutManager(this, 2));
        ingredientModels = new ArrayList<IngredientModel>();
        ingredientAdapter = new IngredientAdapter(RecipeNameActivity.this, ingredientModels);
        ingredientRecycle.setAdapter(ingredientAdapter);






        StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);

                        String instruction_text = json_data.getString("instruction");
                        String ingredient_text = json_data.getString("ingredients");
                        String url_vedio_text = json_data.getString("file");
                        RecipeNameModel modesl = new RecipeNameModel();
                        modesl.setUrl(url_vedio_text);
                        imageViewRecipe.setImageURI(Uri.parse(url_vedio_text));
                         final String REGEX = "\\s*,\\s*";



                        String commaSeparatedingredient = ingredient_text;

                        String[] separated = commaSeparatedingredient.trim().split(REGEX);

                        ingredientResult = new ArrayList<>(Arrays.asList(separated));

                        for (int ii = 0; ii < ingredientResult.size(); ii++) {
                            IngredientModel modelIngredient = new IngredientModel();

                            modelIngredient.setIngredient(ingredientResult.get(ii));

                                ingredientModels.add(modelIngredient);

                        }

                        String commaSeparatedArr = instruction_text;
                        String[] separatedResult = commaSeparatedArr.trim().split("\\s*,");
                        result = new ArrayList<>(Arrays.asList(separatedResult));

                        for (int m = 0; m < result.size(); m++) {
                            RecipeNameModel model = new RecipeNameModel();

                            model.setInstructions(result.get(m));

                            recipeModelList_name.add(model);


                        }

                        ingredientAdapter.notifyDataSetChanged();
                        adapter_name.notifyDataSetChanged();

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                                 }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecipeNameActivity.this, "error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
        fourValue();

    }
    public void fourValue(){
        SharedPreferences sharedPreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        String ids = sharedPreferences.getString("id" ,"");
        Toast.makeText(this, ids, Toast.LENGTH_SHORT).show();
        final String urls = "http://www.dahawwalur.org/staging/recipes/public/api/privaterecipes/"+ ids;


        keyOne = findViewById(R.id.cardView_20Min);
        keyTwo = findViewById(R.id.cardView_medium);
        keythree = findViewById(R.id.cardView_12);
        keyFour = findViewById(R.id.cardView_200g);
        valueOne =findViewById(R.id.valueone);
        valuetwo =findViewById(R.id.valueTwo);
        valueThree =findViewById(R.id.tyur);
        valueFour =findViewById(R.id.rtyui);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(0);
                        JSONObject json_data1 = jsonArray.getJSONObject(1);
                        JSONObject json_data2 = jsonArray.getJSONObject(2);
                        JSONObject json_data3 = jsonArray.getJSONObject(3);


                        String key_text = json_data.getString("time");
                        String value_text = json_data.getString("level");

                        String key_text1 = json_data1.getString("time");
                        String value_text1 = json_data1.getString("level");

                        String key_text2 = json_data2.getString("time");
                        String value_text2 = json_data2.getString("level");

                        String key_text3 = json_data3.getString("time");
                        String value_text3 = json_data3.getString("level");

                        keyOne.setText(key_text);
                        valueOne.setText(value_text);

                        keyTwo.setText(key_text1);
                        valuetwo.setText(value_text1);

                        keythree.setText(key_text2);
                        valueThree.setText(value_text2);

                        keyFour.setText(key_text3);
                        valueFour.setText(value_text3);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RecipeNameActivity.this, "error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

    }

  }

