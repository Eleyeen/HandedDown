package com.example.handeddown.Fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.handeddown.Activities.MyDiscoverRecipesActivity;
import com.example.handeddown.Activities.RecipeNameActivity;
import com.example.handeddown.Activities.SignUpActivity;
import com.example.handeddown.Adapters.RecyclerView_Adapter;
import com.example.handeddown.Models.Recycle_View_Model;
import com.example.handeddown.R;
import com.github.leonardoxh.fakesearchview.FakeSearchView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicFragment extends Fragment {
    private android.widget.SearchView searchView;
    private List<Recycle_View_Model> itemLists;
    RecyclerView_Adapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_public, container, false);
        recyclerView = view.findViewById(R.id.recycle_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemLists = new ArrayList<>() ;
        adapter = new RecyclerView_Adapter(getActivity(), itemLists) ;
        recyclerView.setAdapter(adapter);
        final String url = "http://www.dahawwalur.org/staging/recipes/public/api/recipes";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("All Data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        String startid = json_data.getString("id");
                        String startTitle= json_data.getString("title");
//                        String startdescription= json_data.getString("description");
                        String startauthor= json_data.getString("username");
                        String startfile= json_data.getString("file");

                        Recycle_View_Model model = new Recycle_View_Model();
                        model.setRecipename(startTitle);
//                        model.setRecipedescprition(startdescription);
                        model.setUploadedby(startauthor);
                        model.setImage(startfile);
                        model.setStartId(startid);

                        itemLists.add(model);
                        adapter.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"error"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );


        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);

        customActionBar();
        return view;
    }

    public void customActionBar() {

        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.setElevation(0);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.customappbar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setTitle("Discover Recipes");
        final TextView textView = mCustomView.findViewById(R.id.name);

        searchView = mCustomView.findViewById(R.id.searchView);


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);

            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                textView.setVisibility(View.VISIBLE);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textView.setVisibility(View.GONE);


                adapter.getFilter().filter(newText);

                return false;
            }
        });

        mActionBar.show();

    }

    private void moveToNewActivity () {

        Intent i = new Intent(getActivity(), RecipeNameActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
}
