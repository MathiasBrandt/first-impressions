package com.mathiasbrandt.firstimpressions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check if session exists
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            // session exists
            saveFacebookId(); // TODO: delete this
            goToProfileActivity();
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(AuthUI.FACEBOOK_PROVIDER)
                            .build(),
                    RC_SIGN_IN
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            if(resultCode == RESULT_OK) {
                // user signed in
                saveFacebookId();
                goToProfileActivity();
            } else {
                // user sign in failed
            }
        }
    }

    private void saveFacebookId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
        String facebookId = "";

        for(UserInfo profile : user.getProviderData()) {
            if(profile.getProviderId().equals(getString(R.string.facebook_provider_id))) {
                facebookId = profile.getUid();
            }
        }

        usersDatabase.child(user.getUid()).child(getString(R.string.facebook_user_id)).setValue(facebookId);
    }

    private void goToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }
}
