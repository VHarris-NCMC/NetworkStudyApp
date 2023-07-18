package net.shadyoakdesign.netplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AccountActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;

    private GoogleSignInClient gsc;
    private TextView usernameTextView;
    private TextView emailTextView;
    private Button signOutButton;
    private ImageView userPhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize views
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        signOutButton = findViewById(R.id.signOutButton);
        userPhotoImageView = findViewById(R.id.userPhotoImageView);
        clearUserInfo();

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);


        // Check if user is signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            // User is signed in
            displayUserInfo(account);
            signOutButton.setVisibility(View.VISIBLE);
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });
        } else {
            // User is not signed in
            signOutButton.setVisibility(View.GONE);
            userPhotoImageView.setImageResource(R.drawable.ic_default_photo);

            // Set up sign-in button click listener
            SignInButton googleSignInButton = findViewById(R.id.accountSignInButton);
            googleSignInButton.setVisibility(View.VISIBLE);
            googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent signInIntent = gsc.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            });
        }
    }

    private void clearUserInfo() {
        usernameTextView.setText(R.string.empty_string);
        emailTextView.setText(R.string.empty_string);
    }

    private void displayUserInfo(GoogleSignInAccount account) {
        String username = account.getDisplayName();
        String email = account.getEmail();

        usernameTextView.setText(username);
        emailTextView.setText(email);

        userPhotoImageView.setImageDrawable(new InitialsDrawable( account, this));
    }

    private void signOut() {
        gsc.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Sign out successful, update UI
                        clearUserInfo();
                        userPhotoImageView.setImageResource(R.drawable.ic_default_photo);
                        signOutButton.setVisibility(View.GONE);

                        // Show sign-in button
                        SignInButton googleSignInButton = findViewById(R.id.accountSignInButton);
                        googleSignInButton.setVisibility(View.VISIBLE);
                    }
                });
    }
}
