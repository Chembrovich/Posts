package com.chembrovich.bsuir.posts.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chembrovich.bsuir.posts.R;
import com.chembrovich.bsuir.posts.presenter.PostListPresenter;
import com.chembrovich.bsuir.posts.presenter.interfaces.PostListPresenterInterface;
import com.chembrovich.bsuir.posts.view.interfaces.PostListFragmentInterface;

public class PostListFragment extends Fragment implements PostListFragmentInterface {

    private OnPostClickListener onPostClickListener;
    private PostListPresenterInterface presenter;
    private ViewPagerAdapter viewPagerAdapter;

    public PostListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        final Button logButton = view.findViewById(R.id.log_button);
        logButton.setVisibility(View.INVISIBLE);

        presenter = new PostListPresenter(this);
        presenter.makeRequestToGetPosts();

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestToWriteLogsToFile();
            }
        });

        ViewPager viewPager = view.findViewById(R.id.pager);

        viewPagerAdapter = new ViewPagerAdapter(this.getContext(), presenter);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_dots);
        tabLayout.setupWithViewPager(viewPager, true);

        ImageView imageView = view.findViewById(R.id.image);

        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.rotate_image);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animation);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPostClickListener) {
            onPostClickListener = (OnPostClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPostClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onPostClickListener = null;
    }

    @Override
    public void updateList() {
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostClick(int postId, int userId) {
        onPostClickListener.onPostClick(postId, userId);
    }

    interface OnPostClickListener {
        void onPostClick(int postId, int userId);
    }

}

