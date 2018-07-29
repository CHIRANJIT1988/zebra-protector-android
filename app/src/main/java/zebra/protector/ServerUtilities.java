package zebra.protector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import zebra.protector.configuration.Configuration;
import zebra.protector.model.User;
import zebra.protector.session.SessionManager;

import static zebra.protector.CommonUtilities.SERVER_URL;
import static zebra.protector.CommonUtilities.TAG;
import static zebra.protector.configuration.Configuration.API_URL;


public final class ServerUtilities 
{

	private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    private static Context context = null;


    public static void register(final Context context, User user, final String regId)
    {

        ServerUtilities.context = context;
        String serverUrl;


    	Log.i(TAG, "registering device (regId = " + regId + ")");


        if(RegisterActivity.is_registration)
        {
            serverUrl = SERVER_URL + "register-user.php";
        }

        else
        {
            serverUrl = SERVER_URL + "login-user.php";
        }

        
    	Map<String, String> params = new HashMap<>();

        try
        {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("reg_id", regId);
            jsonObject.put("name", user.name);
            jsonObject.put("mobile_no", user.phone_number);
            jsonObject.put("password", user.password);
            jsonObject.put("device_id", user.device_id);

            params.put("responseJSON", jsonObject.toString());
            Log.v("params", "" + params);
        }

        catch (JSONException e)
        {

        }

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        
        
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) 
        {
        
        	Log.d(TAG, "Attempt #" + i + " to register");
            
        	try 
        	{
            
        		//displayMessage(context, context.getString(R.string.server_registering, i, MAX_ATTEMPTS));
                post(serverUrl, params);

                Log.v("params", "" + params);

                GCMRegistrar.setRegisteredOnServer(context, true);
                
                //String message = context.getString(R.string.server_registered);
                //CommonUtilities.displayMessage(context, message);

                return;
            } 
        	
        	
        	catch (IOException e) 
        	{
            
        		// Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                
                
                if (i == MAX_ATTEMPTS) 
                {
                    break;
                }
                
                
                try 
                {
                
                	Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } 
                
                catch (InterruptedException e1) 
                {
                
                	// Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    
                    Thread.currentThread().interrupt();
                    
                    return;
                }
                
                
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        
        
        //String message = context.getString(R.string.server_register_error, MAX_ATTEMPTS);
        //CommonUtilities.displayMessage(context, message);
    }

    
    
    /**
     * Unregister this account/device pair within the server.
     */
    public static void unregister(final Context context, final String regId)
    {
    
    	Log.i(TAG, "un registering device (regId = " + regId + ")");
        
    	String serverUrl = API_URL + "register-user.php" + "/unregister";
        Map<String, String> params = new HashMap<>();
        params.put("reg_id", regId);
        
        try 
        {

        	post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
        } 
        
        catch (IOException e) 
        {
        
        	// At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            
        	String message = context.getString(R.string.server_unregister_error, e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }
    }

    
    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params) throws IOException
    {   	
        
        URL url;
    
        try 
        {
            url = new URL(endpoint);
        }
        
        catch (MalformedURLException e)
        {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        
        
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        
        
        // constructs the POST body using the parameters
        while (iterator.hasNext()) 
        {
        
        	Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            
            if (iterator.hasNext()) 
            {
                bodyBuilder.append('&');
            }
        }
        
        
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        
        
        try 
        {
        
        	Log.e("URL", "> " + url);
            
        	conn = (HttpURLConnection) url.openConnection();
            
        	conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            
            // handle the response
            int status = conn.getResponseCode();
            
            if (status != 200) 
            {
                //message = "Server response error. Try again";
                throw new IOException("Post failed with error code " + status);
            }

            else
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                br.close();

                Log.v("response: ", "" + sb.toString());


                try
                {

                    JSONObject jsonObj = new JSONObject(sb.toString());

                    int status_code = jsonObj.getInt("status_code");
                    //message = jsonObj.getString("message");

                    if(status_code == 200)
                    {

                        /*SharedPreferences preferences = context.getSharedPreferences(Configuration.SHARED_PREF, Context.MODE_PRIVATE);
                        preferences.edit().putString("key", Security.decrypt(jsonObj.getString("key"), Configuration.SECRET_KEY)).apply();

                        SessionManager session = new SessionManager(context);


                        String user_id = Security.decrypt(jsonObj.getString("user_id"), Configuration.SECRET_KEY);
                        String name = Security.decrypt(jsonObj.getString("user_name"), Configuration.SECRET_KEY);


                        jsonObj = new JSONObject();
                        jsonObj.put("name", name);
                        jsonObj.put("email", "N/A");
                        jsonObj.put("location", "N/A");
                        jsonObj.put("gender", "N/A");
                        jsonObj.put("dob", "N/A");
                        jsonObj.put("mobile_number", RegisterActivity.user.phone_number);

                        preferences.edit().putString("profile_details", jsonObj.toString()).apply();


                        RegisterActivity.user.setUserId(user_id);
                        RegisterActivity.user.setName(name);
                        session.createLoginSession(RegisterActivity.user);


                        Intent callIntent = new Intent(context, DashboardActivity.class);
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(callIntent);

                        RegisterActivity.activity.finish();*/
                    }
                }

                catch (JSONException e)
                {

                    //message = "Failed to Register. Try Again";
                    e.printStackTrace();
                }
            }
        } 
        
        finally 
        {
        
        	if (conn != null) 
        	{
                conn.disconnect();
            }
        }
    }
}