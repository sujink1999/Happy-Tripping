package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BloggerActivity extends AppCompatActivity {

    ListView bloggerListView;
    Button notifications;
    ArrayList<Blog> blogs;
    BlogAdapter blogAdapter;
    Button addPost;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogger);

        firebaseAuth = FirebaseAuth.getInstance();
        blogs = new ArrayList<Blog>();
        bloggerListView = findViewById(R.id.bloggerListView);
        addPost = findViewById(R.id.addPost);
        notifications = findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });


        bloggerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),DisplayActivity.class);
                intent.putExtra("blog",blogs.get(i));
                startActivity(intent);

            }
        });

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://192.168.43.232:4000/xxx/"; // your URL


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BloggerActivity.this,AddBlogActivity.class);
                startActivity(intent);
            }
        });



        try {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url+firebaseAuth.getUid(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            //mTextView.setText(response.toString());

                            // Process the JSON

                            // Get the JSON array
                            JSONArray array = null;
                            try {
                                array = response.getJSONArray("posts");
                                for (int i = 0; i < array.length(); i++) {
                                    blogs.add(new Blog(array.getJSONObject(i).getString("Article_writer"), array.getJSONObject(i).getString("Article_name"), array.getJSONObject(i).getString("Body"), array.getJSONObject(i).getString("Location")));
                                }

                                //Toast.makeText(SearchActivity.this, array.getJSONObject(1).getString("Article_name"), Toast.LENGTH_SHORT).show();

                                blogAdapter = new BlogAdapter(getApplicationContext(), R.layout.blogs, blogs);
                                bloggerListView.setAdapter(blogAdapter);


                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(BloggerActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //DisplayText.setText("That didn't work!");
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error da", Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(jsObjRequest);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.EmailBuilder().build()
                                ))
                                .build(),
                        1);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Sign in successful!! ", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this,LoginActivity.class);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
        Toast.makeText(this, "Can't go back there!", Toast.LENGTH_SHORT).show();
    }
}
