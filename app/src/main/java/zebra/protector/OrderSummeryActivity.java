package zebra.protector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zebra.protector.model.Address;

public class OrderSummeryActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summery);

        Bundle args = new Bundle();
        args.putSerializable("ADDRESS_OBJ", getIntent().getSerializableExtra("ADDRESS_OBJ"));


        Fragment fragment = new OrderSummeryFragment(this, R.color.home_background);
        fragment.setArguments(args);

        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }
}
