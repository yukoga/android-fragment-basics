package com.codepath.mypizza;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.codepath.mypizza.fragments.PizzaDetailFragment;
import com.codepath.mypizza.fragments.PizzaDetailFragment_;
import com.codepath.mypizza.fragments.PizzaMenuFragment;
import com.codepath.mypizza.fragments.PizzaMenuFragment_;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements PizzaMenuFragment.OnItemSelectedListener {
    // define mTracker variable as a public member of MainActivity class.
    public Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // Get tracker object and store it into mTracker if mTracker is null.
        if (mTracker == null) {
            AnalyticsApplication application = (AnalyticsApplication) getApplication();
            mTracker = application.getDefaultTracker();
        }
        // Set screen name
        mTracker.setScreenName("MainActivity");
        // Set custom dimension value.
        // The dimension value which is set by this way might be persisted within entire Apps lifecycle.
        // Thus, latest developer guide recommends us to set custom dimension values with HitBuilder as below.
        // See https://goo.gl/oWoSdU
        mTracker.set("&cd7", "Dimension 7 - MainActivity");
        // Send screenView hit with screen name.
        // Note that this screenView will be sent only once just after MainActivity has been created.
        // You can check that behavior in debug logs.
        mTracker.send(
                new HitBuilders.ScreenViewBuilder()
                        .setCustomDimension(8, "Another way to set a Custom Dimension")
                        .build()
        );

        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        if (savedInstanceState == null) {
            // Instance of first fragment
            PizzaMenuFragment firstFragment = new PizzaMenuFragment_();

            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.flContainer, firstFragment);
            ft.commit();
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            PizzaDetailFragment secondFragment = new PizzaDetailFragment_();
            Bundle args = new Bundle();
            args.putInt("position", 0);
            secondFragment.setArguments(args);
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.add(R.id.flContainer2, secondFragment);
            ft2.commit();
        }
    }

    @Override
    public void onPizzaItemSelected(int position) {
        Toast.makeText(
                this,
                "Called By Fragment A: position - "+ position, Toast.LENGTH_SHORT).show();

        // Load Pizza Detail Fragment
        PizzaDetailFragment secondFragment = new PizzaDetailFragment_();

        Bundle args = new Bundle();
        args.putInt("position", position);
        secondFragment.setArguments(args);


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
          getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.flContainer2, secondFragment)
              .commit();
        }else{
          getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.flContainer, secondFragment)
              .addToBackStack(null)
              .commit();
        }
    }
}
