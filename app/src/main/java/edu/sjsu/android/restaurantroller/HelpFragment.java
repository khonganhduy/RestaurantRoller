package edu.sjsu.android.restaurantroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpFragment extends DialogFragment{
/*
    private ImageView arrowImage;
    private Animation rotate90CounterClockwise;
    private Animation rotate90Clockwise;
*/

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
        return inflater.inflate(R.layout.help_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button closeBtn = (Button) view.findViewById(R.id.help_close_btn);
        closeBtn.setOnClickListener(view12 -> dismiss());
        /*
        TextView rollBody = (TextView) view.findViewById(R.id.body_roll);
        TextView favoriteBody = (TextView) view.findViewById(R.id.body_favorite);
        TextView searchResultBody = (TextView) view.findViewById(R.id.body_search_result);
        TextView searchBody = (TextView) view.findViewById(R.id.body_search);
        ((TextView) view.findViewById(R.id.help_roll)).setOnClickListener(view1 -> animateView(rollBody));
        ((TextView) view.findViewById(R.id.help_favorite)).setOnClickListener((view1 -> animateView(favoriteBody)));
        ((TextView) view.findViewById(R.id.help_search_result)).setOnClickListener((view1 -> animateView(searchResultBody)));
        ((TextView) view.findViewById(R.id.help_search)).setOnClickListener((view1 -> animateView(searchBody)));

        arrowImage = (ImageView)view.findViewById(R.id.drop_down_arrow);
        rotate90CounterClockwise = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate90_counter_clockwise);
        rotate90Clockwise = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate90_clockwise);
*/
        ArrayList<String> myDataSet = new ArrayList<String>();
        myDataSet.add(getResources().getString(R.string.help_roll));
        myDataSet.add(getResources().getString(R.string.help_favorite));
        myDataSet.add(getResources().getString(R.string.help_search_results));
        myDataSet.add(getResources().getString(R.string.help_search));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.help_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example
        HelpFragmentAdapter mAdapter = new HelpFragmentAdapter(myDataSet);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
/*
    public void animateView(TextView body){
        int shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        if(body.getVisibility() == View.GONE){
            arrowImage.startAnimation(rotate90CounterClockwise);
            body.setAlpha(0f);
            body.setVisibility(View.VISIBLE);

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            body.animate()
                    .alpha(1f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);

        }
        else if (body.getVisibility() == View.VISIBLE){
            arrowImage.startAnimation(rotate90Clockwise);
            // TEST THIS MORE LATER
            body.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            body.setVisibility(View.GONE);
                        }
                    });
        }
    }*/
}
