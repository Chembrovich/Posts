package com.chembrovich.bsuir.posts.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chembrovich.bsuir.posts.R;
import com.chembrovich.bsuir.posts.presenter.UserPresenter;
import com.chembrovich.bsuir.posts.presenter.interfaces.UserPresenterInterface;
import com.chembrovich.bsuir.posts.view.interfaces.UserFragmentInterface;

import java.util.Locale;

public class UserFragment extends Fragment implements UserFragmentInterface {

    private static final String POST_ID_ARG = "post_id";
    private static final String USER_ID_ARG = "user_id";

    private UserPresenterInterface presenter;

    private int postId;
    private int userId;

    private LinearLayout userInfoContainer;
    private TextView userNameView;
    private TextView userNickView;
    private TextView userEmailView;
    private TextView userWebsiteView;
    private TextView userPhoneNumberView;
    private TextView userCityView;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(int postId, int userId) {
        UserFragment fragment = new UserFragment();

        Bundle args = new Bundle();

        args.putInt(POST_ID_ARG, postId);
        args.putInt(USER_ID_ARG, userId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            postId = getArguments().getInt(POST_ID_ARG);
            userId = getArguments().getInt(USER_ID_ARG);
        }

        presenter = new UserPresenter(this, userId);
        presenter.makeRequestToGetUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        TextView postIdView = view.findViewById(R.id.user_post_id);
        postIdView.setText(String.format(Locale.getDefault(), "%d", postId)); /*String.format(Locale.getDefault(),"%d",*/

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Contact #" + Integer.toString(userId));

        userInfoContainer = view.findViewById(R.id.user_info_container);

        userNameView = view.findViewById(R.id.user_name);
        userNickView = view.findViewById(R.id.user_nick);
        userEmailView = view.findViewById(R.id.user_email);
        userWebsiteView = view.findViewById(R.id.user_website);
        userPhoneNumberView = view.findViewById(R.id.user_phone_number);
        userCityView = view.findViewById(R.id.user_city);

        Button saveToDbBtn = view.findViewById(R.id.save_to_db_btn);

        saveToDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("Hello, DB))))");
            }
        });

        return view;
    }

    @Override
    public void setUserName(String name) {
        userNameView.setText(name);
    }

    @Override
    public void setUserNick(String nick) {
        userNickView.setText(nick);
    }

    @Override
    public void setUserEmail(String email) {
        userEmailView.setText(getString(R.string.user_email, email));
    }

    @Override
    public void setUserWebsite(String website) {
        userWebsiteView.setText(getString(R.string.user_website, website));
    }

    @Override
    public void setUserPhoneNumber(String phoneNumber) {
        userPhoneNumberView.setText(getString(R.string.user_phone_number, phoneNumber));
    }

    @Override
    public void setUserCity(String city) {
        userCityView.setText(getString(R.string.user_city, city));
    }

    @Override
    public void setUserInfoContainerVisible() {
        userInfoContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
