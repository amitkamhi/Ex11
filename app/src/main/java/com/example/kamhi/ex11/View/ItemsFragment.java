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

import com.example.kamhi.ex11.Controller.CountryAdapter;
import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.Model.DataLoader;
import com.example.kamhi.ex11.R;

import java.util.Comparator;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class ItemsFragment extends ListFragment{

    CountryselectList listener;
    Context context;
    CountryAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        this.context = getActivity();
        try {
            this.listener = (CountryselectList) getActivity();
            this.adapter = new CountryAdapter(this.context);
            this.adapter.sort(new Comparator<Country>() {
                @Override
                public int compare(Country o1, Country o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            setListAdapter(this.adapter);

            final int selectPosition = listener.getCurrentSelection();

            getListView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(selectPosition != -1){
                        getListView().requestFocusFromTouch();
                        getListView().setSelection(selectPosition);
                        getListView().setSelector(android.R.color.holo_blue_dark);
                    }
                }
            }, 100);

            if(selectPosition !=-1){
                listener.setInitCountry(this.adapter.getItem(selectPosition));
            }
            /*
            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Delete?")
                            .setMessage("Are you sure you want to delete this country?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });

                    return true;
                }
            });*/
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'ClickHandler");
        }

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.items_frag, container, false);
    }


    @Override
    public void onListItemClick(final ListView l, View v, final int position, long id) {
        /*v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this country?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                l.removeView(v);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                return false;
            }
        });*/
        getListView().setSelector(android.R.color.holo_blue_dark);
        listener.onCountryChange(position, this.adapter.getItem(position));
    }

    public interface CountryselectList{
        public void onCountryChange(int position, Country country);
        public int getCurrentSelection();
        public void setInitCountry(Country country);
    }
}