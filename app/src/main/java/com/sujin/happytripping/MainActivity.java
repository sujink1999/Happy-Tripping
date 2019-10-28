package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jackandphantom.blurimage.BlurImage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridView);
        final ArrayList<Integer> images = new ArrayList<Integer>() {
            {
                add(R.drawable.image1);
                add(R.drawable.image2);
                add(R.drawable.image3);
                add(R.drawable.image4);
                add(R.drawable.image5);
                add(R.drawable.image6);
                add(R.drawable.image7);
                add(R.drawable.image8);
                add(R.drawable.image9);
                add(R.drawable.image10);
                add(R.drawable.image11);
                add(R.drawable.image12);
                add(R.drawable.image13);
                add(R.drawable.image14);
                add(R.drawable.image15);
            }
        };

        final Button nextButton = findViewById(R.id.nextButton);
        final ArrayList<String> chosenImages = new ArrayList<String>();
        final ArrayList<String> names = new ArrayList<String>() {
            {
                add("chennai");
                add("punjab");
                add("hyderabad");
                add("delhi");
                add("kolkata");
                add("kerala");
                add("andaman");
                add("darjeeling");
                add("ooty");
                add("sikkim");
                add("coorg");
                add("kodaikanal");
                add("pondicherry");
                add("madurai");
                add("thirupathi");
            }
        };

        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getApplicationContext(), names, images);
        gridView.setAdapter(imageGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View imgView, int position, long id) {

                try{if (chosenImages.contains(names.get(position))) {
                    chosenImages.remove(names.get(position));
                    if (chosenImages.size() == 4) {
                        nextButton.setAlpha(0.5f);
                    } else if (chosenImages.size() == 6) {
                        nextButton.setAlpha(0.5f);
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) imgView;
                    ImageView imageView = (ImageView) imgView.findViewById(R.id.imageName_image);
                    imageView.setImageResource(images.get(position));


                } else {
                    chosenImages.add(names.get(position));
                    if (chosenImages.size() == 5) {
                        nextButton.setAlpha(1);
                    }
                    RelativeLayout relativeLayout = (RelativeLayout) imgView;
                    ImageView imageView = (ImageView) imgView.findViewById(R.id.imageName_image);
                    BlurImage.with(getApplicationContext()).load(images.get(position)).intensity(25).Async(true).into(imageView);
                }
                }catch(Exception e)
                {

                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chosenImages.size() !=5) {
                    Toast.makeText(getApplicationContext(), "Please select 5 places.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                    intent.putStringArrayListExtra("chosenImages",chosenImages);
                    startActivity(intent);
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        //do nothing
        Toast.makeText(this, "Can't go back there!", Toast.LENGTH_SHORT).show();
    }
}








