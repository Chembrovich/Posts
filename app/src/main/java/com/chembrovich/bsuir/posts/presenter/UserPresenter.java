package com.chembrovich.bsuir.posts.presenter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.chembrovich.bsuir.posts.database.DBContract.CompanyContract;
import com.chembrovich.bsuir.posts.database.DBContract.UserContract;
import com.chembrovich.bsuir.posts.database.DBContract.AddressContract;
import com.chembrovich.bsuir.posts.database.DBHelper;
import com.chembrovich.bsuir.posts.model.Address;
import com.chembrovich.bsuir.posts.model.Company;
import com.chembrovich.bsuir.posts.model.User;
import com.chembrovich.bsuir.posts.network.ApiHandler;
import com.chembrovich.bsuir.posts.network.interfaces.ApiCallbackInterface;
import com.chembrovich.bsuir.posts.presenter.interfaces.UserPresenterInterface;
import com.chembrovich.bsuir.posts.view.interfaces.UserFragmentInterface;

import retrofit2.Response;

public class UserPresenter implements UserPresenterInterface {

    private static final String CHECK_INTERNET = "Check your internet connection!";
    private static final String USER_IS_EXISTS = "This user is already exists in DB";
    private static final String USER_IS_ADDED = "New user is added to DB";

    private static final String equalsSelectionStatement = " = ? ";
    private final static String andSelectionStatement = " AND ";

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
        if (view != null) {

            view.setUserName(user.getName());
            view.setUserNick(user.getUsername());
            view.setUserEmail(user.getEmail());
            view.setUserWebsite(user.getWebsite());
            view.setUserPhoneNumber(user.getPhone());
            view.setUserCity(user.getAddress().getCity());

            view.setUserInfoContainerVisible();

        }
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
        DBTask dbTask = new DBTask();
        dbTask.execute();
    }

    private long insertUserCompanyInDB(SQLiteDatabase db, Company company) {
        ContentValues values = new ContentValues();
        values.put(CompanyContract.COLUMN_NAME, company.getName());
        values.put(CompanyContract.COLUMN_CATCH_PHRASE, company.getCatchPhrase());
        values.put(CompanyContract.COLUMN_BS, company.getBs());

        return db.insert(CompanyContract.TABLE_NAME, null, values);
    }

    private long insertUserAddressInDB(SQLiteDatabase db, Address address) {
        ContentValues values = new ContentValues();
        values.put(AddressContract.COLUMN_STREET, address.getStreet());
        values.put(AddressContract.COLUMN_SUITE, address.getSuite());
        values.put(AddressContract.COLUMN_CITY, address.getCity());
        values.put(AddressContract.COLUMN_ZIPCODE, address.getZipcode());
        values.put(AddressContract.COLUMN_LATITUDE, address.getGeo().getLat());
        values.put(AddressContract.COLUMN_LONGITUDE, address.getGeo().getLng());

        return db.insert(AddressContract.TABLE_NAME, null, values);
    }

    private long insertUserInDB(SQLiteDatabase db, User user, long addressId, long companyId) {
        ContentValues values = new ContentValues();
        values.put(UserContract._ID, user.getId());
        values.put(UserContract.COLUMN_NAME, user.getName());
        values.put(UserContract.COLUMN_USERNAME, user.getUsername());
        values.put(UserContract.COLUMN_EMAIL, user.getEmail());
        values.put(UserContract.COLUMN_ADDRESS_ID, addressId);
        values.put(UserContract.COLUMN_PHONE, user.getPhone());
        values.put(UserContract.COLUMN_WEBSITE, user.getWebsite());
        values.put(UserContract.COLUMN_COMPANY_ID, companyId);

        return db.insert(UserContract.TABLE_NAME, null, values);
    }

    private long selectCompanyIdFromDB(Company company) {
        String[] projection = {CompanyContract._ID};

        String selection = CompanyContract.COLUMN_NAME + equalsSelectionStatement;
        String[] selectionArgs = new String[]{company.getName()};

        Cursor cursor = db.query(
                CompanyContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        long companyId = -1;

        if (cursor.moveToFirst()) {
            companyId = cursor.getLong(cursor.getColumnIndex(CompanyContract._ID));
        }

        cursor.close();

        return companyId;
    }

    private long selectAddressIdFromDB(Address address) {
        String[] projection = {AddressContract._ID};

        String selection = AddressContract.COLUMN_LONGITUDE + equalsSelectionStatement +
                andSelectionStatement + AddressContract.COLUMN_LATITUDE + equalsSelectionStatement;
        String[] selectionArgs = new String[]{address.getGeo().getLng(), address.getGeo().getLat()};

        Cursor cursor = db.query(
                AddressContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        long addressId = -1;

        if (cursor.moveToFirst()) {
            addressId = cursor.getLong(cursor.getColumnIndex(AddressContract._ID));
        }

        cursor.close();

        return addressId;
    }

    private class DBTask extends AsyncTask<Void, Void, Long> {
        DBHelper dbHelper;

        @Override
        protected void onPreExecute() {
            dbHelper = new DBHelper(view.getViewContext());
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Void... voids) {
            db = dbHelper.getWritableDatabase();

            long companyId = insertUserCompanyInDB(db, user.getCompany());

            if (companyId == -1) {
                companyId = selectCompanyIdFromDB(user.getCompany());
            }

            long addressId = insertUserAddressInDB(db, user.getAddress());

            if (addressId == -1) {
                addressId = selectAddressIdFromDB(user.getAddress());
            }

            long userId = insertUserInDB(db, user, addressId, companyId);

            return userId;
        }

        @Override
        protected void onPostExecute(Long userId) {
            db.close();

            if (userId == -1) {
                view.showMessage(USER_IS_EXISTS);
            } else {
                view.showMessage(USER_IS_ADDED);
            }

            super.onPostExecute(userId);
        }
    }
}
