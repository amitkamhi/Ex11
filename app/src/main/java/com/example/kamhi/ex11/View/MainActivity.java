package com.example.kamhi.ex11.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.R;

public class MainActivity extends Activity implements ItemsFragment.CountryselectList, DetailsFragment.CountryReporter, MyDialog.ResultsListener, FragmentManager.OnBackStackChangedListener {

    private int position = -1;
    private Country country;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                getFragmentManager().beginTransaction().add(android.R.id.content, new MyPrefernces()).addToBackStack(null).commit();
                return true;
            case R.id.action_exit:
                MyDialog.newInstance(MyDialog.EXIT).show(getFragmentManager(), "exit dialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position");
        }

      /*  if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            FragmentManager fm = getFragmentManager();
            if(savedInstanceState != null && fm.findFragmentByTag("items")!=null)
            {
                return;
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragContainer, new ItemsFragment(), "items").commit();
        }*/
    }

    @Override
    public void onCountryChange(int position, Country country) {
        this.position = position;
        DetailsFragment detailsFrag;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            detailsFrag = (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
        }
        else{
            detailsFrag = new DetailsFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragContainer, detailsFrag, "BBB")
                    .addToBackStack("BBB")
                    .commit();
            fm.executePendingTransactions();
        }
        this.country = country;
        detailsFrag.changeTo(this.country);
    }

    @Override
    public int getCurrentSelection() {
        return this.position;
    }

    @Override
    public void setInitCountry(Country country) {
        this.country = country;
        if(country == null){
            position = -1;
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            DetailsFragment detailsFragment =  (DetailsFragment) getFragmentManager().findFragmentById(R.id.detailsFragment);
            if(detailsFragment.isVisible()){
                detailsFragment.changeTo(country);
            }
        }
    }

    @Override
    public Country getCountryData() {
        return this.country;
    }


    @Override
    public void OnfinishDialog(int requestCode, Object result) {
        switch(requestCode)
        {
            case MyDialog.EXIT: {
                finish();
                break;
            }
        }

    }

    @Override
    public void onBackStackChanged() {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ItemsFragment iFragment = (ItemsFragment) getFragmentManager().findFragmentByTag("items");
            if(getFragmentManager().getBackStackEntryCount() == 0){
                iFragment.setMenuState(false);
            }
            else{
                iFragment.setMenuState(true);
            }
        }
    }

    public static class MyPrefernces extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
            return view;
        }
    }


}
