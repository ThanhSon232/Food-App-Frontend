package com.example.myapplication;

import static com.example.myapplication.apis.ApiService.gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.apis.ApiService;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.Restaurant;
import com.example.myapplication.models.adapters.CategoryAdapter;
import com.example.myapplication.models.adapters.PopularItemsAdapter;
import com.example.myapplication.models.adapters.RestaurantAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView options;
    RecyclerView restaurants;
    RecyclerView popularItems;
    CategoryAdapter categoryAdapter;
    RestaurantAdapter restaurantAdapter;
    RecyclerView.AdapterDataObserver adapterDataObserver;
    ShimmerFrameLayout featuredShimmer;
    ShimmerFrameLayout popularShimmer;
    ImageButton menu;
    AppCompatButton logout;
    List<Restaurant> mcList;
    PopularItemsAdapter popularItemsAdapter;
    List<Product> productList;
    TextView viewAll;
    TextView viewItemsAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.main_menu);
        options = findViewById(R.id.main_options);
        featuredShimmer = findViewById(R.id.main_featured_shimmer);
        popularItems = findViewById(R.id.main_popular);
        popularShimmer = findViewById(R.id.main_popular_shimmer);
        restaurants = findViewById(R.id.main_featured);
        viewAll = findViewById(R.id.main_view_all);
        logout = findViewById(R.id.log_out);
        viewItemsAll = findViewById(R.id.main_items_view_all);
        viewItemsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", categoryAdapter.getCurrentItem().getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setUp();
        setUpFeatured();
    }

    private void getPopularItems(String type) {
        ApiService.apiService.getPopularItems(type).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Product[] mcArray = gson.fromJson(response.body().get("data"), Product[].class);
                System.out.println(response.body().get("data"));

                productList = Arrays.asList(mcArray);
                popularItemsAdapter.setProductList(productList);
                popularItems.setAdapter(popularItemsAdapter);
                popularShimmer.stopShimmer();
                popularShimmer.setVisibility(View.GONE);
                popularItems.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", categoryAdapter.getCurrentItem().getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setUpFeatured() {
        restaurantAdapter = new RestaurantAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        restaurants.setLayoutManager(linearLayoutManager);

        getListRestaurant(categoryAdapter.getCurrentItem().getTitle());

    }

    private void getListRestaurant(String type) {
        ApiService.apiService.getFeatured(type).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Restaurant[] mcArray = gson.fromJson(response.body().get("data"), Restaurant[].class);
                mcList = Arrays.asList(mcArray);
                restaurantAdapter.setData(mcList);
                restaurants.setAdapter(restaurantAdapter);
                featuredShimmer.stopShimmer();
                featuredShimmer.setVisibility(View.GONE);
                restaurants.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void setUp() {
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        options.setLayoutManager(linearLayoutManager);

        popularItemsAdapter = new PopularItemsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularItems.setLayoutManager(layoutManager);

        adapterDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                featuredShimmer.startShimmer();
                featuredShimmer.setVisibility(View.VISIBLE);
                restaurants.setVisibility(View.GONE);
                popularShimmer.startShimmer();
                popularShimmer.setVisibility(View.VISIBLE);
                popularItems.setVisibility(View.GONE);
                getPopularItems(categoryAdapter.getCurrentItem().getTitle());
                getListRestaurant(categoryAdapter.getCurrentItem().getTitle());

            }
        };



        categoryAdapter.registerAdapterDataObserver(adapterDataObserver);
        categoryAdapter.setData(getListCategory(), 0);
        options.setAdapter(categoryAdapter);

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", categoryAdapter.getCurrentItem().getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout navDrawer = findViewById(R.id.drawer);
                if(!navDrawer.isDrawerOpen(GravityCompat.START)) navDrawer.openDrawer(GravityCompat.START);
                else navDrawer.closeDrawer(GravityCompat.END);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("MYAPPLICATION", Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = settings.edit();
                prefsEditor.remove("user");
                prefsEditor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private List<Category> getListCategory() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(R.drawable.hamburger, "Burger", true));
        categoryList.add(new Category(R.drawable.grill, "Grill", false));
        categoryList.add(new Category(R.drawable.noodle, "Noodle", false));
        categoryList.add(new Category(R.drawable.meet, "Meat", false));
        categoryList.add(new Category(R.drawable.rice, "Rice", false));
        return categoryList;
    }


}