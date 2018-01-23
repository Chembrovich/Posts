package com.chembrovich.bsuir.posts.view.interfaces;

import android.app.Activity;
import android.content.Context;

public interface PostListFragmentInterface {
    void updateList();

    Activity getActivity();

    void requestPermissions(String[] permissions, int requestCode);

    void showMessage(String message);
}
