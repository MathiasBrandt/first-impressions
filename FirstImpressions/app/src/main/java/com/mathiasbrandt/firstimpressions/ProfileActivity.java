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

    private ImageView profilePicture;
    private TextView fullName;
    private TextView age;
    private TextView location;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usersDatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.users_database_reference));

        profilePicture = (ImageView) findViewById(R.id.image_profile_picture);
        fullName = (TextView) findViewById(R.id.textViewFullName);
        age = (TextView) findViewById(R.id.textViewAge);
        location = (TextView) findViewById(R.id.textViewLocation);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        updateUI();
    }

    private void updateUI() {
        setProfileImage();

        // TODO: get data from Firebase database, and insert it into the UI

        fullName.setText(currentUser.getDisplayName());
        age.setText("");
//        location.setText("");

        for(UserInfo info : currentUser.getProviderData()) {
            Log.d(TAG, info.toString());
        }
    }

    private void setProfileImage() {
        Uri photoUrl = Profile.getCurrentProfile().getProfilePictureUri(500, 500);
        Picasso.with(ProfileActivity.this).load(photoUrl).into(profilePicture);
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