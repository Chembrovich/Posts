package com.chembrovich.bsuir.posts.presenter.interfaces;

public interface PostListPresenterInterface {
    void makeRequestToGetPosts();

    int getPostListSize();

    int getPostId(int pageNumber, int position);

    String getPostTitle(int pageNumber, int position);

    int getPostsCountInLastPage();
}
