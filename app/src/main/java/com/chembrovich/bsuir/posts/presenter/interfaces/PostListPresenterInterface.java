package com.chembrovich.bsuir.posts.presenter.interfaces;

import android.support.annotation.NonNull;

public interface PostListPresenterInterface {
    void makeRequestToGetPosts();

    int getPostListSize();

    int getPostId(int pageNumber, int position);

    String getPostTitle(int pageNumber, int position);

    int getPostsCountInLastPage();

    void requestToWriteLogsToFile();

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
