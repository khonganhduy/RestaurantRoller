package edu.sjsu.android.restaurantroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
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
    private ThumbTextSeekBar ratingSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab Setup Here
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("restaurants").setContent(R.id.restaurantsTab).setIndicator("Restaurants",getDrawable(R.drawable.settings_icon));
        tabs.addTab(spec);
        TabHost.TabSpec spec2 = tabs.newTabSpec("options").setContent(R.id.optionsTab).setIndicator("Options",getDrawable(R.drawable.settings_icon));
        tabs.addTab(spec2);

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
        searchRadiusValue.setFilters(new InputFilter[]{new InputFilterMinMax(0,25), new InputFilter.LengthFilter(4)});
        /*searchRadiusValue.addTextChangedListener(new TextWatcher()  {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s)  {
                String val = searchRadiusValue.getText().toString();
                if(!val.isEmpty()){
                    if (Double.parseDouble(val) > 25)
                        searchRadiusValue.setError("Maximum is 25 miles");
                    else
                        searchRadiusValue.setError(null);
                }
            }});*/
        searchRadiusValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                searchRadiusValue.setCursorVisible(b);
                if(!b){
                    InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
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

            new YelpHelper(this);
    }
}
