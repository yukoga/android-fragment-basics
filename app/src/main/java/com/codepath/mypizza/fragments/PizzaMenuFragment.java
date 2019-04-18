package com.codepath.mypizza.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.mypizza.MainActivity;
import com.codepath.mypizza.R;
import com.codepath.mypizza.data.Pizza;
import com.google.android.gms.analytics.HitBuilders;

import org.androidannotations.annotations.EFragment;

/**
 * Created by Shyam Rokde on 8/5/16.
 */

@EFragment(R.layout.fragment_pizza_menu)
public class PizzaMenuFragment extends Fragment {
    ArrayAdapter<String> itemsAdapter;
    private MainActivity mainAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, Pizza.pizzaMenu);
        mainAct = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_pizza_menu, parent, false);
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // go to activity to load pizza details fragment
            listener.onPizzaItemSelected(position);
          }
        });

        // Google Analytics tracking code.
        mainAct.mTracker.setScreenName("PizzaMenuFragment");
        mainAct.mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    private OnItemSelectedListener listener;
    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){
            this.listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
            + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }
    }

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onPizzaItemSelected(int position);
    }
}
