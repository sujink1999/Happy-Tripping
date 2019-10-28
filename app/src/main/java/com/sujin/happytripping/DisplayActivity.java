package com.sujin.happytripping;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DisplayActivity extends AppCompatActivity {
    TextView title,location,body;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        firebaseAuth = FirebaseAuth.getInstance();
        Blog blog =(Blog) getIntent().getSerializableExtra("blog");

        title = findViewById(R.id.displaytitleblog);
        location=findViewById(R.id.displaylocationblog);
        body=findViewById(R.id.displaybodyvalue);
        body.setMovementMethod(new ScrollingMovementMethod());

        title.setText(blog.articleName.toUpperCase());

        location.setText("About : "+blog.location);
        body.setText(blog.body);



    }
}
