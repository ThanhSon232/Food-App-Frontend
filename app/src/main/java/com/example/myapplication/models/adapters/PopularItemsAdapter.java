package com.example.myapplication.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FoodDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Product;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularItemsAdapter extends RecyclerView.Adapter<PopularItemsAdapter.PopularItemsViewHolder> {
    List<Product> productList;
    Context mContext;

    public PopularItemsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public PopularItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items, null, false);
        return new PopularItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularItemsViewHolder holder, int position) {
        Product product = productList.get(position);
        System.out.println(product);
        if (product == null) return;
        if (product.getImage().size() > 0){
            Picasso.get().load("http://192.168.1.2:8080/image/" + product.getImage().get(0).getName()).into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FoodDetailActivity.class);
                intent.putExtra("productId", product.getId());
                mContext.startActivity(intent);
            }
        });
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.rateRatio.setText(String.valueOf(product.getRateRatio()));
        holder.rateNum.setText(String.valueOf("(" + product.getNumRate() + ")"));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class PopularItemsViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imageView;
        TextView name;
        TextView price;
        TextView subtitle;
        TextView rateRatio;
        TextView rateNum;


        public PopularItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.popular_image);
            name = itemView.findViewById(R.id.popular_name);
            price = itemView.findViewById(R.id.popular_price);
            subtitle = itemView.findViewById(R.id.popular_subtitle);
            rateRatio = itemView.findViewById(R.id.popular_rateRatio);
            rateNum = itemView.findViewById(R.id.popular_numRate);
        }
    }
}
