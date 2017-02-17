package com.example.kamhi.ex11.View;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamhi.ex11.Controller.CountryAdapter;
import com.example.kamhi.ex11.Controller.MyDialog;
import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.Model.DataLoader;
import com.example.kamhi.ex11.R;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class ItemsFragment extends ListFragment implements MyDialog.ResultsListener, CountryAdapter.CountryAdapterListener {

    CountryselectList listener;
    Context context;
    CountryAdapter adapter;
    static ArrayList<String> countries = new ArrayList<String>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        if (savedInstanceState != null){
            countries = savedInstanceState.getStringArrayList("shownCountries");
        }
        else if (countries == null){
            countries = new ArrayList<String>();
        }
        try {
            this.listener = (CountryselectList) getActivity();
            this.adapter = new CountryAdapter(getActivity(), countries, this);
            this.adapter.sort(new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            setListAdapter(this.adapter);

            final int selectPosition = listener.getCurrentSelection();
            /*getListView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(selectPosition != -1){
                        getListView().requestFocusFromTouch();
                        getListView().setSelection(selectPosition);
                        getListView().setSelector(android.R.color.holo_blue_dark);
                    }
                }
            }, 100);*/

            if(selectPosition !=-1){
                listener.setInitCountry(this.adapter.getItem(selectPosition));
            }
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'ClickHandler");
        }

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("shownCountries", countries);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu.findItem(R.id.addCountry) == null){
            inflater.inflate(R.menu.main, menu);
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addCountry:{
                MyDialog.newInstance(MyDialog.ADD).show(getChildFragmentManager(), null);
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }

        }
    }


    public ArrayList<String> getMissingCountry()
    {
        return adapter.getMissingCountries(countries);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.items_frag, container, false);
    }

    @Override
    public void onListItemClick(final ListView l, View v, final int position, long id) {
        getListView().setSelector(android.R.color.holo_blue_dark);
        listener.onCountryChange(position, this.adapter.getItem(position));
    }

    @Override
    public void OnfinishDialog(int requestCode, Object result) {
        switch (requestCode){
            case MyDialog.ADD:
                adapter.addNewCountry(result.toString());
                break;
            case MyDialog.EXIT_DIALOG:
                break;
        }
    }

    @Override
    public void updateShownList(boolean toAdd, String countryName) {
        if (toAdd){
            countries.add(countryName);
        }
        else {
            countries.remove(countryName);
        }
    }

    public interface CountryselectList{
        public void onCountryChange(int position, Country country);
        public int getCurrentSelection();
        public void setInitCountry(Country country);
    }
}