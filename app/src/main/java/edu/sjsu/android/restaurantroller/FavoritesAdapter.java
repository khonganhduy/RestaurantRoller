package edu.sjsu.android.restaurantroller;

import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {

    private ArrayList<Restaurant> mDataset;
    private RestaurantData restaurantData;

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
            addRemoveBtn = (Button) v.findViewById(R.id.add_remove_button_favorite);
        }

        public void setAddedToRoll(boolean addedToRoll) {
            this.addedToRoll = addedToRoll;
        }
    }

    public FavoritesAdapter(ArrayList<Restaurant> myDataset, RestaurantData rData) {
        mDataset = myDataset;
        restaurantData = rData;
    }

    public void addToDataset(Restaurant r){
        if (mDataset == null) {
            mDataset = new ArrayList<>();
        }
        mDataset.add(r);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void setDataset(ArrayList<Restaurant> restaurants){
        mDataset = restaurants;
        notifyDataSetChanged();
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
        String myTags = Arrays.toString(restaurant.getTags().toArray());
        holder.tagsTextView.setText(myTags.substring(1, myTags.length() - 1));

        holder.addRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.rollerAdapter.contains(restaurant))
                    Toast.makeText(holder.view.getContext(), "Item is already in the roller.", Toast.LENGTH_SHORT).show();
                else{
                    MainActivity.rollerAdapter.addToDataset(restaurant);
                    Toast.makeText(holder.view.getContext(), "Item added to the roller.", Toast.LENGTH_SHORT).show();
                }
            }}
        );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    //make sure in format of "http://example.com"
    protected void launchWebsite(String url, View v){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        v.getContext().startActivity(webIntent);
    }

}
