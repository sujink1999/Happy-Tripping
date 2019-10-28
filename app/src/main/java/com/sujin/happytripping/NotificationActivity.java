package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ArrayAdapter<String> array;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ListView notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationList = findViewById(R.id.notificationListView);
        final ArrayList<String> users = new ArrayList<String>();
        firebaseAuth = FirebaseAuth.getInstance();
        ArrayList<String> names = new ArrayList<String>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");

        notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(NotificationActivity.this,BloggerChatBox.class);
                intent.putExtra("name",firebaseAuth.getCurrentUser().getDisplayName());
                startActivity(intent);

            }
        });

        databaseReference.child(firebaseAuth.getCurrentUser().getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String str = dataSnapshot.getValue().toString();



                users.clear();
                char[] p = str.toCharArray();
                char c;
                String user = "";

                int flag=0;
                for(int i=0;i<str.length();i++)
                {

                    if (p[i] == ',' || p[i] == '}') {
                        users.add(user+" wants to connect!");
                        user = "";
                        flag = 0;

                    }

                    if(flag==1)
                    {
                        user+=p[i];
                    }
                    if(p[i] == '=')
                    {

                        flag=1;

                    }




                }

                array = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,users);
                notificationList.setAdapter(array);



            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }
}
