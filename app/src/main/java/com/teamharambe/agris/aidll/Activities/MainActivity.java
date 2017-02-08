package com.teamharambe.agris.aidll.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teamharambe.agris.aidll.Fragments.ListItemsFragment;
import com.teamharambe.agris.aidll.Models.Car;
import com.teamharambe.agris.aidll.Models.Cars;
import com.teamharambe.agris.aidll.Models.Categories;
import com.teamharambe.agris.aidll.Models.Category;
import com.teamharambe.agris.aidll.R;
import com.teamharambe.agris.aidll.Utils.CacheManager;
import com.teamharambe.agris.aidll.Utils.DataRetrieverResponse;
import com.teamharambe.agris.aidll.Utils.JSonUtils;
import com.teamharambe.agris.aidll.Web.DataRetriever;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataRetrieverResponse {

    String response;
    Date lastTimePressedBack;

    private static final String TAG = "MainActivity";
    public Categories categories;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ListItemsFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new Categories();
        setContentView(R.layout.activity_main);

        DataRetriever dataRetriever = new DataRetriever(this);
        dataRetriever.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ListItemsFragment.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onResponseReceived(String response) {
        this.response = response;
        CacheManager.populateCarListToCache(this, response);
    }

    @Override
    protected void onDestroy() {
//        categories.delete(this);
//        cars.delete(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        boolean areCategories = Categories.getAllSelectedCategories(getBaseContext()).size() > 0;
        boolean areCars = Cars.getAllSelectedCarIdList(getBaseContext()).size() > 0;
        if (id == R.id.action_compare_cars) {
            if (!areCategories || !areCars)
            {
                CharSequence alertMsg = "You need to add at least one ";
                if (!areCars && !areCategories)
                {
                    alertMsg = alertMsg + "Car and Criteria!";
                }
                else if (!areCars)
                {
                    alertMsg = alertMsg + "Car!";
                }
                else if (!areCategories)
                {
                    alertMsg = alertMsg + " Criteria!";
                }

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getBaseContext(), alertMsg, duration);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.WHITE);
                toast.show();

            }
            else {
                Intent compareCarsIntent = new Intent(getBaseContext(), ResultsActivity.class);
                startActivity(compareCarsIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        CharSequence alertMsg = "Press Back button again to exit application";
        int duration = Toast.LENGTH_SHORT;

        if (lastTimePressedBack == null)
        {
            lastTimePressedBack = new Date();
            Toast toast = Toast.makeText(getBaseContext(), alertMsg, duration);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.WHITE);
            toast.show();
        }
        else {
            if(((new Date().getTime() - lastTimePressedBack.getTime())/1000) > 10)
            {
                lastTimePressedBack = new Date();

                Toast toast = Toast.makeText(getBaseContext(), alertMsg, duration);
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.WHITE);
                toast.show();
            }
            else
            {
                finish();
            }
        }



//        super.onBackPressed();
    }
}
