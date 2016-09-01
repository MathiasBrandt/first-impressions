package com.mathiasbrandt.firstimpressions;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mathiasbrandt.firstimpressions.facebookobjects.User;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "ProfileActivity";

    private FirebaseUser currentUser;

    private ImageView imageViewProfilePicture;
    private TextView textViewFullName;
    private TextView textViewAge;
    private TextView textViewLocation;
    private ProgressBar progressBarProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewProfilePicture = (ImageView) findViewById(R.id.image_profile_picture);
        textViewFullName = (TextView) findViewById(R.id.textViewFullName);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
        progressBarProfile = (ProgressBar) findViewById(R.id.progressBarProfile);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        updateUI();
    }

    private void updateUI() {
        // get a database reference to the current user
        DatabaseReference userNode = FirebaseDatabase.getInstance().getReference(getString(R.string.db_key_users)).child(currentUser.getUid());

        userNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                Picasso.with(ProfileActivity.this).load(user.getPhotoUrl()).into(imageViewProfilePicture);
                textViewAge.setText(user.getBirthMonth());
                textViewFullName.setText(user.getName());
                textViewLocation.setText(user.getLocation().getName());

                progressBarProfile.setVisibility(View.GONE);
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