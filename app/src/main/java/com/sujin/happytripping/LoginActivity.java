package com.sujin.happytripping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private ChildEventListener mChildEventListener;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    TextView textView;
    DataSnapshot data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = ANONYMOUS;


        //Pushing data into the realtime database
        initialize();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = (Button) findViewById(R.id.submitButton);



        radioGroup.setVisibility(View.VISIBLE);
        btnDisplay.setText("Submit");
        textView = findViewById(R.id.textView4);
        textView.setText("I am a...");




        /*mDatabaseReference.orderByKey().equalTo(mFirebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists
                    Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
                    startActivity(intent);

                } else {
                    //Key does not exist
                    radioGroup.setVisibility(View.VISIBLE);
                    btnDisplay.setVisibility(View.VISIBLE);
                    textView = findViewById(R.id.textView4);
                    textView.setText("I am a...");


                }
                @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
        */



        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                if(btnDisplay.getText().toString().equals("Go")) {





                    mDatabaseReference.child(mFirebaseAuth.getUid()).child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {






                            //Toast.makeText(LoginActivity.this,dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                            if (dataSnapshot.exists()) {
                                //Key exists

                                if(dataSnapshot.getValue().toString().equals("  Travel Blogger"))
                                {
                                    Intent intent = new Intent(getApplicationContext(),BloggerActivity.class);
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                }



                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {

                        }
                    });





                }
                else{
                try {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);

                    Toast.makeText(LoginActivity.this,
                            radioButton.getText(), Toast.LENGTH_SHORT).show();
                    UserDetails userDetails = new UserDetails(mUsername, radioButton.getText().toString());
                    mDatabaseReference
                            .child(mFirebaseAuth.getCurrentUser().getUid())
                            .setValue(userDetails);
                    if (radioButton.getText().toString().equals("  Explorer")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else if (radioButton.getText().toString().equals("  Travel Blogger")) {

                        Intent intent = new Intent(getApplicationContext(), BloggerActivity.class);
                        startActivity(intent);
                        /*Intent intent = new Intent(getApplicationContext(),BloggerChatBox.class);
                        intent.putExtra("name",mFirebaseAuth.getCurrentUser());
                        startActivity(intent);*/

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Please select an option.", Toast.LENGTH_SHORT).show();
                }
                // find the radiobutton by returned id


            }}

        });


        //Login
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    onSignedInInitialize(user.getDisplayName());

                } else {
                    // User is signed out
                    onSignedOutInitialize();
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
                }
            }
        };
    }


        /*Button yay = findViewById(R.id.yay);
        yay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent intent = new Intent(getApplicationContext(), ChatBox.class);
                startActivity(intent);*//*
                AuthUI.getInstance().signOut(getApplicationContext());
            }
        });*/










    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Sign in successful!! ", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
        /*mDatabaseReference.orderByKey().equalTo(mFirebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    //Key exists
                    data=dataSnapshot;


                } else {
                    //Key does not exist
                    radioGroup.setVisibility(View.VISIBLE);
                    btnDisplay.setText("Submit");
                    textView = findViewById(R.id.textView4);
                    textView.setText("I am a...");


                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void onSignedInInitialize(String username) {
        mUsername = username;

    }

    private void onSignedOutInitialize() {
        mUsername = ANONYMOUS;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void initialize()
    {

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Users");

    }




}
