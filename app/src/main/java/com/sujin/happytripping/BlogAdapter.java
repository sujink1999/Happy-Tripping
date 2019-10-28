package com.sujin.happytripping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BlogAdapter extends ArrayAdapter {

    Context mContext;

    ArrayList<Blog> blogs = new ArrayList<Blog>();
    public BlogAdapter(Context context, int resource, ArrayList<Blog> objects) {

        super(context, resource, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.blogs, null);

            //convertView = ((Activity) this).getLayoutInflater().inflate(R.layout.blogs, parent, false);


            TextView authorTextView = (TextView) gridViewAndroid.findViewById(R.id.authorName);
            TextView articleTextView = (TextView) gridViewAndroid.findViewById(R.id.articleName);
            TextView locationTextView = (TextView) gridViewAndroid.findViewById(R.id.locationName);


            Blog blog = (Blog) getItem(position);
            authorTextView.setText("by "+blog.authorName);
            articleTextView.setText(blog.articleName);
            locationTextView.setText("at "+blog.location);


        }else
        {
            gridViewAndroid = (View) convertView;
        }



        return gridViewAndroid;
    }

}
