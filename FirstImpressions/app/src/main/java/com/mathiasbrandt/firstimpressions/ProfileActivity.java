package com.mathiasbrandt.firstimpressions;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "ProfileActivity";

    private DatabaseReference usersDatabase;
    private FirebaseUser currentUser;

    private ImageView imageViewProfilePicture;
    private TextView textViewFullName;
    private TextView textViewAge;
    private TextView textViewLocation;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usersDatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.users_database_reference));

        imageViewProfilePicture = (ImageView) findViewById(R.id.image_profile_picture);
        textViewFullName = (TextView) findViewById(R.id.textViewFullName);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        updateUI();
    }

    private void updateUI() {
        DatabaseReference userNode = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
        userNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String photoUrl = dataSnapshot.child(getString(R.string.db_key_photo_url)).getValue(String.class);
                String age = dataSnapshot.child(getString(R.string.db_key_age)).getValue(String.class);
                String name = dataSnapshot.child(getString(R.string.db_key_name)).getValue(String.class);
                String location = dataSnapshot.child(getString(R.string.db_key_location)).getValue(String.class);

                Picasso.with(ProfileActivity.this).load(photoUrl).into(imageViewProfilePicture);
                textViewAge.setText(age);
                textViewFullName.setText(name);
                textViewLocation.setText(location);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Could not access user node in database");
            }
        });
    }

    public void buttonLogoutClick(View v) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}