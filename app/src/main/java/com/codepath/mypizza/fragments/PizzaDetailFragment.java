package com.codepath.mypizza.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mypizza.MainActivity;
import com.codepath.mypizza.R;
import com.codepath.mypizza.data.Pizza;
import com.google.android.gms.analytics.HitBuilders;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;


@EFragment(R.layout.fragment_pizza_detail)
public class PizzaDetailFragment extends Fragment {
    MainActivity mainAct;
    int position = 0;
    TextView tvTitle;
    TextView tvDetails;


    @Click(R.id.btn_purchase)
    void purchaseBtnClicked() {
        Toast.makeText(mainAct, "Purchase button clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainAct = (MainActivity)getActivity();
        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                position = getArguments().getInt("position", 0);
            }
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
//        return inflater.inflate(R.layout.fragment_pizza_detail, parent, false);
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvDetails = (TextView) view.findViewById(R.id.tvDetails);

        // update view
        tvTitle.setText(Pizza.pizzaMenu[position]);
        tvDetails.setText(Pizza.pizzaDetails[position]);

        // Google Analytics Tracking.
        // set Screen Name with tvTitle.getText() value
        // to specify what specific pizza's detail page showed to users.
        // Without this implementation, it's not possible to see metrics per each pizza on the GA report
        // as PizzaDetailFragment is an same instance per each pizza.
        mainAct.mTracker.setScreenName("PizzaDetailFragment - " + tvTitle.getText());
        mainAct.mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    // Activity is calling this to update view on Fragment
    public void updateView(int position){
        tvTitle.setText(Pizza.pizzaMenu[position]);
        tvDetails.setText(Pizza.pizzaDetails[position]);
    }
}
