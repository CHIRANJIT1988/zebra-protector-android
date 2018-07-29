package zebra.protector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;

import zebra.protector.adapter.AddressRecyclerAdapter;
import zebra.protector.helper.OnTaskCompleted;
import zebra.protector.model.Address;
import zebra.protector.network.InternetConnectionDetector;


public class AddressFragment extends Fragment implements OnTaskCompleted
{

    AddressRecyclerAdapter adapter;
    Context context;
    private int color;

    private ProgressBar pbLoading;
    private RecyclerView recyclerView;


    public AddressFragment()
    {

    }


    @SuppressLint("ValidFragment")
    public AddressFragment(Context context, int color)
    {
        this.color = color;
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_address, container, false);

        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        /*if (!new InternetConnectionDetector(context).isConnected())
        {
            pbLoading.setVisibility(View.GONE);
            new CustomAlertDialog(context, this).showOKDialog("Network Error", "Internet Connection Failure", "network");
            return view;
        }*/


        /*if(Address.addressList.size() == 0)
        {
            //new ReceiveShippingAddress(context, this).fetchAddress(getUserDetails().getUserID());
            onTaskCompleted(true, 200, "cndjcj");
        }

        else
        {
            pbLoading.setVisibility(View.GONE);
        }*/

        Address sAddress = new Address(1, 1, "Chiranjit Bardhan", "781008", "9707930475", "Christian Hospital", "KC Choudhury Road, Chatribari", "Guwahati", "Assam", "India");
        Address.addressList.add(sAddress);

        adapter = new AddressRecyclerAdapter(context, AddressFragment.this);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new AddressRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getActivity(), OrderSummeryActivity.class);
                intent.putExtra("ADDRESS_OBJ", Address.addressList.get(position-1));
                startActivity(intent);
            }
        });

        return view;
    }


    /** Called when the activity is about to become visible. */
    @Override
    public void onStart()
    {

        super.onStart();
        Log.d("Inside : ", "onCreate() event");
    }



    /** Called when the activity has become visible. */
    @Override
    public void onResume()
    {

        super.onResume();
        Log.d("Inside : ", "onResume() event");
    }



    /** Called when another activity is taking focus. */
    @Override
    public void onPause()
    {

        super.onPause();
        Log.d("Inside : ", "onPause() event");
    }



    /** Called when the activity is no longer visible. */
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d("Inside : ", "onStop() event");
    }



    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("Inside : ", "onDestroy() event");
    }


    @Override
    public void onTaskCompleted(boolean flag, int code, String message)
    {

        try
        {

            pbLoading.setVisibility(View.GONE);

            if (flag && code == 200)
            {
                adapter.notifyDataSetChanged();
                return;
            }

            if (flag && code == 300)
            {

                Fragment fragment = new NewAddressFragment(context, R.color.home_background);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

                return;
            }

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

        catch (Exception e)
        {

        }
    }


    /*private User getUserDetails()
    {

        SessionManager session = new SessionManager(context);

        User userObj = new User();

        if (session.checkLogin())
        {
            HashMap<String, String> user = session.getUserDetails();

            userObj.setUserID(Integer.valueOf(user.get(SessionManager.KEY_USER_ID)));
            userObj.setPhoneNo(user.get(SessionManager.KEY_PHONE));
            userObj.setUserName(user.get(SessionManager.KEY_USER_NAME));
        }

        return userObj;
    }*/
}