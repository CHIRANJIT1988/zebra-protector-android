package zebra.protector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import zebra.protector.adapter.CalendarRecyclerAdapter;
import zebra.protector.model.MyCalendar;


public class SubCategoryDetailsActivity extends AppCompatActivity implements View.OnClickListener
{

    //private RecyclerView mRecyclerView;
    //private RecyclerView.LayoutManager mLayoutManager;
    //private CalendarRecyclerAdapter mAdapter;
    private Button button_book;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Calling the RecyclerView
        //mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //mRecyclerView.setHasFixedSize(true);


        // The number of Columns
        //mLayoutManager = new LinearLayoutManager(SubCategoryDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //mLayoutManager = new GridLayoutManager(this, 2);
        //mRecyclerView.setLayoutManager(mLayoutManager);


        List<MyCalendar> calendarList = new ArrayList<>();

        calendarList.add(new MyCalendar(10, "TODAY", 8, "AUGUST"));
        calendarList.add(new MyCalendar(11, "TOMORROW", 8, "AUGUST"));
        calendarList.add(new MyCalendar(12, "WEDNESDAY", 8, "AUGUST"));
        calendarList.add(new MyCalendar(13, "THURSDAY", 8, "AUGUST"));
        calendarList.add(new MyCalendar(14, "FRIDAY", 8, "AUGUST"));
        calendarList.add(new MyCalendar(15, "SATURDAY", 8, "AUGUST"));
        calendarList.add(new MyCalendar(16, "SUNDAY", 8, "AUGUST"));

        /*mAdapter = new CalendarRecyclerAdapter(this, calendarList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new CalendarRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                //startActivity(new Intent(SubCategoryDetailsActivity.this, SubCategoryDetailsActivity.class));
            }
        });*/


        button_book = (Button) findViewById(R.id.btnBook);
        button_book.setOnClickListener(this);
        //displayStoreList();
    }


    /*private void displayStoreList()
    {

        Store.storeList.clear();

        Store.storeList.add(new Store("Sub Category 1", 1.5, 0, 300, 30));
        Store.storeList.add(new Store("Sub Category 2", 3.5, 0, 300, 30));
        Store.storeList.add(new Store("Sub Category 3", .5, 0, 300, 30));

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        simpleRecyclerAdapter = new SubCategoryRecyclerAdapter(this, Store.storeList);
        recyclerView.setAdapter(simpleRecyclerAdapter);


        simpleRecyclerAdapter.SetOnItemClickListener(new SubCategoryRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {


            }
        });
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case android.R.id.home:
            {
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {

            case R.id.btnBook:

                startActivity(new Intent(SubCategoryDetailsActivity.this, ServiceBookingActivity.class));
                break;
        }
    }
}
