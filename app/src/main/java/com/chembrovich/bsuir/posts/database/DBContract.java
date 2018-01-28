package com.chembrovich.bsuir.posts.database;

import android.provider.BaseColumns;

public final class DBContract {

    public DBContract() {
    }

    public static abstract class CompanyContract implements BaseColumns {
        public static final String TABLE_NAME = "company";
        public static final String COLUMN_BS = "bs";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATCH_PHRASE = "catch_phrase";


    }

    public static abstract class AddressContract implements BaseColumns {
        public static final String TABLE_NAME = "address";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_SUITE = "suite";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ZIPCODE = "zipcode";
        public static final String COLUMN_LATITUDE = "lat";
        public static final String COLUMN_LONGITUDE = "lng";
    }

    public static abstract class UserContract implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ADDRESS_ID = "address_id";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_WEBSITE = "website";
        public static final String COLUMN_COMPANY_ID = "company_id";
    }

}

