package com.chembrovich.bsuir.posts.view.interfaces;

import android.content.Context;

public interface UserFragmentInterface {
    void showMessage(String message);

    void setUserName(String name);

    void setUserNick(String nick);

    void setUserEmail(String email);

    void setUserWebsite(String website);

    void setUserPhoneNumber(String phoneNumber);

    void setUserCity(String city);

    void setUserInfoContainerVisible();

    Context getViewContext();
}
