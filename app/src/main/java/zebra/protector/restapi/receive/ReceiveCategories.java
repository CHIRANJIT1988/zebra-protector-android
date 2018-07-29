package zebra.protector.restapi.receive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import zebra.protector.app.MyApplication;
import zebra.protector.helper.OnTaskCompleted;
import zebra.protector.model.Category;
import zebra.protector.model.SubCategory;

import static zebra.protector.configuration.Configuration.API_URL;


public class ReceiveCategories
{

	private OnTaskCompleted listener;

	private String URL = "";

	private Context context;

	private static final int MAX_ATTEMPTS = 10;
	private int ATTEMPTS_COUNT;


	public ReceiveCategories(Context context , OnTaskCompleted listener)
	{

		this.listener = listener;
		this.context = context;

		this.URL = API_URL + "receive-test.php";
	}


	private void execute()
	{

		StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

			@Override
			public void onResponse(String response)
			{

				try
				{

					Log.v("response: ", response);

					JSONArray arr = new JSONArray(response);

					Category.categoryList.clear();

					if(arr.length() > 0)
					{

						for (int i = 0; i < arr.length(); i++)
						{

							JSONObject jsonObj = (JSONObject) arr.get(i);

							String category_id = jsonObj.getString("category_id");
							String category_name = jsonObj.getString("category_name");
							String description = jsonObj.getString("description");
							String image = jsonObj.getString("image");
							int is_guest_allowed = jsonObj.getInt("is_guest_allowed");


							JSONArray sub_category_array = new JSONArray(jsonObj.getString("sub_category_details"));

							List<SubCategory> subCategoryList = new ArrayList<>();

							for (int j = 0; j < sub_category_array.length(); j++)
							{

								jsonObj = (JSONObject) sub_category_array.get(j);

								String sub_category_id = jsonObj.getString("sub_category_id");
								String sub_category_name = jsonObj.getString("sub_category_name");

								subCategoryList.add(new SubCategory(sub_category_id, sub_category_name));

								//Log.v("my_sub_category_details", option);
							}

							Category.categoryList.add(new Category(category_id, category_name, description, image, is_guest_allowed, subCategoryList));
						}

						listener.onTaskCompleted(true, 200, "success");
					}
				}

				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error)
			{

				Log.v("Product List: ", "" + error);

				if(ATTEMPTS_COUNT != MAX_ATTEMPTS)
				{

					execute();

					ATTEMPTS_COUNT ++;

					Log.v("#Attempt No: ", "" + ATTEMPTS_COUNT);
					return;
				}

				listener.onTaskCompleted(false, 500, "Internet Connection Failure"); // Invalid User

			}
		});

		// Adding request to request queue
		MyApplication.getInstance().addToRequestQueue(postRequest);
	}
}