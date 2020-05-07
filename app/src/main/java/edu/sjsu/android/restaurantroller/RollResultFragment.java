package edu.sjsu.android.restaurantroller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

public class RollResultFragment extends DialogFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
        return inflater.inflate(R.layout.roll_result_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView nameTextView = (TextView) view.findViewById(R.id.selected_first_line);
        TextView ratingCountView = (TextView) view.findViewById(R.id.selected_rating_text);
        TextView distanceView = (TextView) view.findViewById(R.id.selected_distance_text);
        ImageView ratingIcon = (ImageView) view.findViewById(R.id.selected_rating_icon);
        ImageView restaurantIcon = (ImageView) view.findViewById(R.id.selected_shop_icon);
        ImageButton selectedYelpButton = (ImageButton) view.findViewById(R.id.selected_yelp_button);
        Button closeBtn = (Button) view.findViewById(R.id.roll_restaurant_close_btn);
        Button rerollBtn = (Button) view.findViewById(R.id.roll_restaurant_reroll_btn);

        // Set up information display
        Bundle restaurantData = getArguments();
        nameTextView.setText(restaurantData.getString(MainActivity.RESTAURANT_NAME_KEY));
        if (restaurantData.getString(MainActivity.RESTAURANT_URL_KEY) != null && !restaurantData.getString(MainActivity.RESTAURANT_URL_KEY).equals("")){
            ratingCountView.setVisibility(View.VISIBLE);
            ratingIcon.setVisibility(View.VISIBLE);
            restaurantIcon.setVisibility(View.VISIBLE);
            selectedYelpButton.setVisibility(View.VISIBLE);

            ratingCountView.setText(restaurantData.getInt(MainActivity.RESTAURANT_RATING_COUNT_KEY) + " reviews");
            String rating = "stars" + Double.toString(restaurantData.getDouble(MainActivity.RESTAURANT_RATING_KEY)).replaceAll("\\.", "");
            ratingIcon.setImageResource(view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));
            distanceView.setText(String.format("%.2f",YelpHelper.metersToMiles(restaurantData.getDouble(MainActivity.RESTAURANT_DISTANCE_KEY))) + " mi");
            Picasso.get().load(restaurantData.getString(MainActivity.RESTAURANT_IMAGE_KEY)).into(restaurantIcon);
        }

        // Set up buttons
        closeBtn.setOnClickListener(view12 -> dismiss());

        rerollBtn.setOnClickListener(view13 -> {
            View rollBtn = (View) getActivity().findViewById(R.id.roll_btn);
            ((MainActivity)getActivity()).rollRestaurants(rollBtn, MainActivity.rollerList);
            dismiss();
        });

        selectedYelpButton.setOnClickListener(view1 -> {
            String url = restaurantData.getString(MainActivity.RESTAURANT_URL_KEY);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(webIntent);
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
