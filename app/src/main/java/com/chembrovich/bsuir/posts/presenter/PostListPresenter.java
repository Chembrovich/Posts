package com.chembrovich.bsuir.posts.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.chembrovich.bsuir.posts.R;
import com.chembrovich.bsuir.posts.model.Post;
import com.chembrovich.bsuir.posts.network.ApiHandler;
import com.chembrovich.bsuir.posts.network.interfaces.ApiCallbackInterface;
import com.chembrovich.bsuir.posts.view.interfaces.PostListFragmentInterface;
import com.chembrovich.bsuir.posts.presenter.interfaces.PostListPresenterInterface;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class PostListPresenter implements PostListPresenterInterface {
    private static final int POSTS_COUNT_IN_PAGE = 6;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private static final String PERMISSION_DENIED = "Permission denied!";
    private static final String EXTERNAL_STORAGE_NOT_WRITABLE = "External storage is not writable";
    private static final String CHECK_INTERNET = "Check internet connection!";

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
                view.showMessage(CHECK_INTERNET);
            }
        };
        apiHandler.getPostList(getPostsCallback);
    }

    @Override
    public int getPostId(int pageNumber, int position) {
        if (postList == null) {
            return 0;
        }
        int id = postList.get(((pageNumber) * POSTS_COUNT_IN_PAGE) + position).getPostId();
        return id;
    }

    @Override
    public String getPostTitle(int pageNumber, int position) {
        if (postList == null) {
            return null;
        }
        return postList.get(((pageNumber) * POSTS_COUNT_IN_PAGE) + position).getTitle();
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

    @Override
    public void requestToWriteLogsToFile() {
        if (writeIsAllowed()) {
            writeLogsToFile();
        } else {
            requestWriteExternalStoragePermission();
        }
    }

    private void writeLogsToFile() {
        if (isExternalStorageWritable()) {

            File appDirectory = new File(Environment.getExternalStorageDirectory() + "/PostsLogs");
            File logFile = new File(appDirectory, "logcat" + System.currentTimeMillis() + ".txt");

            if (!appDirectory.exists()) {
                appDirectory.mkdir();
            }
            try {
                Runtime.getRuntime().exec("logcat -d");
                Runtime.getRuntime().exec("logcat -f " + logFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            view.showMessage(EXTERNAL_STORAGE_NOT_WRITABLE);
        }
    }

    private void requestWriteExternalStoragePermission() {
        view.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
    }

    private boolean writeIsAllowed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return writeExternalStoragePermissionIsGranted();
        } else {
            return true;
        }
    }

    private boolean writeExternalStoragePermissionIsGranted() {
        return ActivityCompat.checkSelfPermission(view.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            writeLogsToFile();
        }
    }

    private void onPermissionsDenied(int requestCode) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            view.showMessage(PERMISSION_DENIED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PostListPresenter.REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionsGranted(requestCode);
                } else {
                    onPermissionsDenied(requestCode);
                }
            }
        }
    }
}
