package zebra.protector;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import zebra.protector.network.InternetConnectionDetector;

import static zebra.protector.configuration.Configuration.PLACES_API_BASE;
import static zebra.protector.configuration.Configuration.TYPE_AUTOCOMPLETE;
import static zebra.protector.configuration.Configuration.OUT_JSON;
import static zebra.protector.configuration.Configuration.API_KEY;


public class ServiceBookingActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    private int back_pressed = 0;
    private GoogleMap mMap;
    public static ServiceBookingActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);

        ImageButton ib_back = (ImageButton) findViewById(R.id.ib_back);
        AutoCompleteTextView edit_search = (AutoCompleteTextView) findViewById(R.id.edit_search);

        edit_search.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item_auto_complete));
        edit_search.setOnItemClickListener(this);

        ib_back.setOnClickListener(this);
        redirectAddressFragment();

        if (!isGooglePlayServicesAvailable())
        {
            finish();
        }

        setUpMap(0, 0);

        activity = ServiceBookingActivity.this;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {

        String str = (String) adapterView.getItemAtPosition(position);

        if(new InternetConnectionDetector(this).isConnected())
        {
            Intent intent = new Intent(ServiceBookingActivity.this, ServiceLocationActivity.class);
            intent.putExtra("PLACE", str);
            startActivityForResult(intent, 1);
        }

        else
        {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_LONG).show();
        }
    }


    private void redirectAddressFragment()
    {

        Fragment fragment = new AddressFragment(this, R.color.home_background);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View view)
    {

    }


    @Override
    public void onBackPressed()
    {

        if(back_pressed == 0)
        {
            back_pressed++;
            Toast.makeText(getApplicationContext(), "Press Back Button again to Back", Toast.LENGTH_LONG).show();
        }

        else
        {
            finish();
        }
    }


    public static ArrayList<String> autocomplete(String input)
    {

        ArrayList<String> resultList = new ArrayList<>();

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();

        try
        {

            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];

            while ((read = in.read(buff)) != -1)
            {
                jsonResults.append(buff, 0, read);
            }
        }

        catch (MalformedURLException e)
        {
            Log.e("Google Place Error: ", "Error processing Places API URL", e);
            return resultList;
        }

        catch (IOException e)
        {
            Log.e("Google Place Error: ", "Error connecting to Places API", e);
            return resultList;
        }

        finally
        {

            if (conn != null)
            {
                conn.disconnect();
            }
        }

        try
        {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<>(predsJsonArray.length());

            for (int i = 0; i < predsJsonArray.length(); i++)
            {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        }

        catch (JSONException e)
        {
            Log.e("Google Place Error: ", "Cannot process JSON results", e);
        }

        return resultList;
    }


    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable
    {

        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId)
        {
            super(context, textViewResourceId);
        }


        @Override
        public int getCount() {
            return resultList.size();
        }


        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }


        @Override
        public Filter getFilter()
        {

            Filter filter = new Filter()
            {

                @Override
                protected FilterResults performFiltering(CharSequence constraint)
                {

                    FilterResults filterResults = new FilterResults();

                    if (constraint != null)
                    {

                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }

                    return filterResults;
                }


                @Override
                protected void publishResults(CharSequence constraint, FilterResults results)
                {

                    if (results != null && results.count > 0)
                    {
                        notifyDataSetChanged();
                    }

                    else
                    {
                        notifyDataSetInvalidated();
                    }
                }
            };

            return filter;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 1)
        {

            double latitude = data.getDoubleExtra("LATITUDE", 0);
            double longitude = data.getDoubleExtra("LONGITUDE", 0);

            //address.setLatitude(latitude);
            //address.setLongitude(longitude);

            LatLng position = new LatLng(latitude, longitude);
            MarkerOptions options = new MarkerOptions();
            options.position(position);

            mMap.clear();
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }


    private boolean isGooglePlayServicesAvailable()
    {

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (ConnectionResult.SUCCESS == status)
        {
            return true;
        }

        else
        {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    private void setUpMap(double latitude, double longitude)
    {

        try
        {

            if (mMap == null)
            {

                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
                mMap.setMyLocationEnabled(false);

                LatLng position = new LatLng(latitude, longitude);
                MarkerOptions options = new MarkerOptions();
                options.position(position);

                if (mMap != null)
                {

                    mMap.addMarker(options);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

                            marker.showInfoWindow(); // show marker info window on marker click
                            return true;
                        }
                    });


                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                        @Override
                        public void onMapClick(LatLng point)
                        {
                            /*if(permissionCheckerGPS())
                            {
                                displayMap();
                            }*/
                        }
                    });
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
                }
            }
        }

        catch (Exception e)
        {

        }
    }
}