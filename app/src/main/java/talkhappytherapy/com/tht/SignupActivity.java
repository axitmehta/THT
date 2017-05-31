package talkhappytherapy.com.tht;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignupActivity extends AppCompatActivity{

    EditText firstName;
    EditText lastName;
    EditText emailID;
    EditText password;
    EditText confirmPassword;

    TextView support;

    Button loginButton;
    private ProgressDialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();


        firstName = (EditText) findViewById(R.id.SignUp_EditTextFirstName);
        lastName = (EditText) findViewById(R.id.SignUp_EditTextLastName);
        emailID = (EditText) findViewById(R.id.SignUp_EditTextEmailAddress);
        password = (EditText) findViewById(R.id.SignUp_EditTextPassword);
        password.setTypeface(Typeface.DEFAULT);
        confirmPassword = (EditText) findViewById(R.id.SignUp_EditTextConformPassword);
        confirmPassword.setTypeface(Typeface.DEFAULT);

        support = (TextView) findViewById(R.id.SignUp_TextSupport);

        loginButton = (Button) findViewById(R.id.SignUp_ButtonSignUp);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstName.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "All fields are compulsory", Toast.LENGTH_LONG).show();
                }
                else if (lastName.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields are compulsory", Toast.LENGTH_LONG).show();
                }
                else if (emailID.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields are compulsory", Toast.LENGTH_LONG).show();
                }
                else if (password.getText().toString().isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields are compulsory", Toast.LENGTH_LONG).show();
                }
                else if(password.getText().length()<6) {
                    Toast.makeText(SignupActivity.this, "Password must be atleast 6 characters long", Toast.LENGTH_LONG).show();
                }
                else if(!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Password Field And Confirm Password Field should be same.", Toast.LENGTH_LONG).show();
                }
                else {
                    String firstname = firstName.getText().toString();
                    String lastname = lastName.getText().toString();
                    String email = emailID.getText().toString();
                    String password = confirmPassword.getText().toString();
                    newlogin(firstname+" "+lastname,email,password);
                }
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }

        });
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"talkhappytherapy@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SignupActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    void newlogin(final String name, final String email, final String password)
    {
        dialog = ProgressDialog.show(this, "", "Signing up. Please wait...", true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            if (user != null) {
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "User profile updated.");
                                                }
                                            }
                                        });
                            }

                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "Email sent.");
                                                }
                                            }
                                        });
                            }

                            dialog.dismiss();

                            alertbox();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(SignupActivity.this, "Sign-up Failed. Please try again!",
                                        Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            dialog.dismiss();
                            alertbox1(task.getException().getMessage());
                        }
                    }
                });
    }

    private void alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("One step remaining...")
                .setMessage("Please verify your Email Id and then Login.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(SignupActivity.this, IntroScreen.class);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void alertbox1(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Uh.. Oh!")
                .setMessage(result)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, IntroScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}