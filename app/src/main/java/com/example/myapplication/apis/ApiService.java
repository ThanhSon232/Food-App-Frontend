package com.example.myapplication.apis;

import com.example.myapplication.models.Restaurant;
import com.example.myapplication.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    String baseURL = "http://192.168.100.3:8080/";

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();
    ApiService apiService = new Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @POST("api/account/register")
    Call<User> register(@Body User user);

    @POST("api/account/validate")
    Call<JsonObject> validateOTP(@Body JsonObject user);

    @POST("api/account/login")
    Call<JsonObject> login(@Body JsonObject user);

    @GET("api/restaurant/getFeatured")
    Call<JsonObject> getFeatured(@Query("type") String type);

    @GET("api/product/getPopularItems")
    Call<JsonObject> getPopularItems(@Query("type") String type);

    @GET("/api/restaurant/getAllRestaurant")
    Call<JsonObject> getRestaurantWithFilter(@Query("type") String type, @Query("filter") String filter);

    @GET("/api/product/getAllProduct")
    Call<JsonObject> getProductWithFilter(@Query("type") String type, @Query("filter") String filter);

    @GET("/api/product/getProductDetail")
    Call<JsonObject> getProductDetail(@Query("id") String productId);

    @GET("api/extraProduct/get")
    Call<JsonObject> getProductAdditionFood(@Query("productId")String productId);
}
