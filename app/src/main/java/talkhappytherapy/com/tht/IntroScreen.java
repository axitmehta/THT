package talkhappytherapy.com.tht;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroScreen extends AppCompatActivity{

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private Button SignUP_Button, Login_Button;
    private Button Anonymous_Button;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        //applyFont(this,findViewById(R.id.coordinatorLayout));

        mAuth = FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT>=23) {

            String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO};
            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        Anonymous_Button = (Button) findViewById(R.id.anonymous);
        SignUP_Button = (Button)findViewById(R.id.singup);
        Login_Button = (Button)findViewById(R.id.login);

        Anonymous_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAnonymously();
            }
        });

        SignUP_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intd= new Intent(IntroScreen.this, SignupActivity.class);
                startActivity(intd);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        });

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intd= new Intent(IntroScreen.this,LoginActivity.class);
                startActivity(intd);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

            }
        });
    }

    private void signInAnonymously() {
        dialog = ProgressDialog.show(this, "", "Logging in. Please wait...", true);
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intd= new Intent(IntroScreen.this,MainActivity.class);
                            startActivity(intd);
                            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInAnonymously:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInAnonymously:failure", task.getException());
                            Toast.makeText(IntroScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        dialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }

    public static boolean hasPermissions(IntroScreen context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }
}
