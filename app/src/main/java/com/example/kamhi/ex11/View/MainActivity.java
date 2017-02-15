package com.example.kamhi.ex11.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kamhi.ex11.Controller.MyDialog;
import com.example.kamhi.ex11.Controller.MyListView;
import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.Model.DataLoader;
import com.example.kamhi.ex11.Model.XMLParser;
import com.example.kamhi.ex11.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements ItemsFragment.CountryselectList, DetailsFragment.CountryReporter, MyDialog.DialogListener {

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position");
        }

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            FragmentManager fm = getFragmentManager();
            if(savedInstanceState != null && fm.findFragmentByTag("AAA")!=null)
            {
                return;
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragContainer, new ItemsFragment(), "AAA").commit();
        }
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
    public String onFinishedDialog(){
        return null;
    }
}
