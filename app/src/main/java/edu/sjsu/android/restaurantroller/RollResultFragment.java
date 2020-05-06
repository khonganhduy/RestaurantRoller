package edu.sjsu.android.restaurantroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button closeBtn = (Button) view.findViewById(R.id.roll_restaurant_close_btn);
        Button rerollBtn = (Button) view.findViewById(R.id.roll_restaurant_reroll_btn);

        // Set up information display
        Bundle restaurantData = getArguments();
        nameTextView.setText(restaurantData.getString(MainActivity.RESTAURANT_NAME_KEY));
        ratingCountView.setText(restaurantData.getInt(MainActivity.RESTAURANT_RATING_COUNT_KEY) + " reviews");
        String rating = "stars" + Double.toString(restaurantData.getDouble(MainActivity.RESTAURANT_RATING_KEY)).replaceAll("\\.", "");
        ratingIcon.setImageResource(view.getResources().getIdentifier(rating, "drawable", "edu.sjsu.android.restaurantroller"));
        distanceView.setText(String.format("%.2f",YelpHelper.metersToMiles(restaurantData.getDouble(MainActivity.RESTAURANT_DISTANCE_KEY))) + " mi");
        Picasso.get().load(restaurantData.getString(MainActivity.RESTAURANT_IMAGE_KEY)).into(restaurantIcon);

        // Set up buttons
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        rerollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View rollBtn = (View) getActivity().findViewById(R.id.roll_btn);
                ((MainActivity)getActivity()).rollRestaurants(rollBtn);
                dismiss();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
