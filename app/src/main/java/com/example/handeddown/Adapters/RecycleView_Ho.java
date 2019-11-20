package com.example.handeddown.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handeddown.Models.FruitModel;
import com.example.handeddown.Models.Recycle_View_Model;
import com.example.handeddown.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecycleView_Ho extends RecyclerView.Adapter<RecycleView_Ho.MyViewHolder> {

    private LayoutInflater inflater;
    private List<FruitModel> imageModelArrayList;
    private ArrayList<FruitModel> listFruit;

    public RecycleView_Ho(Context ctx, ArrayList<FruitModel> imageModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.listFruit = imageModelArrayList;
    }

    @Override
    public RecycleView_Ho.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleView_Ho.MyViewHolder holder, int position) {

        holder.time.setText(imageModelArrayList.get(position).getName());
        holder.iv.setImageResource(imageModelArrayList.get(position).getImageView322());
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    imageModelArrayList = listFruit;
//                } else {
//                    List<FruitModel> filteredList = new ArrayList<>();
//                    for (FruitModel row : listFruit) {
//
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//                    imageModelArrayList=  filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = imageModelArrayList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                imageModelArrayList = (ArrayList<FruitModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//
//    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.tv);
             iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}
