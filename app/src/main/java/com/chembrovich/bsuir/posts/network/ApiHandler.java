package com.chembrovich.bsuir.posts.network;

import com.chembrovich.bsuir.posts.model.Post;
import com.chembrovich.bsuir.posts.model.User;
import com.chembrovich.bsuir.posts.network.interfaces.ApiCallbackInterface;
import com.chembrovich.bsuir.posts.network.interfaces.PostApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private static final String POST_API_BASE_URL = "http://jsonplaceholder.typicode.com/";
    private PostApiInterface postApi;

    public ApiHandler() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(POST_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        postApi = retrofit.create(PostApiInterface.class);
    }

    public void getPostList(final ApiCallbackInterface<List<Post>> callback) {//as param Generic <T>, and in the Presenter true type
        postApi.getPostList().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                try {
                    callback.onResponse(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getUserById(final ApiCallbackInterface<User> callback, int userId) {
        postApi.getUserById(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
