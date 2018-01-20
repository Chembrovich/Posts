package com.chembrovich.bsuir.posts.presenter;

import com.chembrovich.bsuir.posts.model.Post;
import com.chembrovich.bsuir.posts.network.ApiHandler;
import com.chembrovich.bsuir.posts.network.interfaces.ApiCallbackInterface;
import com.chembrovich.bsuir.posts.view.interfaces.PostListFragmentInterface;
import com.chembrovich.bsuir.posts.presenter.interfaces.PostListPresenterInterface;

import java.util.List;

import retrofit2.Response;

public class PostListPresenter implements PostListPresenterInterface {
    private static final int POSTS_COUNT_IN_PAGE = 6;

    private ApiHandler apiHandler;
    private PostListFragmentInterface view;
    private List<Post> postList;

    public PostListPresenter(PostListFragmentInterface view) {
        this.view = view;
        apiHandler = new ApiHandler();
    }

    @Override
    public void makeRequestToGetPosts() {
        ApiCallbackInterface<List<Post>> getPostsCallback = new ApiCallbackInterface<List<Post>>() {
            @Override
            public void onResponse(Response<List<Post>> response) {
                postList = response.body();
                view.updateList();
            }

            @Override
            public void onFailure() {

            }
        };
        apiHandler.getPostList(getPostsCallback);
    }

    @Override
    public int getPostId(int pageNumber, int position) {
        if (postList == null) {
            return 0;
        }
        int id;
        id = postList.get(((pageNumber) * POSTS_COUNT_IN_PAGE) + position).getPostId();
        return id;
    }

    @Override
    public String getPostTitle(int pageNumber, int position) {
        if (postList == null) {
            return null;
        }
        String title;
        title = postList.get(((pageNumber) * POSTS_COUNT_IN_PAGE) + position).getTitle();
        return title;
    }

    @Override
    public int getPostListSize() {
        if (postList == null) {
            return 0;
        } else {
            return (int) Math.ceil((double) postList.size() / POSTS_COUNT_IN_PAGE);
        }
    }

    @Override
    public int getPostsCountInLastPage() {
        return postList.size() % POSTS_COUNT_IN_PAGE;
    }
}
