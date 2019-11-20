package com.example.handeddown.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.handeddown.Models.IngredientModel;
import com.example.handeddown.Models.RecipeNameModel;
import com.example.handeddown.R;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FullscreenActivity extends AppCompatActivity {
    View mBottomLayoutq;
    View mVideoLayoutq;
    UniversalVideoView mVideoViewq;
    UniversalMediaController mMediaControllerq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        SharedPreferences sharedPreferences = getSharedPreferences("abc", Context.MODE_PRIVATE);
        String ids = sharedPreferences.getString("id" ,"");


        final String url = "http://www.dahawwalur.org/staging/recipes/public/api/post/"+ids;

        mVideoViewq = (UniversalVideoView) findViewById(R.id.videoViewq);
        mMediaControllerq = (UniversalMediaController) findViewById(R.id.media_controllerq);
        mVideoViewq.setMediaController(mMediaControllerq);

        mVideoViewq.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            private int cachedHeight;
            private boolean isFullscreen;
            String TAG = "RecipeNameActivity";
            @Override
            public void onScaleChange(boolean isFullscreen) {
                this.isFullscreen=isFullscreen;
                if (isFullscreen) {


//                    Toast.makeText(RecipeNameActivity.this, "fullscreen", Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(FullscreenActivity.this,RecipeNameActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
                Log.d(TAG, "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
                Log.d(TAG, "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
                Log.d(TAG, "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading
                Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
            }

        });


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("All Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);

                        String url_vedio_text = json_data.getString("Url");



                        mVideoViewq.setVideoPath(url_vedio_text);
                        mVideoViewq.start();



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
