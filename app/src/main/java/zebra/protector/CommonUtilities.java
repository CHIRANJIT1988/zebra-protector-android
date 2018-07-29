package zebra.protector;

import android.content.Context;
import android.content.Intent;

import static zebra.protector.configuration.Configuration.API_URL;


public final class CommonUtilities
{
	
	// give your server registration url here
    public static final String SERVER_URL = API_URL;

    // Google project id
    public static final String SENDER_ID = "840877988809";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "Zebra Application";

    public static final String DISPLAY_MESSAGE_ACTION =
            "zebra.protector.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */


    public static void displayMessage(Context context, String message)
    {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}