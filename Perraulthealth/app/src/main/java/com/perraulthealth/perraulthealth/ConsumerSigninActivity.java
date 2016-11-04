package com.perraulthealth.perraulthealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class ConsumerSigninActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkboxSignedin;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private ProgressDialog progressDialog;
    private  String TAG ="ConsumerSigninActivity" ;
    private String email;
    private String password;
    private boolean checkbox;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_signin);






        findViewById(R.id.buttonSignin).setOnClickListener(this);
        findViewById(R.id.textViewSignup).setOnClickListener(this);


            editTextEmail = (EditText) findViewById(R.id.editTextEmail);



            editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            password = editTextPassword.getText().toString().trim();


            checkboxSignedin = (CheckBox) findViewById(R.id.checkboxSignedin);

        System.out.println("checkbox" + checkbox);







        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    finish();
                    startActivity(new Intent(getApplicationContext(),ConsumerMapsActivity.class ));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    //TODO:Tarun

                }
                // ...
            }
        };

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onClick (View v)
    {
        int i = v.getId();

        if(i ==R.id.buttonSignin) {


            email = editTextEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
                return;
            }
            password = editTextPassword.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
                return;
            }
            checkbox = checkboxSignedin.isChecked();
            signIn();


        }

        if(i==R.id.textViewSignup)
        {
            finish();
            startActivity(new Intent(this,ConsumerSignupActivity.class ));
        }

    }

    private void signIn() {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

       progressDialog.setMessage("Signing in User...");
        progressDialog.show();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            saveSigninData( );
                            finish();
                            progressDialog.dismiss();
                            //otp auth
                            startActivity(new Intent(getApplicationContext(), ConsumerMapsActivity.class));
                        }


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(ConsumerSigninActivity.this, "auth_failed",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ConsumerSignupActivity.class));
                        }

                        // [START_EXCLUDE]


                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
        saveSigninData( );
    }




    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }


        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }


        return valid;
    }


    private void saveSigninData() {

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();


            String uid = user.getUid();

        Log.d(TAG, "saveSigninData "+ mRootRef);
        Log.d(TAG, "saveSigninData "+ user);
        Log.d(TAG, "saveSigninData "+ FirebaseDatabase.getInstance());
        Log.d(TAG, "saveSigninData "+ uid);


                mRootRef.child("Consumer").child(uid).child("checkbox").setValue(checkbox);
        }

    }

/*
    private void findEmailforUsername() {


        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        System.out.println(firebaseAuth.getInstance());
        System.out.println("user" + user);
        String uid = null;
        if (user != null) {
            // Name, email address, and profile photo Url
            //String name = user.getDisplayName();
            // String email = user.getEmail();
            // Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            //String uid;
            uid = user.getUid();
        }
        System.out.println("uid"+ uid);
        System.out.println(mobile_or_email);
        email = mobile_or_email;
        System.out.println(email);

         mRootRef.child("Consumer").child(uid).child("mobile").(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        Query queryRef = temp.equalTo(mobile_or_email);


    }

*/

