package edu.sjsu.android.restaurantroller;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;


public class AddRestaurantFragment extends DialogFragment {

    // Temporary autofill
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
    private Button confirmBtn;
    private Button cancelBtn;
    private Button addTagBtn;
    private AutoCompleteTextView tagSearch;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AddTagsAdapter mAdapter;
    private ArrayList<String> myDataSet;

    private InputFilter filter;


    public static class AlphaNumericInputFilter implements InputFilter {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            // Only keep characters that are alphanumeric
            StringBuilder builder = new StringBuilder();
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (Character.isLetter(c)) {
                    builder.append(Character.toLowerCase(c));
                }
            }

            // If all characters are valid, return null, otherwise only return the filtered characters
            boolean allCharactersValid = (builder.length() == end - start);
            return allCharactersValid ? null : builder.toString();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
        return inflater.inflate(R.layout.add_new_restaurant_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        confirmBtn = (Button) view.findViewById(R.id.confirm_btn);
        cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        addTagBtn = (Button) view.findViewById(R.id.add_tag_btn);
        tagSearch = (AutoCompleteTextView) view.findViewById(R.id.tag_search);
        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(tagSearch.getFilters()));
        curInputFilters.add(0, new AlphaNumericInputFilter());
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        tagSearch.setFilters(newInputFilters);

        // Temporary Dataset
        myDataSet = new ArrayList<String>(Arrays.asList("jojo", "dab", "test", "test2", "test3", "test4"));
        // Temporary autofill adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        tagSearch.setAdapter(adapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.add_tags_recycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example
        mAdapter = new AddTagsAdapter(myDataSet);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDataSet.add(0, tagSearch.getText().toString());
                tagSearch.getText().clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
