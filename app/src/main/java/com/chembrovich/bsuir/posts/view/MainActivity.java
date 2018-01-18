package com.chembrovich.bsuir.posts.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chembrovich.bsuir.posts.R;

public class MainActivity extends AppCompatActivity implements PostListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.post_list_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            PostListFragment postListFragment= new PostListFragment();
            postListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.post_list_fragment, postListFragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
