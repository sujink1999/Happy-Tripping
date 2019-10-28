package com.sujin.happytripping;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.HashMap;

public class AddBlogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);


        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        final Button button = findViewById(R.id.postButton);

        final EditText articleName = (EditText) findViewById(R.id.addArticleName);
        final EditText location = (EditText) findViewById(R.id.addPlaceName);
        final EditText body = (EditText) findViewById(R.id.textArea_information);

        body.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });




        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://192.168.43.232:4000/articles"; // your URL

        queue.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mname, marticleName, mlocation, mbody;
                marticleName = articleName.getText().toString();
                mlocation = location.getText().toString();
                mbody = body.getText().toString();



                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("Article_name", marticleName); // the entered data as the body.
                params.put("Article_writer", firebaseAuth.getCurrentUser().getDisplayName());
                params.put("Location", mlocation);
                params.put("Body", mbody);
                params.put("Uid", firebaseAuth.getUid());

                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = response;
                                    Toast.makeText(AddBlogActivity.this, jsonObject.getString("validity"), Toast.LENGTH_SHORT).show();
                                    articleName.setText("");
                                    location.setText("");
                                    body.setText("");
                                    params.clear();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(AddBlogActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }



                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //DisplayText.setText("That didn't work!");
                        error.printStackTrace();
                        Toast.makeText(AddBlogActivity.this, "Error da", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });




    }

}




