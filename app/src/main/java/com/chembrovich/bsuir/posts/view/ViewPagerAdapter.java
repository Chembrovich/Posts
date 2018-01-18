package com.chembrovich.bsuir.posts.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chembrovich.bsuir.posts.R;

public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;
    public ViewPagerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 17;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.post_page_item, container, false);
        LinearLayout firstPostViewInPage = view.findViewById(R.id.first_post);
        TextView idTextView = firstPostViewInPage.findViewById(R.id.post_id);
        idTextView.setText("1");
        firstPostViewInPage = view.findViewById(R.id.second_post);

        firstPostViewInPage.setVisibility(View.INVISIBLE);

        try {
            container.addView(view);
        } finally {

        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
