package com.chembrovich.bsuir.posts.presenter;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.chembrovich.bsuir.posts.database.DBContract.CompanyContract;
import com.chembrovich.bsuir.posts.database.DBContract.UserContract;
import com.chembrovich.bsuir.posts.database.DBContract.AddressContract;
import com.chembrovich.bsuir.posts.database.DBHelper;
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
    private SQLiteDatabase db;

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

    @Override
    public String getUserEmail() {
        return user.getEmail();
    }

    @Override
    public String getUserWebsite() {
        final String httpString = "http://";
        final String httpsString = "https://";

        String url = user.getWebsite();
        if (!url.startsWith(httpString) && !url.startsWith(httpsString))
            url = httpString + url;
        return url;
    }

    @Override
    public String getUserPhoneNumber() {
        return user.getPhone();
    }

    @Override
    public String getUserCityCoordinates() {
        return user.getAddress().getGeo().getLat() + "," + user.getAddress().getGeo().getLng();
    }

    @Override
    public void saveUserInDB() {
        DBHelper dbHelper = new DBHelper(view.getViewContext());
        db = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(CompanyContract.COLUMN_NAME, user.getCompany().getName());
        values.put(CompanyContract.COLUMN_CATCH_PHRASE, user.getCompany().getCatchPhrase());
        values.put(CompanyContract.COLUMN_BS, user.getCompany().getBs());

// Insert the new row, returning the primary key value of the new row
        long companyId = db.insert(CompanyContract.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(AddressContract.COLUMN_STREET, user.getAddress().getStreet());
        values.put(AddressContract.COLUMN_SUITE, user.getAddress().getSuite());
        values.put(AddressContract.COLUMN_CITY, user.getAddress().getCity());
        values.put(AddressContract.COLUMN_ZIPCODE, user.getAddress().getZipcode());
        values.put(AddressContract.COLUMN_LATITUDE, user.getAddress().getGeo().getLat());
        values.put(AddressContract.COLUMN_LONGITUDE, user.getAddress().getGeo().getLng());

// Insert the new row, returning the primary key value of the new row
        long addressId = db.insert(AddressContract.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(UserContract._ID, user.getId());
        values.put(UserContract.COLUMN_NAME, user.getName());
        values.put(UserContract.COLUMN_USERNAME, user.getUsername());
        values.put(UserContract.COLUMN_EMAIL, user.getEmail());
        values.put(UserContract.COLUMN_ADDRESS_ID, addressId);
        values.put(UserContract.COLUMN_PHONE, user.getPhone());
        values.put(UserContract.COLUMN_WEBSITE, user.getWebsite());
        values.put(UserContract.COLUMN_COMPANY_ID, companyId);

// Insert the new row, returning the primary key value of the new row
        long userId = db.insert(UserContract.TABLE_NAME, null, values);

        db.close();
    }
}
