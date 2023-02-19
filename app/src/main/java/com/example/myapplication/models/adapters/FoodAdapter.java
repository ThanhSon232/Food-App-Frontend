package com.example.myapplication.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodAdapterViewHolder>{
    List<Product> productList;
    Context mContext;

    public FoodAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent, false);
        return new FoodAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapterViewHolder holder, int position) {
        Product product = productList.get(position);
        if(product == null) return;
        if(product.getImage().size() != 0) {
            Picasso.get()
                    .load("http://192.168.100.3:8080/image/" + product.getImage().get(0).getName()).error(R.drawable.ic_baseline_error_outline_24)
                    .fit()
                    .into(holder.imageView);
        }
        holder.name.setText(product.getName());
        holder.rateRatio.setText(String.valueOf(product.getRateRatio()));
        holder.numRate.setText(String.valueOf("("+product.getNumRate()+")"));

    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    class FoodAdapterViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView imageView;
        TextView name;
        TextView price;
        TextView rateRatio;
        TextView numRate;
        TextView freeShipping;
        public FoodAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            name = itemView.findViewById(R.id.restaurant_name);
            price = itemView.findViewById(R.id.restaurant_price);
            rateRatio = itemView.findViewById(R.id.restaurant_rate_ratio);
            numRate = itemView.findViewById(R.id.restaurant_num_rate);
        }
    }
}
