package edu.sjsu.android.restaurantroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpFragmentAdapter extends RecyclerView.Adapter<HelpFragmentAdapter.MyViewHolder>{
    private ArrayList<String> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private View view;
        private View row;
        private TextView header;
        private TextView body;
        private ImageView dropDownArrow;


        public MyViewHolder(View v) {
            super(v);
            view = v;
            row = (RelativeLayout) v.findViewById(R.id.help_row_item);
            header = (TextView) v.findViewById(R.id.header);
            body = (TextView) v.findViewById(R.id.body);
            dropDownArrow = (ImageView) v.findViewById(R.id.drop_down_arrow);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HelpFragmentAdapter(ArrayList<String> myDataset) {
        this.mDataset = myDataset;
    }

    //public void updateData(String data, int position){
    //  mDataset.set(position, data);
    //notifyItemChanged(position);
    //}

    // Create new views (invoked by the layout manager)
    @Override
    public HelpFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.help_fragment_row, parent, false);
        HelpFragmentAdapter.MyViewHolder vh = new HelpFragmentAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HelpFragmentAdapter.MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.header.setText(mDataset.get(position));
        switch (position){
            case 0:
                holder.body.setText(holder.view.getContext().getResources().getString(R.string.body_roll));
                break;
            case 1:
                holder.body.setText(holder.view.getContext().getResources().getString(R.string.body_favorite));
                break;
            case 2:
                holder.body.setText(holder.view.getContext().getResources().getString(R.string.body_search_results));
                break;
            case 3:
                holder.body.setText(holder.view.getContext().getResources().getString(R.string.body_search));
                break;
        }

        Animation rotate90CounterClockwise = AnimationUtils.loadAnimation(holder.view.getContext(), R.anim.rotate90_counter_clockwise);
        Animation rotate90Clockwise = AnimationUtils.loadAnimation(holder.view.getContext(), R.anim.rotate90_clockwise);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shortAnimationDuration = holder.view.getContext().getResources().getInteger(
                        android.R.integer.config_shortAnimTime);
                if(holder.body.getVisibility() == View.GONE){
                    holder.dropDownArrow.startAnimation(rotate90CounterClockwise);
                    holder.body.setAlpha(0f);
                    holder.body.setVisibility(View.VISIBLE);

                    // Animate the content view to 100% opacity, and clear any animation
                    // listener set on the view.
                    holder.body.animate()
                            .alpha(1f)
                            .setDuration(shortAnimationDuration)
                            .setListener(null);

                }
                else if (holder.body.getVisibility() == View.VISIBLE){
                    holder.dropDownArrow.startAnimation(rotate90Clockwise);
                    // TEST THIS MORE LATER
                    holder.body.animate()
                            .alpha(0f)
                            .setDuration(shortAnimationDuration)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    holder.body.setVisibility(View.GONE);
                                }
                            });
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
