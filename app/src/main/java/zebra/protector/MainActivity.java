package zebra.protector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button button_guest, button_premium;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_guest = (Button) findViewById(R.id.btnGuestUser);
        button_premium = (Button) findViewById(R.id.btnPremiumUser);

        button_guest.setOnClickListener(this);
        button_premium.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {

        Intent intent = new Intent();

        switch (view.getId())
        {

            case R.id.btnGuestUser:

                intent = new Intent(MainActivity.this, DashboardActivity.class);
                break;

            case R.id.btnPremiumUser:

                intent = new Intent(MainActivity.this, RegisterActivity.class);
                break;
        }

        startActivity(intent);
        finish();
    }
}
