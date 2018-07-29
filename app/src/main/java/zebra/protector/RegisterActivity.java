package zebra.protector;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zebra.protector.model.User;
import zebra.protector.session.SessionManager;

import static zebra.protector.configuration.Configuration.SHARED_PREF;


public class RegisterActivity extends AppCompatActivity
{

    private static final int READ_SMS_PERMISSION_REQUEST_CODE = 1;

    private SharedPreferences prefs = null;
    private SessionManager session; // Session Manager Class
    private int back_pressed = 0;

    public static Activity activity;
    public static boolean is_registration = false;
    public static User user;
    public static String message;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //setSupportActionBar(mToolbar);

        //assert getSupportActionBar() != null;
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.session = new SessionManager(getApplicationContext()); // Session Manager
        this.prefs = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        activity = RegisterActivity.this;


        if (prefs.getBoolean("firstrun", true))
        {
            prefs.edit().putBoolean("firstrun", false).apply();
        }

        /*if(!getIntent().getBooleanExtra("NEW_USER", false))
        {
            redirectFragment(new LoginFragment(), "SIGN IN");
        }

        else
        {
            redirectFragment(new RegisterFragment(), "SIGN UP");
        }*/

        /*if(session.checkLogin())
        {

            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
            this.finish();
        }

        else
        {

        }*/

        permissionCheckerReadSMS();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        // Fixes bug for disappearing fragment content
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupViewPager(ViewPager viewPager)
    {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new LoginFragment(), "SIGN IN");
        adapter.addFrag(new RegisterFragment(), "SIGN UP");

        viewPager.setAdapter(adapter);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }


        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }


        public void addFrag(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }


    /*private void redirectFragment(Fragment fragment, String title)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();

        setTitle(title);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case android.R.id.home:

                if(back_pressed == 0)
                {
                    back_pressed++;
                    Toast.makeText(getApplicationContext(), "Press Back Button again to Exit", Toast.LENGTH_LONG).show();
                }

                else
                {
                    finish();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed()
    {

        if(back_pressed == 0)
        {
            back_pressed++;
            Toast.makeText(getApplicationContext(), "Press Back Button again to Exit", Toast.LENGTH_LONG).show();
        }

        else
        {
            finish();
        }
    }


    private boolean permissionCheckerReadSMS() {

        if (!checkPermissionReadSMS()) {
            requestPermissionReadSMS();
            return false;
        }

        return true;
    }


    private boolean checkPermissionReadSMS()
    {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        else
        {
            return false;
        }
    }


    private void requestPermissionReadSMS(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS))
        {
            makeToast("SMS permission allows us to read or receive SMS. Please allow in App Settings for read or receive SMS.");
        }

        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {

        switch (requestCode)
        {

            case READ_SMS_PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeToast("Permission Granted");
                } else {
                    makeToast("Permission Denied");
                }

                break;
        }
    }


    private void makeToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}