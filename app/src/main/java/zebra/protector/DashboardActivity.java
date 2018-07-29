package zebra.protector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Transformation;

import java.util.Timer;
import java.util.TimerTask;

import zebra.protector.adapter.HomeRecyclerAdapter;
import zebra.protector.helper.Blur;
import zebra.protector.helper.OnAdvertisementLoad;
import zebra.protector.model.Product;

import static zebra.protector.configuration.Configuration.ADVERTISEMENT_URL;
import static zebra.protector.model.Advertisement.advertisementList;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnAdvertisementLoad
{

    private RecyclerView recyclerView;
    private HomeRecyclerAdapter simpleRecyclerAdapter;
    private ViewPager viewPager;
    private int currentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide1);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {

                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

                int mutedColor = palette.getMutedColor(ContextCompat.getColor(getApplicationContext(), R.color.myPrimaryColor));
                collapsingToolbar.setContentScrimColor(mutedColor);
            }
        });*/


        viewPager = (ViewPager) findViewById(R.id.pager);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Product.productCategoryList.add(new Product(1, "Electrician", ""));
        Product.productCategoryList.add(new Product(1, "Plumber", ""));
        Product.productCategoryList.add(new Product(1, "Carpenter", ""));
        Product.productCategoryList.add(new Product(1, "Appliance", ""));
        Product.productCategoryList.add(new Product(1, "Pest Control", ""));
        Product.productCategoryList.add(new Product(1, "Refrigerator", ""));
        Product.productCategoryList.add(new Product(1, "Car Wash", ""));
        Product.productCategoryList.add(new Product(1, "Cleaning", ""));
        Product.productCategoryList.add(new Product(1, "House Security", ""));
        Product.productCategoryList.add(new Product(1, "Packers Motors", ""));
        Product.productCategoryList.add(new Product(1, "Housekeeping", ""));


        if (simpleRecyclerAdapter == null)
        {
            simpleRecyclerAdapter = new HomeRecyclerAdapter(this, Product.productCategoryList);
            recyclerView.setAdapter(simpleRecyclerAdapter);
        }


        simpleRecyclerAdapter.SetOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position)
            {

                Intent intent = new Intent(DashboardActivity.this, SubCategoryActivity.class);
                intent.putExtra("CATEGORY", 1);
                startActivity(intent);

                //startActivity(new Intent(DashboardActivity.this, ServiceLocationActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed()
    {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class ImageFragmentPagerAdapter extends FragmentPagerAdapter {

        public ImageFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return advertisementList.size();
        }


        @Override
        public Fragment getItem(int position) {
            return SwipeFragment.newInstance(position);
        }
    }


    public static class SwipeFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
            final ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
            Bundle bundle = getArguments();

            final int position = bundle.getInt("position");
            final String imageFileName = advertisementList.get(position).file_name;

            Transformation blurTransformation = new Transformation() {

                @Override
                public Bitmap transform(Bitmap source)
                {
                    Bitmap blurred = Blur.fastblur(getActivity(), source, 10);
                    source.recycle();
                    return blurred;
                }

                @Override
                public String key()
                {
                    return "blur()";
                }
            };


            Picasso.with(getActivity())
                    .load(ADVERTISEMENT_URL + imageFileName) // thumbnail url goes here
                    .transform(blurTransformation)
                    .into(imageView, new Callback() {

                        @Override
                        public void onSuccess()
                        {

                            Picasso.with(getActivity())
                                    .load(ADVERTISEMENT_URL + imageFileName) // image url goes here
                                    .into(imageView);
                        }

                        @Override
                        public void onError()
                        {

                        }
                    });


            return swipeView;
        }


        static SwipeFragment newInstance(int position) {

            SwipeFragment swipeFragment = new SwipeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            swipeFragment.setArguments(bundle);

            return swipeFragment;
        }
    }


    private void autoSlideImages() {

        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {

            public void run() {

                if (currentPage == advertisementList.size()) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();

        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 3000);
    }


    @Override
    public void onAdvertisementLoad(boolean flag, int code, String message)
    {

        if(code == 200)
        {

            // Code for image swipe slider
            ImageFragmentPagerAdapter imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager());
            viewPager.setOffscreenPageLimit(5);
            viewPager.setAdapter(imageFragmentPagerAdapter);

            autoSlideImages();
        }
    }
}