package zebra.protector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import zebra.protector.configuration.Configuration;
import zebra.protector.helper.Helper;
import zebra.protector.helper.OnTaskCompleted;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        OnTaskCompleted/*, AddSchoolFragment.AddSchoolFragmentListener, NewPasswordFragment.AddSchoolFragmentListener*/
{

    private Button button_new_password, button_edit_school, button_edit_profile;
    private TextView tv_mobile_number, tv_student_name, tv_student_email, tv_student_location, tv_student_gender, tv_student_dob, tv_school_name, tv_school_location, tv_progress;
    private ProgressBar profile_progress;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("My Profile");

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById();
        setListener();

        this.preferences = getSharedPreferences(Configuration.SHARED_PREF, Context.MODE_PRIVATE);
    }


    private void setListener()
    {
        button_edit_school.setOnClickListener(this);
        button_edit_profile.setOnClickListener(this);
        button_new_password.setOnClickListener(this);
    }


    private void findViewById()
    {

        //button_edit_school = (Button) findViewById(R.id.button_edit_school);
        button_edit_profile = (Button) findViewById(R.id.button_edit_profile);
        button_new_password = (Button) findViewById(R.id.button_new_password);

        tv_mobile_number = (TextView) findViewById(R.id.mobile_number);
        tv_student_name = (TextView) findViewById(R.id.student_name);
        tv_student_email = (TextView) findViewById(R.id.student_email);
        tv_student_location = (TextView) findViewById(R.id.student_location);
        tv_student_gender = (TextView) findViewById(R.id.student_gender);
        tv_student_dob = (TextView) findViewById(R.id.student_dob);

        //tv_school_name = (TextView) findViewById(R.id.school_name);
        //tv_school_location = (TextView) findViewById(R.id.school_location);

        tv_progress = (TextView) findViewById(R.id.tv_progress);
        profile_progress = (ProgressBar) findViewById(R.id.profile_progress);
    }


    @Override
    public void onResume()
    {

        super.onResume();
        setProfileData(preferences.getString("profile_details", ""), preferences.getString("school_details", ""));
    }


    @Override
    public void onClick(View view)
    {

        /*switch (view.getId())
        {

            case R.id.button_new_password:

                FragmentManager fm = getSupportFragmentManager();

                NewPasswordFragment dialogFragment = new NewPasswordFragment();
                dialogFragment.setListener(ProfileActivity.this);
                dialogFragment.setRetainInstance(true);
                dialogFragment.show(fm, "NewPasswordFragment");

                break;

            case R.id.button_edit_school:

                FragmentManager fm1 = getSupportFragmentManager();

                AddSchoolFragment dialogFragment1 = new AddSchoolFragment(getApplicationContext());
                dialogFragment1.setListener(ProfileActivity.this);
                dialogFragment1.setRetainInstance(true);
                dialogFragment1.show(fm1, "AddSchoolFragment");

                break;

            case R.id.button_edit_profile:

                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                break;

        }*/
    }


    /*@Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog, School school)
    {

        JSONObject jsonObject = new JSONObject();

        try
        {

            jsonObject.put("school_name", school.school_name);
            jsonObject.put("state", school.state);

            this.preferences.edit().putString("school_details", jsonObject.toString()).apply();
            new UpdateSchoolDetails(getApplicationContext(), this).update(jsonObject.toString(), "1");
        }

        catch (JSONException e)
        {

        }
    }*/


    /*@Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog, String new_password)
    {
        Toast.makeText(getApplicationContext(), new_password, Toast.LENGTH_SHORT).show();
    }*/


   /* @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog)
    {
        // Do nothing
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case android.R.id.home:
            {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void setProfileData(String profile_json_data, String school_json_data)
    {

        try
        {

            JSONObject jsonObj = new JSONObject(profile_json_data);

            String name = jsonObj.getString("name");
            String email = jsonObj.getString("email");
            String location = jsonObj.getString("location");
            String dob = jsonObj.getString("dob");
            String gender = jsonObj.getString("gender");
            String mobile_number = jsonObj.getString("mobile_number");

            tv_student_name.setText(Helper.toCamelCase(name));
            tv_student_email.setText(email);
            tv_student_location.setText(Helper.toCamelCase(location));
            tv_student_gender.setText(Helper.toCamelCase(gender));
            tv_student_dob.setText(dob);
            tv_mobile_number.setText(String.valueOf("+91-" + mobile_number));

            jsonObj = new JSONObject(school_json_data);

            String school_name = jsonObj.getString("school_name");
            String school_location = jsonObj.getString("state");

            tv_school_name.setText(school_name.toUpperCase());
            tv_school_location.setText(Helper.toCamelCase(school_location));

            //User userObj = new User(name, email, location, dob, gender);
            //School schoolObj = new School(school_name, school_location);

            //profile_progress(userObj, schoolObj);
        }

        catch (JSONException e)
        {

        }
    }


    /*private void profile_progress(User userObj, School schoolObj)
    {

        int progress = 0;

        if(!userObj.getName().trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!userObj.getEmail().trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!userObj.getLocation().trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!userObj.getDateOfBirth().trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!userObj.getGender().trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!schoolObj.school_name.trim().isEmpty())
        {
            progress += (100/7);
        }

        if(!schoolObj.state.trim().isEmpty())
        {
            progress += (100/7);
        }

        profile_progress.setProgress(progress);
        tv_progress.setText(String.valueOf(progress + "%"));
    }*/


    @Override
    public void onTaskCompleted(boolean flag, int code, String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}