package edu.sjsu.android.restaurantroller;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActionBarActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if (itemId == R.id.help_btn){
            HelpFragment dialogFragment = new HelpFragment();
            AppCompatActivity activity = (AppCompatActivity) this;
            dialogFragment.show(activity.getSupportFragmentManager(), "editText");
        }
        return super.onOptionsItemSelected(item);
    }
}
