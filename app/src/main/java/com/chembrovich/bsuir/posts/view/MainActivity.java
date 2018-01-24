package com.chembrovich.bsuir.posts.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chembrovich.bsuir.posts.R;

public class MainActivity extends AppCompatActivity implements PostListFragment.OnPostClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            PostListFragment postListFragment= new PostListFragment();
            postListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, postListFragment).commit();
        }
    }

    @Override
    public void onPostClick(int postId, int userId) {
        UserFragment userFragment = UserFragment.newInstance(postId, userId);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, userFragment).addToBackStack(null).commit(); //replace
    }
}
