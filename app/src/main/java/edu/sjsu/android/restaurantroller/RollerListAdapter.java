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

    private ArrayList<WeightedRestaurant> mDataset;

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
    public RollerListAdapter(ArrayList<WeightedRestaurant> myDataset) {
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
        WeightedRestaurant restaurant = mDataset.get(position);
        holder.nameTextView.setText(restaurant.getRestaurantName());

        holder.ratingCountView.setText(restaurant.getRatingCount() + " reviews");
        String rating = "stars" + Double.toString(restaurant.getRating()).replaceAll("\\.", "");
        holder.ratingIcon.setImageResource(holder.view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));

        holder.distanceView.setText(String.format("%.2f",YelpHelper.metersToMiles(restaurant.getDistance())) + " mi");

        View.OnClickListener launchWeb = view -> launchWebsite(restaurant.getWebsiteURL(), holder.view);
        Picasso.get().load(restaurant.getImageURL()).into(holder.restaurantIcon);
        holder.restaurantIcon.setOnClickListener(launchWeb);
        holder.yelpLaunch.setOnClickListener(launchWeb);


        // Set the weight of the restaurant
        holder.rollWeightView.setText(String.valueOf(restaurant.getWeight()));


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
                if (restaurant.getWeight() > 0){
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

}
