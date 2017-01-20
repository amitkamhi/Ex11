package com.example.kamhi.ex11.View;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.R;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class DetailsFragment extends Fragment {

    TextView tVDetails;
    CountryReporter listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            this.listener = (CountryReporter) getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'DataReporter");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tVDetails = (TextView)view.findViewById(R.id.details);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_frag, container, false);
    }

    public void changeTo(Country newCountry){
        this.tVDetails.setText(newCountry.toString());
    }

    public static interface CountryReporter{
        public Country getCountryData();
    }

}
