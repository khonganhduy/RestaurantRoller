package edu.sjsu.android.restaurantroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputFilter;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;

import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;

public class MainActivity extends MainActionBarActivity {
    protected static final int QUERY_FINISHED = 1;
    public static final int MIN_WEIGHT = 1;
    public static final String RESTAURANT_NAME_KEY = "restaurant_name";
    public static final String RESTAURANT_WEIGHT_KEY = "restaurant_weight";
    public static final String RESTAURANT_IMAGE_KEY = "restaurant_image";
    public static final String RESTAURANT_RATING_KEY = "restaurant_rating";
    public static final String RESTAURANT_RATING_COUNT_KEY = "restaurant_rating_count";
    public static final String RESTAURANT_DISTANCE_KEY = "restaurant_distance";


    // Buttons


    // Object to pull/put data from database


    // Start labelling instance variables to the subtab they correlate to
    private TabHost tabs;

    // Roller Tab variables
    private RecyclerView rollerRecyclerView;
    private RecyclerView.Adapter rollerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Restaurant> restaurantList;
    private Button addRestaurantBtn, rollRestaurantsBtn;

    // Favorites Tab variables
    private RecyclerView favoriteRecyclerView;
    private RecyclerView.Adapter favoriteAdapter;

    // Search Results Tab variables
    private RestaurantData restaurantData;

    // Search Tab variables
    private LinearLayout searchTab;
    private EditText searchTermText, searchRadiusText;
    private ThumbTextSeekBar ratingSeekBar;
    private Button searchButton;
    private Spinner minSpinner, maxSpinner;

    private Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message inputMessage){

            switch(inputMessage.what){
                case QUERY_FINISHED:
                    onQueryFinish((Response<SearchResponse>)inputMessage.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTabs(savedInstanceState);

        // Use this variable to add or remove stuff from the database
        // to insert, make a new RestaurantEntity using RestaurantEntity(String name, String tag)
        // and use insert method of Restaurant Data
        restaurantData = new RestaurantData(getApplication());

        setUpRollerTab(savedInstanceState);
        setUpFavoritesTab(savedInstanceState);
        setUpResultsTab(savedInstanceState);
        setUpSearchTab(savedInstanceState);
    }

    private void setupTabs(Bundle savedInstanceState){
        // Main Tab Setup Here
        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("roller").setContent(R.id.roller_tab).setIndicator("Roll");
        tabs.addTab(spec);
        TabHost.TabSpec spec2 = tabs.newTabSpec("favorites").setContent(R.id.favorites_lists_tab).setIndicator("Favorite");
        tabs.addTab(spec2);
        TabHost.TabSpec spec3 = tabs.newTabSpec("search results").setContent(R.id.search_result_lists_tab).setIndicator("Search Results");
        tabs.addTab(spec3);
        TabHost.TabSpec spec4 = tabs.newTabSpec("search").setContent(R.id.search_tab).setIndicator("Search");
        tabs.addTab(spec4);
    }

    private void setUpRollerTab(Bundle savedInstanceState) {
        // Roller Tab Setup
        rollerRecyclerView = (RecyclerView) findViewById(R.id.roller_recycler_view);
        rollerRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rollerRecyclerView.setLayoutManager(layoutManager);

        // DUMMY DATA
        restaurantList = new ArrayList<Restaurant>();
        restaurantList.add(new YelpRestaurant("dddddddddddddddddddddddddddd", 1.0, 24, 40000, "test1IconUrl"));
        restaurantList.add(new YelpRestaurant("test 2", 4.0, 583, 29, "test2IconUrl"));
        rollerAdapter = new RollerListAdapter(restaurantList);
        rollerRecyclerView.addItemDecoration(new DividerItemDecoration(rollerRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        rollerRecyclerView.setAdapter(rollerAdapter);

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
                rollRestaurants(view);
            }
        });
    }
    private void setUpFavoritesTab(Bundle savedInstanceState){

    }

    private void setUpResultsTab(Bundle savedInstanceState){

    }

    private void setUpSearchTab(Bundle savedInstanceState){
        // Search Tab Setup
        searchTab = findViewById(R.id.search_tab);
        searchTab.setOnFocusChangeListener((view, b) -> {
            if(b){
                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); }});

        searchTermText = findViewById(R.id.searchTerm);
        searchTermText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                searchTab.requestFocus(); }
            return false; });

        searchRadiusText = (EditText) findViewById(R.id.radiusValue);
        searchRadiusText.setFilters(new InputFilter[]{new InputFilterMinMax(0,25), new InputFilter.LengthFilter(4)});
        searchRadiusText.setOnFocusChangeListener((view, b) -> searchRadiusText.setCursorVisible(b));

        ratingSeekBar = (ThumbTextSeekBar) findViewById(R.id.ratingBar);
        ratingSeekBar.setThumbText("1.0");
        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                ratingSeekBar.setThumbText(String.format( "%.1f", (progress + 2)/2.0));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}});

        minSpinner = findViewById(R.id.priceMin);
        minSpinner.setSelection(0);
        minSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(maxSpinner.getSelectedItemPosition() < pos)
                    maxSpinner.setSelection(pos); }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
        maxSpinner = findViewById(R.id.priceMax);
        maxSpinner.setSelection(3);
        maxSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if (minSpinner.getSelectedItemPosition() > pos)
                    minSpinner.setSelection(pos); }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){}});

        // Instantiates yelp API, do not remove!
        try {
            YelpHelper yelp = new YelpHelper(this);
        } catch (IOException e) {
            e.printStackTrace(); // TODO show toast on exception
        }

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> {
            YelpHelper.YelpQueryBuilder query = new YelpHelper.YelpQueryBuilder()
                    .setMinMaxPrice(minSpinner.getSelectedItemPosition() + 1, maxSpinner.getSelectedItemPosition() + 1)
                    .setLatLong(37.4256019322,-121.910018101); // TODO get GPS coordinates and shove them here
            String term = searchTermText.getText().toString();
            if(!term.isEmpty())
                query.setSearchTerm(term);
            String rad = searchRadiusText.getText().toString();
            if(!rad.isEmpty())
                query.setRadius(Double.parseDouble(rad));
            int rating = ratingSeekBar.getProgress();
            if(rating > 0)
                query.setRatingMin(rating);
            try {
                YelpHelper.YelpQuery runningQuery = query.executeQuery(handler);
            } catch (ExecutionException e) {
                e.printStackTrace(); // TODO Show toasts on exceptions
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    protected void onQueryFinish(Response<SearchResponse> r){
        Log.i("asyncro response", r.toString());
        tabs.setCurrentTab(0);
        ArrayList<Business> bis = r.body().getBusinesses();
        ArrayList<Restaurant> t = new ArrayList<>();
        for(Business b: bis){
            double rating = b.getRating();
            if(rating >= (ratingSeekBar.getProgress() + 2)/ 2.0) {
                String url = b.getImageUrl().replaceAll("o\\.jpg", "ms.jpg");
                YelpRestaurant w = new YelpRestaurant(b.getName(), rating, b.getReviewCount(), b.getDistance(), url);
                t.add(w);
            }
        }
        rollerAdapter = new RollerListAdapter(t);
        rollerRecyclerView.addItemDecoration(new DividerItemDecoration(rollerRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        rollerRecyclerView.setAdapter(rollerAdapter);
    }

    public void rollRestaurants(View view){
        Integer[] weights = new Integer[restaurantList.size()];
        for (int i = 0; i < weights.length; i++){
            weights[i] = restaurantList.get(i).getWeight();
        }
        Restaurant rolledRestaurant = restaurantList.get(new RollerUtility().rollWeighted(weights));
        Bundle bundle = new Bundle();
        bundle.putString(RESTAURANT_NAME_KEY, rolledRestaurant.getRestaurantName());
        bundle.putInt(RESTAURANT_WEIGHT_KEY, rolledRestaurant.getWeight());
        if (rolledRestaurant instanceof YelpRestaurant){
            YelpRestaurant yelpRestaurant = (YelpRestaurant) rolledRestaurant;
            bundle.putString(RESTAURANT_IMAGE_KEY, yelpRestaurant.getImageURL());
            bundle.putDouble(RESTAURANT_RATING_KEY, yelpRestaurant.getRating());
            bundle.putInt(RESTAURANT_RATING_COUNT_KEY, yelpRestaurant.getRatingCount());
            bundle.putDouble(RESTAURANT_DISTANCE_KEY, yelpRestaurant.getDistance());
        }
        RollResultFragment dialogFragment = new RollResultFragment();
        dialogFragment.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        dialogFragment.show(activity.getSupportFragmentManager(), "roll_restaurants");
    }
}
