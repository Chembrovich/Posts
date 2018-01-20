package com.chembrovich.bsuir.posts.network.interfaces;

import com.chembrovich.bsuir.posts.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApiInterface {
    @GET("posts/")
    Call<List<Post>> getPostList();
}
