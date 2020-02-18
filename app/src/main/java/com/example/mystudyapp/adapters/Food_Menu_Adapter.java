package com.example.mystudyapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mystudyapp.R;
import com.example.mystudyapp.Retrofit2.ResultFood;

import java.util.List;

public class Food_Menu_Adapter extends RecyclerView.Adapter<Food_Menu_Adapter.ViewHolder> {
    private List<ResultFood> mData;
    private Context context;

    public Food_Menu_Adapter(Context context, List<ResultFood> listitem) {
        this.context = context;
        this.mData = listitem;
    }


    @NonNull
    @Override
    public Food_Menu_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_div, parent, false);

        ViewHolder viewHolder = new ViewHolder(convertView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.food_div.setText(mData.get(i).getFood_div());
        viewHolder.food_name.setText(mData.get(i).getFood_name());
        viewHolder.food_price.setText(mData.get(i).getFood_price());



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView food_div;
        TextView food_name;
        TextView food_price;

        public ViewHolder(View itemView) {
            super(itemView);

            // 레이아웃 들고 오기
            TextView div_txt = (TextView) itemView.findViewById(R.id.div_txt);
            TextView name_txt = (TextView) itemView.findViewById(R.id.name_txt);
            TextView price_txt = (TextView) itemView.findViewById(R.id.price_txt);

            this.food_div = div_txt;
            this.food_name = name_txt;
            this.food_price = price_txt;
        }
    }


}