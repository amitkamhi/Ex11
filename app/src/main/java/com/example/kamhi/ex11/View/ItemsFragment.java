package com.example.kamhi.ex11.View;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.R;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class ItemsFragment extends ListFragment{

    CountryselectList listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            this.listener = (CountryselectList) getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'ClickHandler");
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.items_frag, container, false);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        getListView().setSelector(android.R.color.holo_blue_dark);
    }



    public interface CountryselectList{
        public void onCountryChange(int position, Country country);
        public int getCurrentSelection();
        public void setInitCountry(Country country);
    }
}
