package com.example.handeddown.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.handeddown.Activities.AddFriendActivity;
import com.example.handeddown.Activities.RecipeNameActivity;
import com.example.handeddown.Adapters.RecycleView_Ho;
import com.example.handeddown.Adapters.RecyclerView_Adapter;
import com.example.handeddown.Models.FruitModel;
import com.example.handeddown.Models.Recycle_View_Model;
import com.example.handeddown.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrivateFragment extends Fragment {
    private List<Recycle_View_Model> pdfDocs;
    private android.widget.SearchView searchView;
    ImageView btn_Plus;
    private Context context;
    private RecyclerView_Adapter adapter_p;
    private ArrayList<FruitModel> imageModelArrayList;
    private RecycleView_Ho nameAdapter;

    private RecyclerView recyclerView_p,recyclerView_Hor;
    private int[] myImageList ={R.drawable.down1, R.drawable.foodpic2,R.drawable.down2, R.drawable.down3,R.drawable.down4,R.drawable.down5};
    private String[] myImageNameList = new String[]{"Apple","Mango" ,"Strawberry","Pineapple","Orange","Blueberry"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_private, container, false);
        recyclerView_p = (RecyclerView)view.findViewById(R.id.recycle_View1);

        recyclerView_Hor = (RecyclerView) view.findViewById(R.id.recycle_View);
        btn_Plus=view.findViewById(R.id.plus_buton);

        imageModelArrayList=eatFruits();
        btn_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddFriendActivity.class);
                startActivity(intent);
            }
        });


            Recycle();
            setRecyclerView_How();
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
        mActionBar.setTitle("My Recipes");
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
            public boolean onQueryTextSubmit(String query)
            {
                 adapter_p.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                textView.setVisibility(View.GONE);
                adapter_p.getFilter().filter(newText);

                return false;
            }
        });

        mActionBar.show();

    }

    private void setRecyclerView_How(){

        RecycleView_Ho adapter_hor = new RecycleView_Ho(getActivity(), (ArrayList<FruitModel>) imageModelArrayList);
        recyclerView_Hor.setAdapter(adapter_hor);
        recyclerView_Hor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView_Hor.setHasFixedSize(true);
        recyclerView_Hor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        imageModelArrayList = new ArrayList<>() ;



    }

    private ArrayList<FruitModel> eatFruits(){

        ArrayList<FruitModel> list = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            FruitModel fruitModel = new FruitModel();
            fruitModel.setName(myImageNameList[i]);
            fruitModel.setImageView322(myImageList[i]);
            list.add(fruitModel);
        }

        return list;
    }

    private void Recycle(){

        recyclerView_p.setHasFixedSize(true);
        recyclerView_p.setLayoutManager(new LinearLayoutManager(getActivity()));

        pdfDocs = new ArrayList<>() ;

//
//        for(int i=0 ; i < 10 ; i++){
//            Recycle_View_Model item = new Recycle_View_Model("", "Recipe Name Here"+(i+1) , "lorem ipusm dolor sit amet, consectetur"+(i+1) , "John Smith"+(i+1)) ;
//            pdfDocs.add(item) ;
//        }

        adapter_p = new RecyclerView_Adapter(getActivity(),pdfDocs) ;
        recyclerView_p.setAdapter(adapter_p);


        final String url = "http://www.dahawwalur.org/staging/recipes/public/api/recipes";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                            JSONArray jsonArray= new JSONArray(response);
//                            JSONObject jsonObject = jsonArray.getJSONObject(0);
//                            String id = jsonObject.getString("id");
//                            Bundle bundle = new Bundle();
//                            bundle.putString("id",id);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("All Data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        String startid = json_data.getString("id");
                        String startTitle= json_data.getString("title");
//                        String startdescription= json_data.getString("instruction");
                        String startauthor= json_data.getString("username");
                        String startfile= json_data.getString("file");

                        Recycle_View_Model model = new Recycle_View_Model();
                        model.setRecipename(startTitle);
//                        model.setRecipedescprition(startdescription);
                        model.setUploadedby(startauthor);
                        model.setStartId(startid);
                        model.setImage(startfile);

                        pdfDocs.add(model);
                        adapter_p.notifyDataSetChanged();


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




    }

}