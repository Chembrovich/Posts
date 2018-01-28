package com.chembrovich.bsuir.posts.presenter.interfaces;

public interface UserPresenterInterface {
    void makeRequestToGetUserInfo();

    String getUserEmail();

    String getUserWebsite();

    String getUserPhoneNumber();

    String getUserCityCoordinates();

    void saveUserInDB();
}
