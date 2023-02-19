package com.example.myapplication;

import static com.example.myapplication.apis.ApiService.gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.apis.ApiService;
import com.example.myapplication.components.LoadingDialog;
import com.example.myapplication.models.AdditionFood;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Restaurant;
import com.example.myapplication.models.adapters.AdditionFoodAdapter;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDetailActivity extends AppCompatActivity {
    ImageButton backButton;
    LoadingDialog loadingDialog;
    ImageView image;
    TextView name;
    TextView rating;
    TextView numRate;
    TextView seeReview;
    TextView price;
    ImageButton remove;
    ImageButton add;
    TextView quantity;
    TextView description;
    RecyclerView recyclerView;
    AdditionFoodAdapter additionFoodAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        backButton = findViewById(R.id.back_button);
        loadingDialog = new LoadingDialog(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUp();
        getDetailFood();
    }

    private void getAdditionFood() {
        ApiService.apiService.getProductAdditionFood(getIntent().getStringExtra("productId")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                System.out.println(response.body());
                AdditionFood[] additionFoods = gson.fromJson(response.body().get("data"), AdditionFood[].class);
                additionFoodAdapter.setAdditionFoodList(List.of(additionFoods));
                recyclerView.setAdapter(additionFoodAdapter);
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    private void setUp() {
        image = findViewById(R.id.detail_image);
        name = findViewById(R.id.product_detail_name);
        rating = findViewById(R.id.detail_rate_ratio);
        numRate = findViewById(R.id.detail_num_rate);
        seeReview = findViewById(R.id.detail_review);
        price = findViewById(R.id.detail_price);
        remove = findViewById(R.id.detail_remove);
        add = findViewById(R.id.detail_add);
        quantity = findViewById(R.id.detail_quantity);
        description = findViewById(R.id.detail_description);
        recyclerView = findViewById(R.id.detail_recycler);
        additionFoodAdapter = new AdditionFoodAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getDetailFood(){
        loadingDialog.startLoading();
        ApiService.apiService.getProductDetail(getIntent().getStringExtra("productId")).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println(response.body());
                Product product = gson.fromJson(response.body().get("data"), Product.class);
                if(product.getImage().size() != 0){
                    Picasso.get().load("http://192.168.1.2:8080/image/" + product.getImage().get(0).getName()).into(image);
                }
                name.setText(product.getName());
                rating.setText(String.valueOf(product.getRateRatio()));
                numRate.setText("("+product.getNumRate()+")");
                price.setText(String.valueOf(product.getPrice())+"vnd");
                quantity.setText("1");
                description.setText(product.getDescription());
                getAdditionFood();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

}