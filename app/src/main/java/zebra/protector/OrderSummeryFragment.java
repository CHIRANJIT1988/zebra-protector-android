package zebra.protector;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

import zebra.protector.helper.OnTaskCompleted;
import zebra.protector.model.Address;

/*import educing.tech.customer.alert.CustomAlertDialog;
import educing.tech.customer.db.mysql.SendOrderInfo;
import educing.tech.customer.db.mysql.SyncChatMessage;
import educing.tech.customer.helper.GenerateUniqueId;
import educing.tech.customer.helper.OnTaskCompleted;
import educing.tech.customer.model.Cart;
import educing.tech.customer.model.ChatMessage;
import educing.tech.customer.model.Order;
import educing.tech.customer.model.ShippingAddress;
import educing.tech.customer.model.Store;
import educing.tech.customer.model.User;
import educing.tech.customer.session.SessionManager;
import educing.tech.customer.sqlite.SQLiteDatabaseHelper;

import static zebra.protector.model.Order.delivery_charge_total;
import static zebra.protector.model.Order.discount_total;
import static zebra.protector.model.Order.grand_total;
import static zebra.protector.model.Order.sub_total;*/


public class OrderSummeryFragment extends Fragment implements View.OnClickListener, OnTaskCompleted
{

	private Context context;
	private int color;

	private TextView tvSubTotal;
	private TextView tvDiscount;
	private TextView tvDeliveryCharge;
	private TextView tvTotal;

	private TextView tvName;
	private TextView tvPhoneNumber;
	private TextView tvAddress;

	private Button btnPlaceOrder;
	private Address shipping_address;

	private ProgressDialog pDialog;

	//private SQLiteDatabaseHelper helper;


	public OrderSummeryFragment()
	{

	}


	@SuppressLint("ValidFragment")
	public OrderSummeryFragment(Context context, int color)
	{
		this.color = color;
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{

		View rootView = inflater.inflate(R.layout.fragment_order_summery, container, false);

		findViewById(rootView);
		btnPlaceOrder.setOnClickListener(this);

		displayPaymentDetails();
		displayShippingAddress();

		pDialog = new ProgressDialog(context);
		//helper = new SQLiteDatabaseHelper(context);

		return rootView;
	}
	
	
	/** Called when the activity is about to become visible. */   
	@Override
	public void onStart() {

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


	private void findViewById(View rootView)
	{

		tvSubTotal = (TextView) rootView.findViewById(R.id.sub_total);
		tvDeliveryCharge = (TextView) rootView.findViewById(R.id.delivery_charge);
		tvDiscount = (TextView) rootView.findViewById(R.id.discount);
		tvTotal = (TextView) rootView.findViewById(R.id.total);


		tvName = (TextView) rootView.findViewById(R.id.name);
		tvPhoneNumber = (TextView) rootView.findViewById(R.id.phone_no);
		tvAddress = (TextView) rootView.findViewById(R.id.address);
		btnPlaceOrder = (Button) rootView.findViewById(R.id.btnPlaceOrder);
	}


	private void initProgressDialog()
	{

		pDialog.setMessage("Placing Order ...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}


	private void displayPaymentDetails()
	{

		/*DecimalFormat df = new DecimalFormat("0.00");

		tvSubTotal.setText(df.format(sub_total));
		tvDiscount.setText(df.format(discount_total));
		tvTotal.setText(df.format(grand_total));

		tvDeliveryCharge.setText(String.valueOf(df.format(delivery_charge_total)));*/

	}


	private void displayShippingAddress()
	{

		shipping_address = (Address)this.getArguments().getSerializable("ADDRESS_OBJ");

		StringBuilder sAddress = new StringBuilder().append(shipping_address.getAddress().toUpperCase()).append(", ")
				.append(shipping_address.landmark.toUpperCase()).append(", ").append(shipping_address.city.toUpperCase())
				.append(", ").append(shipping_address.state.toUpperCase()).append(", ").append(shipping_address.pincode);

		tvName.setText(shipping_address.name.toUpperCase());
		tvPhoneNumber.setText(shipping_address.phone_no);
		tvAddress.setText(sAddress);
	}


	public void onClick(View v)
	{

		initProgressDialog();

		onTaskCompleted(true, 200, "ORD79828128");
		//new SendOrderInfo(context, this, Order.orderList, shipping_address.getAddressId()).execute();
	}


	@Override
	public void onTaskCompleted(boolean flag, int code, String message)
	{

		if (pDialog.isShowing())
		{
			pDialog.dismiss();
		}

		ServiceBookingActivity.activity.finish();

		Bundle args = new Bundle();
		args.putSerializable("ORDER_NO", message);

		Fragment fragment = new OrderConfirmationFragment();
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
		fragmentTransaction.replace(R.id.container_body, fragment);
		fragmentTransaction.commit();


		/*try
		{

			if (pDialog.isShowing())
			{
				pDialog.dismiss();
			}

			if(flag && code == 200)
			{

				composeChatMessage();

				*//*ShoppingBagActivity.shopping_bag_activity.finish();
				ProductActivity.product_activity.finish();
				StoresActivity.store_activity.finish();


				Order.orderList.clear();
				Cart.cart.clear();*//*


				Bundle args = new Bundle();
				args.putSerializable("ORDER_NO", message);

				Fragment fragment = new OrderConfirmationFragment();
				fragment.setArguments(args);

				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
				fragmentTransaction.replace(R.id.container_body, fragment);
				fragmentTransaction.commit();

				ShippingAddressActivity.ib_payment_details.setBackgroundResource(R.drawable.ib_order_status_completed);
			}

			else if(!flag && code == 500)
			{
				new CustomAlertDialog(context, OrderSummeryFragment.this).showOKDialog("Fail", message);
			}
		}

		catch (Exception e)
		{

		}*/
	}
}