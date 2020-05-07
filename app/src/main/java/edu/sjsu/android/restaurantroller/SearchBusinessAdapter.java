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
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Category;

import java.util.ArrayList;
import java.util.TreeSet;

public class SearchBusinessAdapter extends RecyclerView.Adapter<SearchBusinessAdapter.SearchBusinessViewHolder> {

    private ArrayList<Restaurant> mDataset;


    public static class SearchBusinessViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private TextView nameTextView, ratingCountView, distanceView;
        private Button addRemoveBtn;
        private ImageButton yelpLaunch;
        private ImageView ratingIcon, restaurantIcon;
        public SearchBusinessViewHolder(View v) {
            super(v);
            view = v;
            nameTextView = (TextView) v.findViewById(R.id.first_line);
            yelpLaunch = (ImageButton) v.findViewById(R.id.yelp_button);

            ratingCountView = (TextView) v.findViewById(R.id.rating_count_text);
            ratingIcon = (ImageView) v.findViewById(R.id.rating_icon);

            restaurantIcon = (ImageView) v.findViewById(R.id.shop_icon);

            distanceView = (TextView) v.findViewById(R.id.distance_text);

            addRemoveBtn = (Button) v.findViewById(R.id.add_remove_button);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchBusinessAdapter(ArrayList<Business> myDataset) {
        ArrayList<Restaurant> dataSet = new ArrayList<>();
        for (Business b: myDataset)
        {
            TreeSet<String> tags = new TreeSet();
            for(Category c: b.getCategories())
                tags.add(c.getAlias());
            Restaurant r = new Restaurant(b, tags);
            dataSet.add(r);
        }
        mDataset = dataSet;
    }

    @Override
    public SearchBusinessAdapter.SearchBusinessViewHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_list_row, parent, false);
        SearchBusinessAdapter.SearchBusinessViewHolder vh = new SearchBusinessAdapter.SearchBusinessViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(SearchBusinessAdapter.SearchBusinessViewHolder holder, final int position) {

        holder.ratingCountView.setVisibility(View.VISIBLE);
        holder.ratingIcon.setVisibility(View.VISIBLE);
        holder.distanceView.setVisibility(View.VISIBLE);
        holder.yelpLaunch.setVisibility(View.VISIBLE);
        holder.restaurantIcon.setVisibility(View.VISIBLE);

        Restaurant r = mDataset.get(position);
        Business b = r.getBusinessSource();

        holder.nameTextView.setText(b.getName());

        View.OnClickListener launchWeb = view -> launchWebsite(b.getUrl(), holder.view);
        Picasso.get().load(b.getImageUrl().replaceAll("o\\.jpg", "ms.jpg")).into(holder.restaurantIcon);
        holder.restaurantIcon.setOnClickListener(launchWeb);
        holder.yelpLaunch.setOnClickListener(launchWeb);

        holder.ratingCountView.setText(b.getReviewCount() + " reviews");
        String rating = "stars" + Double.toString(b.getRating()).replaceAll("\\.", "");
        holder.ratingIcon.setImageResource(holder.view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));
        holder.distanceView.setText(String.format("%.2f", YelpHelper.metersToMiles(b.getDistance())) + " mi");

        holder.addRemoveBtn.setOnClickListener(view -> {
                if(r.inRoller())
                    MainActivity.rollerAdapter.removeFromDataset(r);
                else
                    MainActivity.rollerAdapter.addToDataset(r);
                r.setInRoller(!r.inRoller());
                Log.i("checking", MainActivity.rollerList.toString());
                notifyDataSetChanged();
        });

        String check = r.inRoller() ? "Remove": "Add";
        holder.addRemoveBtn.setText(check);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    protected void launchWebsite(String url, View v) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        v.getContext().startActivity(webIntent);
    }
}
