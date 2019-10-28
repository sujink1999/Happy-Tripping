package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlogDisplayActivity extends AppCompatActivity {


    TextView author,title,location,body;
    Button connectButton;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_display);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        firebaseAuth = FirebaseAuth.getInstance();
        final Blog blog =(Blog) getIntent().getSerializableExtra("blog");
        author = findViewById(R.id.authorblog);
        title = findViewById(R.id.titleblog);
        location=findViewById(R.id.locationblog);
        body=findViewById(R.id.bodyvalue);
        body.setMovementMethod(new ScrollingMovementMethod());

        title.setText(blog.articleName.toUpperCase());
        author.setText("Author : "+blog.authorName);
        location.setText("About : "+blog.location);
        body.setText(blog.body);

        connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference
                        .child(blog.getAuthorName()).child(firebaseAuth.getUid())
                        .setValue(firebaseAuth.getCurrentUser().getDisplayName());
                Intent i = new Intent(getApplicationContext(),ChatBox.class);
                i.putExtra("blogger",firebaseAuth.getCurrentUser().getDisplayName());
                startActivity(i);
            }
        });

    }
}
