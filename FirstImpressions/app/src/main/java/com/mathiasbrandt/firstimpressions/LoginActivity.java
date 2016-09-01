package com.mathiasbrandt.firstimpressions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    public static final int PROFILE_PICTURE_SIZE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());

        // check if session exists
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            // session exists
            goToProfileActivity();
        } else {
            // user is not signed in. Start FirebaseUI sign in flow
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
                saveFacebookData();
                goToProfileActivity();
            } else {
                // user sign in failed
                Log.d(TAG, "User sign in failed");
            }
        }
    }

    private void saveFacebookData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference userNode = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userData, GraphResponse response) {
                            Gson gson = new Gson();
                            User user = gson.fromJson(userData.toString(), User.class);
                            user.setPhotoUrl(Profile.getCurrentProfile().getProfilePictureUri(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE).toString());
                            userNode.setValue(user);
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,birthday,location,name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void goToProfileActivity() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }
}
