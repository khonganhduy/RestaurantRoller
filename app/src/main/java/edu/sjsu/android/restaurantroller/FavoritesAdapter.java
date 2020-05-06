package edu.sjsu.android.restaurantroller;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yelp.fusion.client.models.Category;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.TreeSet;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private ArrayList<Restaurant> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView nameTextView, tagsTextView;
        private Button addRemoveBtn;
        private boolean addedToRoll = false;
        public FavoritesViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.first_line);
            tagsTextView = (TextView) v.findViewById(R.id.tags_text);
            addRemoveBtn = (Button) v.findViewById(R.id.add_remove_button);
        }

        public void setAddedToRoll(boolean addedToRoll) {
            this.addedToRoll = addedToRoll;
        }
    }

    public FavoritesAdapter(ArrayList<Restaurant> myDataset) {
        mDataset = myDataset;
    }


    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list_row, parent, false);
        FavoritesAdapter.FavoritesViewHolder vh = new FavoritesViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Restaurant restaurant = mDataset.get(position);
        holder.nameTextView.setText(restaurant.getRestaurantName());
        holder.tagsTextView.setText(restaurant.getTags().toArray().toString());

        holder.addRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(restaurant.inRoller())
                    MainActivity.rollerList.remove(restaurant);
                else
                    MainActivity.rollerList.add(restaurant);
                restaurant.setInRoller(!restaurant.inRoller());
                notifyDataSetChanged();
            }}
        );

        String check = restaurant.inRoller() ? "Remove": "Add";
        holder.addRemoveBtn.setText(check);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //make sure in format of "http://example.com"
    protected void launchWebsite(String url, View v){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        v.getContext().startActivity(webIntent);
    }

}
