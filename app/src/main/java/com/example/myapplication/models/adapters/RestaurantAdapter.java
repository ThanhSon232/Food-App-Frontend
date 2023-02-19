package com.example.myapplication.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    Context mContext;
    List<Restaurant> restaurantList;
    int index = 0;

    public RestaurantAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        if (restaurant == null) return;

        holder.name.setText(restaurant.getName());
        holder.rateRatio.setText(String.valueOf(restaurant.getRateRatio()));
        holder.numRate.setText(String.valueOf("("+restaurant.getNumRate()+")"));
        if(restaurant.getImageURL().size() != 0) {
            Picasso.get()
                    .load("http://192.168.100.3:8080/image/" + restaurant.getImageURL().get(0).getName()).error(R.drawable.ic_baseline_error_outline_24)
                    .fit()
                    .into(holder.image);
        }
        holder.shipping.setText(restaurant.isFreeShipping() ? "Free delivery" : "No free delivery");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        TypeAdapter typeAdapter = new TypeAdapter();
        typeAdapter.setData(restaurant.getTypes());
        typeAdapter.notifyDataSetChanged();
        holder.recyclerView.setAdapter(typeAdapter);

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        ImageView favorite;
        TextView rateRatio;
        TextView numRate;
        TextView shipping;
        RecyclerView recyclerView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_name);
            favorite = itemView.findViewById(R.id.card_favorite);
            rateRatio = itemView.findViewById(R.id.card_rate_ratio);
            numRate = itemView.findViewById(R.id.card_num_rate);
            shipping = itemView.findViewById(R.id.card_shipping);
            recyclerView = itemView.findViewById(R.id.categories);
        }
    }
}
