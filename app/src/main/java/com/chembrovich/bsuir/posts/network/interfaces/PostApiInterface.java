package com.chembrovich.bsuir.posts.network.interfaces;

import com.chembrovich.bsuir.posts.model.Post;
import com.chembrovich.bsuir.posts.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostApiInterface {
    @GET("posts/")
    Call<List<Post>> getPostList();

    @GET("/users/{user_id}")
    Call<User> getUserById(@Path("user_id") int userId);
}
