package edu.sjsu.android.restaurantroller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpFragment extends DialogFragment{

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
        return inflater.inflate(R.layout.help_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button closeBtn = (Button) view.findViewById(R.id.help_close_btn);
        closeBtn.setOnClickListener(view12 -> dismiss());

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
}
