package com.example.myapplication.models.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    private Context context;
    private List<Category> categoryList;
    private int index;

    public int getIndex() {
        return index;
    }

    public Category getCurrentItem() {
        return categoryList.get(index);
    }

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> categoryList, int index) {
        this.categoryList = categoryList;
        this.index = index;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_icon, parent, false);

        return new CategoryViewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        Category category = categoryList.get(position);
        if (category == null) return;
        if (category.isSelected()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.outrageous_orange));
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.black));


        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category temp = categoryList.get(index);
                temp.setSelected(false);
                categoryList.set(index, temp);
                category.setSelected(true);
                categoryList.set(position, category);
                index = position;
                notifyDataSetChanged();
            }
        });
        holder.textView.setText(category.getTitle());
        holder.imageView.setImageResource(category.getResource());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CardView cardView;


        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon);
            textView = itemView.findViewById(R.id.title);
            cardView = itemView.findViewById(R.id.icon_card);
        }
    }
}
