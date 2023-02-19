package com.example.myapplication;

import static com.example.myapplication.apis.ApiService.gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.apis.ApiService;
import com.example.myapplication.models.Restaurant;
import com.example.myapplication.models.adapters.RestaurantSecondAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    AppBarLayout appBarLayout;
    ImageView imageView;
    Spinner spinner;
    RecyclerView recyclerView;
    RestaurantSecondAdapter restaurantSecondAdapter;
    List<Restaurant> restaurantList;
    ImageButton backButton;
    Bundle bundle;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.restaurant) ;
        setContentView(R.layout.activity_restaurant);
        coordinatorLayout = findViewById(R.id.restaurant_layout);
        appBarLayout = findViewById(R.id.restaurant_appbar);
        imageView = findViewById(R.id.restaurant_background);
        spinner = findViewById(R.id.restaurant_spinner);
        recyclerView = findViewById(R.id.restaurant_recycler);
        backButton = findViewById(R.id.back_button);
        bundle = getIntent().getExtras();
        shimmerFrameLayout = findViewById(R.id.restaurant_card_shimmer);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUp();
        setUpRecycler();

    }

    private void setUpRecycler() {
        restaurantSecondAdapter = new RestaurantSecondAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(restaurantSecondAdapter);

        getRestaurant(bundle.getString("type"));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getRestaurant(bundle.getString("type"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getRestaurant(String type) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        ApiService.apiService.getRestaurantWithFilter(type, spinner.getSelectedItem().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Restaurant[] mcArray = gson.fromJson(response.body().get("data"), Restaurant[].class);
                restaurantList = Arrays.asList(mcArray);
                restaurantSecondAdapter.setRestaurantList(restaurantList);
                recyclerView.setAdapter(restaurantSecondAdapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void setUp() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                RestaurantActivity.this, R.layout.custom_spinner_layout, List.of("Popular","A-Z", "Z-A"));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                System.out.println((225.0/appBarLayout.getTotalScrollRange()));
                imageView.setImageAlpha((int) (Integer.valueOf(appBarLayout.getTotalScrollRange()+verticalOffset)*(225.0/appBarLayout.getTotalScrollRange())));
            }
        });
    }
}