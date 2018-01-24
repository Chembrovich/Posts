package com.chembrovich.bsuir.posts.presenter;

import com.chembrovich.bsuir.posts.model.User;
import com.chembrovich.bsuir.posts.network.ApiHandler;
import com.chembrovich.bsuir.posts.network.interfaces.ApiCallbackInterface;
import com.chembrovich.bsuir.posts.presenter.interfaces.UserPresenterInterface;
import com.chembrovich.bsuir.posts.view.interfaces.UserFragmentInterface;

import retrofit2.Response;

public class UserPresenter implements UserPresenterInterface {

    private static final String CHECK_INTERNET = "Check your internet connection!";

    private int userId;
    private User user;

    private UserFragmentInterface view;
    private ApiHandler apiHandler;

    public UserPresenter(UserFragmentInterface view, int userId) {
        this.view = view;
        this.userId = userId;
        apiHandler = new ApiHandler();
    }

    @Override
    public void makeRequestToGetUserInfo() {
        ApiCallbackInterface<User> getUserCallback = new ApiCallbackInterface<User>() {
            @Override
            public void onResponse(Response<User> response) {
                user = response.body();
                setUserViewData(user);
            }

            @Override
            public void onFailure() {
                view.showMessage(CHECK_INTERNET);
            }
        };
        apiHandler.getUserById(getUserCallback, userId);
    }

    private void setUserViewData(User user) {
        view.setUserName(user.getName());
        view.setUserNick(user.getUsername());
        view.setUserEmail(user.getEmail());
        view.setUserWebsite(user.getWebsite());
        view.setUserPhoneNumber(user.getPhone());
        view.setUserCity(user.getAddress().getCity());

        view.setUserInfoContainerVisible();
    }
}
