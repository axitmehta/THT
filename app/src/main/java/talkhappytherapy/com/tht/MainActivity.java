package talkhappytherapy.com.tht;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

public class MainActivity extends AppCompatActivity {

    private MaterialViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 22) {
            //For changing the statusbar color
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //  window.setStatusBarColor(this.getResources().getColor(R.color.example_color2));
        }
          /*  toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            ActionBar supportActionBar = getSupportActionBar();
            supportActionBar.show();*/


        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        if (mViewPager != null) {
            mViewPager.getPagerTitleStrip().setTextColor(Color.WHITE);
        }
        if (mViewPager != null) {
            mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

                @Override
                public Fragment getItem(int position) {
                    switch (position % 6) {
                        case 0:
                            return RecyclerViewFragment.newInstance();
                        case 1:
                            //return RecyclerViewFragment1.newInstance();
                        case 2:
                            //return RecyclerViewFragment2.newInstance();
                        case 3:
                            //return RecyclerViewFragment3.newInstance();
                        case 4:
                            //return RecyclerViewFragment4.newInstance();
                        case 5:
                            //return RecyclerViewFragment5.newInstance();
                        default:
                            return RecyclerViewFragment.newInstance();
                    }
                }

                @Override
                public int getCount() {
                    return 6;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position % 6) {
                        case 0:
                            return "TALK TO US";
                        case 1:
                            return "UPCOMING EVENTS";
                        case 2:
                            return "TALK TO A HAPPINESS COACH";
                        case 3:
                            return "HAPPINESS HUB";
                        case 4:
                            return "FROM THE FOUNDER";
                        case 5:
                            return "CONTACT US";
                    }
                    return "";
                }
            });
        }

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable( R.color.blue, getResources().getDrawable(R.drawable.min));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable( R.color.red, getResources().getDrawable(R.drawable.min2));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable( R.color.green, getResources().getDrawable(R.drawable.min3));
                    case 3:
                        return HeaderDesign.fromColorResAndDrawable( R.color.lime, getResources().getDrawable(R.drawable.tht1));
                    case 4:
                        //return HeaderDesign.fromColorResAndDrawable( R.color.green_teal, getResources().getDrawable(R.drawable.dbt));
                    case 5:
                        //return HeaderDesign.fromColorResAndDrawable( R.color.cyan, getResources().getDrawable(R.drawable.sundar));
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    //Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(getIntent());
        Toast.makeText(this, "Press and Hold BACK button to exit", Toast.LENGTH_LONG).show();
    }
}

//        // [START config_signin]
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // [END config_signin]
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getToken() instead.
//            String uid = user.getUid();
//
//
//            logout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    FirebaseAuth.getInstance().signOut();
//
//                    mAuth.signOut();
//
//                    // Google sign out
//                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                            new ResultCallback<Status>() {
//                                @Override
//                                public void onResult(@NonNull Status status) {
//
//                                }
//                            });
//
//                    Intent intent = new Intent(MainActivity.this, IntroScreen.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            });
//        }
//
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

