package com.example.kamhi.ex11.View;

import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kamhi.ex11.Controller.CountryAdapter;
import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class ItemsFragment extends ListFragment implements MyDialog.ResultsListener, CountryAdapter.CountryAdapterListener {

    CountryselectList listener;
    Context context;
    CountryAdapter adapter;
    static ArrayList<String> countries = new ArrayList<String>();
    Menu fragMenu;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        if (savedInstanceState == null){
            //countries = savedInstanceState.getStringArrayList("shownCountries");
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            ItemsFragment.countries = new ArrayList<String>(sp.getStringSet("shown", new HashSet<String>()));
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

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    adapter.OnItemLongClick(position);
                    listener.setInitCountry(null);
                    return true;
                }
            });
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
    public void onDestroy() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        boolean toSaveList = sp.getBoolean("toSaveList", false);
        if(toSaveList){
            Set<String> set = new HashSet<String>();
            set.addAll(countries);
            editor.putStringSet("shown", set);
        }
        else{
            editor.remove("shown");
            //countries.clear();
        }
        editor.commit();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.fragMenu = menu;
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
    public void setMenuState(boolean hidden){
        fragMenu.findItem(R.id.addCountry).setVisible(!hidden);
    }

    public interface CountryselectList{
        public void onCountryChange(int position, Country country);
        public int getCurrentSelection();
        public void setInitCountry(Country country);
    }
}