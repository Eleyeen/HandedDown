package com.example.handeddown.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handeddown.Models.IngredientModel;
import com.example.handeddown.Models.RecipeNameModel;
import com.example.handeddown.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class Recipes_Name_Adepter extends RecyclerView.Adapter<Recipes_Name_Adepter.MyViewHolder> {
    View view;
    private Context context ;
    private List<RecipeNameModel> recipeModelList_name ;
    private  List<String> result;

    public Recipes_Name_Adepter(Context context, List<RecipeNameModel> recipeModelList_name) {
        this.context = context;
        this.recipeModelList_name = recipeModelList_name;
        }

        @NonNull
        @Override
        public Recipes_Name_Adepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posit) {
        view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.instruction_xml , null , false) ;
        return new Recipes_Name_Adepter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            RecipeNameModel item = recipeModelList_name.get(i);
            myViewHolder.title_Instruction.setText(item.getInstructions());

        }

        @Override
        public int getItemCount() {

        return recipeModelList_name.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView title_Instruction ;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title_Instruction = itemView.findViewById(R.id.title_Ints);




            }

            @Override
           public void onClick(View v) {


           }
       }
}
