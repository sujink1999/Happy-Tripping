package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    EditText searchText;
    Button searchButton;
    ListView listView;
    ArrayAdapter<String> nameAdapter;
    ArrayList<String> articles;
    ArrayList<Blog> blogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        blogs = new ArrayList<Blog>();
        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);
        listView = findViewById(R.id.listView);
        articles = new ArrayList<String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),BlogDisplayActivity.class);
                intent.putExtra("blog",blogs.get(i));
                startActivity(intent);

            }
        });




        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://192.168.43.232:4000/articles/"; // your URL

        queue.start();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                articles.clear();
                blogs.clear();
                if(!searchText.getText().toString().equals("")) {
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url+searchText.getText().toString(),
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
                                        array = response.getJSONArray("blogs");
                                        for(int i=0;i<array.length();i++)
                                        {
                                            articles.add(array.getJSONObject(i).getString("Article_name").toUpperCase());
                                            blogs.add(new Blog(array.getJSONObject(i).getString("Article_writer"),array.getJSONObject(i).getString("Article_name"),array.getJSONObject(i).getString("Body"),array.getJSONObject(i).getString("Location")));
                                        }

                                        //Toast.makeText(SearchActivity.this, array.getJSONObject(1).getString("Article_name"), Toast.LENGTH_SHORT).show();

                                        nameAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,articles);
                                        listView.setAdapter(nameAdapter);


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }


                            , new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //DisplayText.setText("That didn't work!");
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error da", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(jsObjRequest);

                }else{

                    Toast.makeText(SearchActivity.this, "Enter a place please.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}





