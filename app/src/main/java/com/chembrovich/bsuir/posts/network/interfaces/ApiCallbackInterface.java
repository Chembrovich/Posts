package com.chembrovich.bsuir.posts.network.interfaces;

import retrofit2.Response;

public interface ApiCallbackInterface<T> {
    void onResponse(Response<T> response);

    void onFailure();
}
