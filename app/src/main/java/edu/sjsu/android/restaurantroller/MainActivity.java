package edu.sjsu.android.restaurantroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends MainActionBarActivity {

    // Buttons
    private Button addRestaurantBtn;
    private Button rollRestaurantsBtn;

    // Object to pull/put data from database
    private RestaurantData restaurantData;

    // Start labelling instance variables to the subtab they correlate to

    // Tab 1 variables
    private RecyclerView restaurantRecyclerView;
    private RecyclerView.Adapter restaurantAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<WeightedRestaurant> restaurantList;
    //private ArrayList<String> restaurantList;
    // Tab 2 variables
    private LinearLayout optionsTab;
    private EditText searchTermText, searchRadiusText;
    private ThumbTextSeekBar ratingSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use this variable to add or remove stuff from the database
        // to insert, make a new RestaurantEntity using RestaurantEntity(String name, String tag)
        // and use insert method of Restaurant Data
        restaurantData = new RestaurantData(getApplication());


        // Tab Setup Here
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("roller").setContent(R.id.restaurantsTab).setIndicator("Roll");
        tabs.addTab(spec);
        TabHost.TabSpec spec2 = tabs.newTabSpec("saved").setContent(R.id.favoritesTab).setIndicator("Favorites");
        tabs.addTab(spec2);
        TabHost.TabSpec spec3 = tabs.newTabSpec("options").setContent(R.id.optionsTab).setIndicator("Options");
        tabs.addTab(spec3);

        // Roller Tab Setup
        restaurantRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        restaurantRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        restaurantRecyclerView.setLayoutManager(layoutManager);

        // DUMMY DATA
        restaurantList = new ArrayList<WeightedRestaurant>();
        restaurantList.add(new WeightedRestaurant("test 1"));
        restaurantList.add(new WeightedRestaurant("test 2"));
        restaurantAdapter = new RestaurantListAdapter(restaurantList);
        restaurantRecyclerView.addItemDecoration(new DividerItemDecoration(restaurantRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        restaurantRecyclerView.setAdapter(restaurantAdapter);

        // Creates fragment to add a restaurant
        addRestaurantBtn = findViewById(R.id.add_restaurant_btn);
        addRestaurantBtn.setOnClickListener(view -> {
            AddRestaurantFragment dialogFragment = new AddRestaurantFragment();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            dialogFragment.show(activity.getSupportFragmentManager(), "editText");
        });

        // Rolls the restaurants and selects one randomly based on the weights
        rollRestaurantsBtn = (Button) findViewById(R.id.roll_btn);
        rollRestaurantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] weights = new int[restaurantList.size()];
                for (int i = 0; i < weights.length; i++){
                    weights[i] = restaurantList.get(i).getWeight();
                }

            }
        });


        // Options Tab Setup
        optionsTab = findViewById(R.id.optionsTab);
        optionsTab.setOnFocusChangeListener((view, b) -> {
            if(b){
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        searchTermText = findViewById(R.id.searchTerm);
        searchTermText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                optionsTab.requestFocus();
            }
            return false;
        });

        searchRadiusText = (EditText) findViewById(R.id.radiusValue);
        searchRadiusText.setFilters(new InputFilter[]{new InputFilterMinMax(0,25), new InputFilter.LengthFilter(4)});
        searchRadiusText.setOnFocusChangeListener((view, b) -> searchRadiusText.setCursorVisible(b));

        ratingSeekBar = (ThumbTextSeekBar) findViewById(R.id.ratingBar);
        ratingSeekBar.setThumbText("None");
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(progress == 0)
                    ratingSeekBar.setThumbText("None");
                else
                    ratingSeekBar.setThumbText(String.format( "%.1f", (progress + 1)/2.0));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}});
    }
}
