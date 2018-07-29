package zebra.protector;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

import zebra.protector.model.User;
import zebra.protector.network.InternetConnectionDetector;

import static zebra.protector.CommonUtilities.SENDER_ID;
import static zebra.protector.RegisterActivity.user;

//import com.google.android.gcm.GCMRegistrar;
//import educing.tech.store.ServerUtilities;
//import educing.tech.store.model.Store;
//import educing.tech.store.network.InternetConnectionDetector;
//import static educing.tech.store.activities.RegisterActivity.is_registration;
//import static educing.tech.store.activities.RegisterActivity.message;
//import static educing.tech.store.activities.RegisterActivity.store;


public class LoginFragment extends Fragment implements View.OnClickListener
{

    private TextInputLayout layout_mobile_number, layout_password;
    private Button btnNewPassword, btnSignIn;
    private EditText editPhone, editPassword;
    private TextView tvStatus;
    private ProgressBar pBar;

    private RelativeLayout relative_main;
    private Context context = null;


    // AsyncTask
    private AsyncTask<Void, Void, Void> mRegisterTask;


    public LoginFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        context = this.getActivity();
        RegisterActivity.is_registration = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        findViewById(rootView);
        addClickListener();

        hideKeyboard(rootView);

        return rootView;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }


    @Override
    public void onDetach()
    {
        super.onDetach();
    }


    private void findViewById(View rootView)
    {

        layout_mobile_number = (TextInputLayout) rootView.findViewById(R.id.input_layout_phone_number);
        layout_password = (TextInputLayout)  rootView.findViewById(R.id.input_layout_password);

        btnNewPassword = (Button) rootView.findViewById(R.id.btnNewPassword);
        btnSignIn = (Button) rootView.findViewById(R.id.btnSignIn);

        editPhone = (EditText) rootView.findViewById(R.id.editPhoneNumber);
        editPassword = (EditText) rootView.findViewById(R.id.editPassword);

        pBar = (ProgressBar) rootView.findViewById(R.id.pbLoading);
        tvStatus = (TextView) rootView.findViewById(R.id.status);
        relative_main = (RelativeLayout) rootView.findViewById(R.id.relative_main);
    }


    private void addClickListener()
    {
        btnSignIn.setOnClickListener(this);
        btnNewPassword.setOnClickListener(this);
    }


    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy()
    {

        if (mRegisterTask != null)
        {
            mRegisterTask.cancel(true);
        }


        try
        {
            //context.unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(context);
        }

        catch (Exception e)
        {
            Log.e("UnRegister Error", "> " + e.getMessage());
        }

        super.onDestroy();
        Log.d("Inside : ", "onDestroy() event");
    }


    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {

            case R.id.btnSignIn:

                if(validateMobileNumber() && validatePassword())
                {

                    if (!new InternetConnectionDetector(getActivity()).isConnected())
                    {
                        makeSnackbar("Internet Connection Fail");
                        return;
                    }

                    pBar.setVisibility(View.VISIBLE);
                    tvStatus.setText(String.valueOf("Logging ... "));

                    user = initUserObject();
                    //gcm_registration();

                    if(editPhone.getText().toString().equals("9707930475") && editPassword.getText().toString().equals("123"))
                    {
                        startActivity(new Intent(getActivity(), DashboardActivity.class));
                        getActivity().finish();
                    }

                    else
                    {
                        makeSnackbar("Invalid User Details");
                    }
                }

                break;
        }
    }


    private User initUserObject()
    {

        WifiManager m_wm = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);

        User user = new User();

        user.phone_number = editPhone.getText().toString();
        user.password = editPassword.getText().toString();
        //store.setConfirmationCode(String.valueOf(GenerateUniqueId.getRandomNo(999999, 100000)));
        user.device_id = String.valueOf(m_wm.getConnectionInfo().getMacAddress());

        return user;
    }


    private boolean validateMobileNumber()
    {

        if(editPhone.getText().toString().trim().length() != 10)
        {
            editPhone.setError("Invalid Mobile Number");
            editPhone.requestFocus();
            return false;
        }

        else
        {
            layout_mobile_number.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatePassword()
    {

        if(editPassword.getText().toString().trim().isEmpty())
        {
            editPassword.setError("Enter Password");
            editPassword.requestFocus();
            return false;
        }

        else
        {
            layout_password.setErrorEnabled(false);
        }

        return true;
    }


    private void makeSnackbar(String msg)
    {

        Snackbar snackbar = Snackbar.make(relative_main, msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.myPrimaryColor));
        snackbar.show();
    }


    private void hideKeyboard(final View rootView)
    {

        editPhone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editPhone.getText().toString().trim().length() == 10) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                }
            }
        });
    }


    private void gcm_registration()
    {

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(context);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(context);

        //context.registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(context);

        // Check if regid already presents
        if (regId.equals(""))
        {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(context, SENDER_ID);
        }

        else
        {

            // Try to register again, but not in the UI thread.
            // It's also necessary to cancel the thread onDestroy(),
            // hence the use of AsyncTask instead of a raw thread.
            // final Context context = this;

            mRegisterTask = new AsyncTask<Void, Void, Void>()
            {

                @Override
                protected Void doInBackground(Void... params)
                {
                    // Register on our server
                    // On server creates a new user
                    ServerUtilities.register(context, user, regId);
                    return null;
                }


                @Override
                protected void onPostExecute(Void result)
                {

                    mRegisterTask = null;

                    /*makeSnackbar(message);
                    tvStatus.setText("");
                    pBar.setVisibility(View.GONE);*/
                }

            };

            mRegisterTask.execute(null, null, null);
        }
    }
}