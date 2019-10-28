package com.sujin.happytripping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

    /*public class ImageGridAdapter extends BaseAdapter {

        private Context mContext;
        private List<Integer> mImageIds;

        public ImageGridAdapter(Context context, List<Integer> imageIds) {
            mContext = context;
            mImageIds = imageIds;
        }


        *//**
         * Returns the number of items the adapter will display
         *//*
        @Override
        public int getCount() {
            return mImageIds.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        *//**
         * Creates a new ImageView for each item referenced by the adapter
         *//*
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // If the view is not recycled, this creates a new ImageView to hold an image
                imageView = new ImageView(mContext);
                // Define the layout parameters
                //imageView.setAdjustViewBounds(true);
                //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            // Set the image resource and return the newly created ImageView
            //imageView.setImageResource(mImageIds.get(position));

            Bitmap bmp;
            int width=300;
            int height=300;
            bmp = BitmapFactory.decodeResource(mContext.getResources(),mImageIds.get(position));//image is your image
            bmp= Bitmap.createScaledBitmap(bmp, width,height, true);
            imageView.setImageBitmap(bmp);
            return imageView;
        }

    }*/


public class ImageGridAdapter extends BaseAdapter {

    private Context mContext;
    private final ArrayList<String> gridViewString;
    private final ArrayList<Integer> gridViewImageId;

    public ImageGridAdapter(Context context, ArrayList<String> gridViewString, ArrayList<Integer> gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        RoundBitmap roundBitmap = new RoundBitmap();
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.image_name2, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.imageName_name);
            ImageView imageView = (ImageView) gridViewAndroid.findViewById(R.id.imageName_image);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
            textViewAndroid.setText(gridViewString.get(i));
            try {
                imageView.setImageResource(gridViewImageId.get(i));
            }catch (Exception e)
            {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(),gridViewImageId.get(i),null);
                Bitmap bitmap1 = ((BitmapDrawable)drawable).getBitmap();
                bitmap1.compress(Bitmap.CompressFormat.JPEG,40,byteArrayOutputStream);
                byte[] BYTE = byteArrayOutputStream.toByteArray();
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
                imageView.setImageBitmap(roundBitmap.getRoundedCornerBitmap(bitmap2,100));
            }
        } else {
            gridViewAndroid = (View) convertView;
        }



        return gridViewAndroid;
    }
}

