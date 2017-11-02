package com.example.mac.lacus;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.mac.lacus.R.layout.activity_main;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    EditText email, password;
    Button passwordForgot, needAccount;

    ScrollView activity_main;

    SignInButton googleLogin;

    private static final int RC_SIGN_IN = 1;
    private String TAG;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authL;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //SET FULLSCREEN
        setContentView(R.layout.activity_main);

        activity_main = (ScrollView) findViewById(R.id.activity_main);


        //View
        authL = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("user", firebaseAuth.getCurrentUser().toString());
                }
            }
        };
        googleLogin = (SignInButton) findViewById(R.id.conectarConGoogle);
        login = (Button) findViewById(R.id.ingresar);
        email = (EditText) findViewById(R.id.input_signin_email);
        password = (EditText) findViewById(R.id.input_signin_password);
        passwordForgot = (Button) findViewById(R.id.forgotpassword);
        needAccount = (Button) findViewById(R.id.needaccount);


        login.setOnClickListener(this);
        passwordForgot.setOnClickListener(this);
        needAccount.setOnClickListener(this);
        //Init Firebase Auth
        auth = FirebaseAuth.getInstance();



        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signIn();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("141062154051-97jvqv0jl8cso8gvsmvrqpfrug1u38q9.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Check already session, if ok -> Dashboard
         if(auth.getCurrentUser() !=null){
           startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authL);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }


                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgotpassword) {
            startActivity(new Intent(this, ForgotPassword.class));
            finish();
        } else if (v.getId() == R.id.needaccount) {
            startActivity(new Intent(this, Sign_up.class));
            finish();
        } else if (v.getId() == R.id.ingresar) {
            loginIser(email.getText().toString(), password.getText().toString());
        }
    }

    private void loginIser(String emailL, final String passwordL) {

        if (email.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "El usuario no existe o la contraseña no es válida!", Toast.LENGTH_LONG).show();

        } else {
            auth.signInWithEmailAndPassword(emailL, passwordL)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                email.setText("");
                                password.setText("");
                                email.getSelectionStart();
                                Toast.makeText(MainActivity.this, "El usuario no existe o la contraseña no es válida!", Toast.LENGTH_LONG).show();

                            } else {
                                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }
}
