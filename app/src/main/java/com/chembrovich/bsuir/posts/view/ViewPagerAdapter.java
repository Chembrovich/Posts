package com.chembrovich.bsuir.posts.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chembrovich.bsuir.posts.R;
import com.chembrovich.bsuir.posts.presenter.interfaces.PostListPresenterInterface;

class ViewPagerAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private PostListPresenterInterface presenter;

    ViewPagerAdapter(Context context, PostListPresenterInterface presenter) {
        this.presenter = presenter;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return presenter.getPostListSize();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View mainView = inflater.inflate(R.layout.post_page_item, container, false);
        LinearLayout page = mainView.findViewById(R.id.post_page_item);

        if (position != getCount() - 1) {

            for (int i = 0; i < page.getChildCount(); i++) {
                View rowOfPage = page.getChildAt(i);

                if (rowOfPage instanceof ViewGroup) {

                    for (int j = 0; j < ((ViewGroup) rowOfPage).getChildCount(); j++) {
                        View postView = ((ViewGroup) rowOfPage).getChildAt(j);

                        int postPositionInPage = (i * (page.getChildCount() + 1)) + j;
                        instantiatePostView(postView, position, postPositionInPage);
                    }
                }
            }
        } else {

            int postNumberInLastPage = 0;
            int postsCountInLastPage = presenter.getPostsCountInLastPage();

            for (int i = 0; i < page.getChildCount(); i++) {
                View rowOfPage = page.getChildAt(i);

                if (rowOfPage instanceof ViewGroup) {

                    for (int j = 0; j < ((ViewGroup) rowOfPage).getChildCount(); j++) {
                        View postView = ((ViewGroup) rowOfPage).getChildAt(j);

                        if (postNumberInLastPage < postsCountInLastPage) {
                            int postPositionInPage = (i * (page.getChildCount() + 1)) + j;
                            instantiatePostView(postView, position, postPositionInPage);
                        } else {
                            postView.setVisibility(View.INVISIBLE);
                        }
                        postNumberInLastPage++;
                    }
                }
            }

            View view = page.findViewById(R.id.first_post);
            instantiatePostView(view, position, 0);
            view = page.findViewById(R.id.sixth_post);
            view.setVisibility(View.INVISIBLE);
        }

        container.addView(page);
        return page;
    }

    private void instantiatePostView(View postView, final int pageNumber, final int postPositionInPage) {

        postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPostClick(pageNumber, postPositionInPage);
            }
        });

        TextView idTextView = postView.findViewById(R.id.post_id);
        TextView titleTextView = postView.findViewById(R.id.post_title);

        String id = Integer.toString(presenter.getPostId(pageNumber, postPositionInPage));
        String title = presenter.getPostTitle(pageNumber, postPositionInPage);

        idTextView.setText(id);
        titleTextView.setText(title);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
