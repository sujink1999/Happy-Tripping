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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NavigationActivity extends AppCompatActivity {

    ListView navListView;
    Button searchMoreButton;
    ArrayList<String> chosenImages = new ArrayList<String>();
    ArrayList<Blog> blogs;
    BlogAdapter blogAdapter;
     public static final int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);




        blogs = new ArrayList<Blog>();
        navListView = findViewById(R.id.navListView);
        searchMoreButton = findViewById(R.id.searchMoreButton);
        searchMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        navListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),BlogDisplayActivity.class);
                intent.putExtra("blog",blogs.get(i));
                startActivity(intent);

            }
        });
        chosenImages = getIntent().getStringArrayListExtra("chosenImages");

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://192.168.43.232:4000/fivefeed"; // your URL


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("cn1", chosenImages.get(0)); // the entered data as the body.
        params.put("cn2", chosenImages.get(1));
        params.put("cn3", chosenImages.get(2));
        params.put("cn4", chosenImages.get(3));
        params.put("cn5", chosenImages.get(4));


        try {
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with response
                            //mTextView.setText(response.toString());

                            // Process the JSON

                            // Get the JSON array
                            JSONArray array = null;
                            try {
                                array = response.getJSONArray("fivearticles");
                                for (int i = 0; i < array.length(); i++) {
                                    blogs.add(new Blog(array.getJSONObject(i).getString("Article_writer"), array.getJSONObject(i).getString("Article_name"), array.getJSONObject(i).getString("Body"), array.getJSONObject(i).getString("Location")));
                                }

                                //Toast.makeText(SearchActivity.this, array.getJSONObject(1).getString("Article_name"), Toast.LENGTH_SHORT).show();

                                blogAdapter = new BlogAdapter(getApplicationContext(), R.layout.blogs, blogs);
                                navListView.setAdapter(blogAdapter);


                            } catch (Exception e) {
                                e.printStackTrace();
                                //Toast.makeText(NavigationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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


        switch(item.getItemId()){

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
                        RC_SIGN_IN);


            default:
                return super.onOptionsItemSelected(item);
        }



        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
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



