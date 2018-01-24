package com.chembrovich.bsuir.posts.view.interfaces;

import android.app.Activity;

public interface PostListFragmentInterface {
    void updateList();

    Activity getActivity();

    void requestPermissions(String[] permissions, int requestCode);

    void onPostClick(int postId, int userId);

    void showMessage(String message);
}
