package com.example.handeddown.Adapters;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handeddown.Activities.RecipeNameActivity;
import com.example.handeddown.Models.Recycle_View_Model;
import com.example.handeddown.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.media.CamcorderProfile.get;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {
        private Context context ;
        private List<Recycle_View_Model>  recipeModelList ;
        private List<Recycle_View_Model>  listItems;
      private Button btn_arrow;
        public RecyclerView_Adapter(Context context, List<Recycle_View_Model> recipeModelList) {
            this.context = context;
            this.listItems = recipeModelList;
            this.recipeModelList = recipeModelList;
        }



    @NonNull
        @Override
        public RecyclerView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int posit) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipieitem , parent , false) ;
            return new RecyclerView_Adapter.MyViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Recycle_View_Model item = recipeModelList.get(i);

        myViewHolder.name.setText(item.getRecipename());
        myViewHolder.description.setText(item.getRecipedescprition());
        myViewHolder.uploadedbY.setText(item.getUploadedby());
        Glide.with(context).load(item.getImage()).into(myViewHolder.imageView);
        myViewHolder.startIds.setText(item.getStartId());

        SharedPreferences sharedPreferences = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", item.getStartId());
        editor.apply();


        myViewHolder.btn_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RecipeNameActivity.class);
                context.startActivity(intent);

            }
        });

    }
    @Override
        public int getItemCount() {
            return recipeModelList.size();

        }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    recipeModelList = listItems;
                } else {
                    List<Recycle_View_Model> filteredList = new ArrayList<>();
                    for (Recycle_View_Model row : listItems) {

//                        Toast.makeText(context, "charString else", Toast.LENGTH_SHORT).show();

                        if (row.getRecipename().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    recipeModelList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = recipeModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                recipeModelList = (ArrayList<Recycle_View_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView name ;
            private TextView description ;
            private TextView uploadedbY ;
            public CardView btn_arrow;
            public  TextView startIds;
            private ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                startIds = (TextView) itemView.findViewById(R.id.startId);
                name = (TextView) itemView.findViewById(R.id.text_card_1);
                description =(TextView) itemView.findViewById(R.id.text_View_card);
                uploadedbY = (TextView) itemView.findViewById(R.id.text_card_jhon);
                btn_arrow = itemView.findViewById(R.id.btn_arrow) ;
                imageView = itemView.findViewById(R.id.image_View2);
            }
            @Override
            public void onClick(View v) {


            }
        }

    }


