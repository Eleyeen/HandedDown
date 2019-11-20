package com.example.handeddown.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handeddown.Activities.RecipeNameActivity;
import com.example.handeddown.Models.IngredientModel;
import com.example.handeddown.Models.RecipeNameModel;
import com.example.handeddown.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private List<String> ingredientResult;
    private Context context ;
    private List<IngredientModel> ingredientModels ;


    public IngredientAdapter(Context context, List<IngredientModel> ingredientModels) {
        this.context = context;
        this.ingredientModels = ingredientModels;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posit) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_xml , parent , false) ;
        return new IngredientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.MyViewHolder myViewHolder, int i) {
       IngredientModel item = ingredientModels.get(i);
        myViewHolder.title_ingendient.setText(item.getIngredient());






    }

    @Override
    public int getItemCount() {

        return ingredientModels.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title_ingendient ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_ingendient = itemView.findViewById(R.id.ingredienXmlText);

        }

        @Override
        public void onClick(View v) {


        }
    }
}
