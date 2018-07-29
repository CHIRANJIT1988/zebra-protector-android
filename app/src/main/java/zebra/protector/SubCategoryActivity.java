package zebra.protector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import zebra.protector.adapter.SubCategoryRecyclerAdapter;
import zebra.protector.model.Store;

public class SubCategoryActivity extends AppCompatActivity implements View.OnClickListener
{

    private RecyclerView recyclerView;
    private SubCategoryRecyclerAdapter simpleRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        displayStoreList();
    }


    private void displayStoreList()
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

                startActivity(new Intent(SubCategoryActivity.this, SubCategoryDetailsActivity.class));
            }
        });
    }


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

    }
}
