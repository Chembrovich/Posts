package com.chembrovich.bsuir.posts.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chembrovich.bsuir.posts.R;
import com.chembrovich.bsuir.posts.presenter.PostListPresenter;
import com.chembrovich.bsuir.posts.presenter.interfaces.PostListPresenterInterface;
import com.chembrovich.bsuir.posts.view.interfaces.PostListFragmentInterface;

public class PostListFragment extends Fragment implements PostListFragmentInterface {

    private OnFragmentInteractionListener mListener;
    private PostListPresenterInterface presenter;
    private ViewPagerAdapter viewPagerAdapter;

    public PostListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        presenter = new PostListPresenter(this);
        presenter.makeRequestToGetPosts();

        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(this.getContext(), presenter);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateList() {
        viewPagerAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
