package com.example.myapplication;

import static com.example.myapplication.apis.ApiService.gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.apis.ApiService;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Restaurant;
import com.example.myapplication.models.adapters.FoodAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
    AppBarLayout appBarLayout;
    ImageView imageView;
    Spinner spinner;
    Bundle bundle;
    TextView firstName;
    TextView secondName;
    ImageButton backButton;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        appBarLayout = findViewById(R.id.food_appbar);
        imageView = findViewById(R.id.food_background);
        spinner = findViewById(R.id.food_spinner);
        bundle = getIntent().getExtras();
        firstName = findViewById(R.id.food_firstname);
        secondName = findViewById(R.id.food_secondname);
        backButton = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.food_recycler);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shimmerFrameLayout = findViewById(R.id.food_shimmer);
        setUp();
        setUpRecycler();
    }

    private void setUpRecycler() {
        foodAdapter = new FoodAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodAdapter);
        getFood(bundle.getString("type"));
    }

    private void getFood(String type) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        ApiService.apiService.getProductWithFilter(type, spinner.getSelectedItem().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Product[] mcArray = gson.fromJson(response.body().get("data"), Product[].class);
                productList = Arrays.asList(mcArray);
                foodAdapter.setProductList(productList);
                recyclerView.setAdapter(foodAdapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private Drawable getDrawable(){
        String type = bundle.getString("type");
        switch (type){
            case "Burger":
                return getResources().getDrawable(R.drawable.burger_background);
            case "Meat":
                return getResources().getDrawable(R.drawable.meat_background);
        }
        return getResources().getDrawable(R.drawable.meat_background);
    }

    private void setName(){
        String type = bundle.getString("type");
        switch (type) {
            case "Burger":
                firstName.setText("Ham");
                secondName.setText("burger");
                break;
            case "Meat":
                firstName.setText("Mee");
                secondName.setText("eeeat");
                break;
        }
    }

    private void setUp() {
        imageView.setImageDrawable(getDrawable());
        setName();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                FoodActivity.this, R.layout.custom_spinner_layout, List.of("Popular","A-Z", "Z-A","High to low", "Low to high"));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                imageView.setImageAlpha((int) (Integer.valueOf(appBarLayout.getTotalScrollRange()+verticalOffset)*(225.0/appBarLayout.getTotalScrollRange())));
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getFood(bundle.getString("type"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}