package edu.sjsu.android.restaurantroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Start labelling instance variables to the subtab they correlate to

    // Tab 1 variables
    private RecyclerView restaurantRecyclerView;
    private RecyclerView.Adapter restaurantAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> restaurantList;
    // Tab 2 variables
    private EditText searchRadiusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab Setup Here
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("restaurants").setContent(R.id.restaurantsTab).setIndicator("Restaurants");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("options").setContent(R.id.optionsTab).setIndicator("Options");
        tabs.addTab(spec);

        // Tab 1 Setup
        restaurantRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        restaurantRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        restaurantRecyclerView.setLayoutManager(layoutManager);
        restaurantList = new ArrayList<String>(Arrays.asList("Test 1", "Test 2"));
        restaurantAdapter = new RestaurantListAdapter(restaurantList);
        restaurantRecyclerView.addItemDecoration(new DividerItemDecoration(restaurantRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        restaurantRecyclerView.setAdapter(restaurantAdapter);


        // Tab 2 Setup
        searchRadiusValue = (EditText) findViewById(R.id.radiusValue);
        searchRadiusValue.setFilters(new InputFilter[]{new InputFilterMinMax(0,25)});
    }
}
