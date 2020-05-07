package edu.sjsu.android.restaurantroller;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class RollerListAdapter extends RecyclerView.Adapter<RollerListAdapter.RollverViewHolder>{

    private ArrayList<Restaurant> mDataset = null;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RollverViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView nameTextView, ratingCountView, distanceView, rollWeightView;
        private ImageButton increaseWeight, decreaseWeight, yelpLaunch;
        private ImageView ratingIcon, restaurantIcon;

        public RollverViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.first_line);
            rollWeightView = (TextView) v.findViewById(R.id.roll_weight);
            increaseWeight = (ImageButton) v.findViewById(R.id.increase);
            decreaseWeight = (ImageButton) v.findViewById(R.id.decrease);
            yelpLaunch = (ImageButton) v.findViewById(R.id.yelp_button);

            ratingCountView = (TextView) v.findViewById(R.id.rating_count_text);
            ratingIcon = (ImageView) v.findViewById(R.id.rating_icon);

            restaurantIcon = (ImageView) v.findViewById(R.id.shop_icon);

            distanceView = (TextView) v.findViewById(R.id.distance_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RollerListAdapter(ArrayList<Restaurant> myDataset) {
        mDataset = myDataset;
    }

    //public void updateData(String data, int position){
    //  mDataset.set(position, data);
    //notifyItemChanged(position);
    //}

    // Create new views (invoked by the layout manager)
    @Override
    public RollverViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roller_list_row, parent, false);
        RollverViewHolder vh = new RollverViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RollverViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Restaurant restaurant = mDataset.get(position);
        holder.nameTextView.setText(restaurant.getRestaurantName());
        holder.rollWeightView.setText(String.valueOf(restaurant.getWeight()));
        Log.i("Restaraunt:", restaurant.getRestaurantName());
        Log.i("Pos", "v:" + position);
        if (restaurant instanceof YelpRestaurant){
            YelpRestaurant yelpRestaurant = (YelpRestaurant) restaurant;
            holder.ratingCountView.setVisibility(View.VISIBLE);
            holder.ratingIcon.setVisibility(View.VISIBLE);
            holder.distanceView.setVisibility(View.VISIBLE);
            holder.yelpLaunch.setVisibility(View.VISIBLE);
            holder.restaurantIcon.setVisibility(View.VISIBLE);

            View.OnClickListener launchWeb = view -> launchWebsite(yelpRestaurant.getWebsiteURL(), holder.view);
            Picasso.get().load(yelpRestaurant.getImageURL()).into(holder.restaurantIcon);
            holder.restaurantIcon.setOnClickListener(launchWeb);
            holder.yelpLaunch.setOnClickListener(launchWeb);

            holder.ratingCountView.setText(yelpRestaurant.getRatingCount() + " reviews");
            String rating = "stars" + Double.toString(yelpRestaurant.getRating()).replaceAll("\\.", "");
            holder.ratingIcon.setImageResource(holder.view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));
            holder.distanceView.setText(String.format("%.2f",YelpHelper.metersToMiles(yelpRestaurant.getDistance())) + " mi");
        }


        // Changes weight when chevrons are clicked
        holder.increaseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurant.setWeight(restaurant.getWeight() + 1);
                notifyDataSetChanged();
            }
        });
        holder.decreaseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurant.getWeight() > MainActivity.MIN_WEIGHT){
                    restaurant.setWeight(restaurant.getWeight() - 1);
                    notifyDataSetChanged();
                }
            }
        });
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

    public boolean addToDataset(Restaurant r){
        if(mDataset == null){
            mDataset = new ArrayList<>();
        }
        boolean ret = mDataset.add(r);
        notifyItemInserted(mDataset.size() - 1);
        return ret;
    }

    public boolean removeFromDataset(Restaurant r){
        if(mDataset == null)
            return false;
        int ind = mDataset.indexOf(r);
        boolean ret = mDataset.remove(r);
        if(ret)
            notifyItemRemoved(ind);
        return ret;
    }

    public boolean contains(Restaurant r){
        return mDataset.contains(r);
    }

}
