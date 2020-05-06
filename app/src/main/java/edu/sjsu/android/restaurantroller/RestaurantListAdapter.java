package edu.sjsu.android.restaurantroller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>{

    private ArrayList<Restaurant> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView nameTextView, ratingCountView, distanceView, rollWeightView;
        private ImageButton increaseWeight, decreaseWeight, yelpButton;
        private ImageView ratingIcon, restaurantIcon;

        public RestaurantViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.first_line);
            rollWeightView = (TextView) v.findViewById(R.id.roll_weight);
            increaseWeight = (ImageButton) v.findViewById(R.id.increase);
            decreaseWeight = (ImageButton) v.findViewById(R.id.decrease);
            yelpButton = (ImageButton) v.findViewById(R.id.yelp_button);

            ratingCountView = (TextView) v.findViewById(R.id.rating_text);
            ratingIcon = (ImageView) v.findViewById(R.id.rating_icon);

            restaurantIcon = (ImageView) v.findViewById(R.id.shop_icon);

            distanceView = (TextView) v.findViewById(R.id.distance_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RestaurantListAdapter(ArrayList<Restaurant> myDataset) {
        mDataset = myDataset;
    }

    //public void updateData(String data, int position){
    //  mDataset.set(position, data);
    //notifyItemChanged(position);
    //}

    // Create new views (invoked by the layout manager)
    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_row, parent, false);
        RestaurantViewHolder vh = new RestaurantViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Restaurant restaurant = mDataset.get(position);
        holder.nameTextView.setText(restaurant.getRestaurantName());
        holder.rollWeightView.setText(String.valueOf(restaurant.getWeight()));
        if (mDataset.get(position) instanceof YelpRestaurant){
            YelpRestaurant yelpRestaurant = (YelpRestaurant) restaurant;
            holder.ratingCountView.setVisibility(View.VISIBLE);
            holder.ratingIcon.setVisibility(View.VISIBLE);
            holder.distanceView.setVisibility(View.VISIBLE);
            holder.yelpButton.setVisibility(View.VISIBLE);


            holder.ratingCountView.setText(yelpRestaurant.getRatingCount() + " reviews");
            String rating = "stars" + Double.toString(yelpRestaurant.getRating()).replaceAll("\\.", "");
            holder.ratingIcon.setImageResource(holder.view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));
            holder.distanceView.setText(String.format("%.2f",YelpHelper.metersToMiles(yelpRestaurant.getDistance())) + " mi");
            Picasso.get().load(yelpRestaurant.getImageURL()).into(holder.restaurantIcon);
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

}
