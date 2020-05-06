package edu.sjsu.android.restaurantroller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yelp.fusion.client.models.Business;

import java.util.ArrayList;

public class SearchBusinessAdapter extends RecyclerView.Adapter<RollerListAdapter.RollverViewHolder> {

    private ArrayList<Business> mDataset;


    public static class BusinessViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView nameTextView, ratingCountView, distanceView;
        private TextView rollWeightView;
        private ImageButton addToRoll;
        private ImageButton decreaseWeight;

        public BusinessViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.first_line);
            rollWeightView = (TextView) v.findViewById(R.id.roll_weight);
            addToRoll = (ImageButton) v.findViewById(R.id.increase);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchBusinessAdapter(ArrayList<Business> myDataset) {
        mDataset = myDataset;
    }

    //public void updateData(String data, int position){
    //  mDataset.set(position, data);
    //notifyItemChanged(position);
    //}

    // Create new views (invoked by the layout manager)
    @Override
    public RollerListAdapter.RollverViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list_row, parent, false);
        RollerListAdapter.RollverViewHolder vh = new RollerListAdapter.RollverViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RollerListAdapter.RollverViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //WeightedRestaurant restaurant = mDataset.get(position);
        //holder.nameTextView.setText(restaurant.getRestaurantName());

        // Set the weight of the restauran
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
