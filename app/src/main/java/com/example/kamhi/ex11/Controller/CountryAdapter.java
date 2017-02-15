package com.example.kamhi.ex11.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kamhi.ex11.Model.Country;
import com.example.kamhi.ex11.Model.DataLoader;
import com.example.kamhi.ex11.R;

import java.util.ArrayList;

/**
 * Created by Kamhi on 31/1/2017.
 */
public class CountryAdapter extends ArrayAdapter<Country> {
    ArrayList<Country> countries;
    Context context;
    public CountryAdapter(Context context) {
        super(context, R.layout.row_item);
        this.context = context;
        countries = new ArrayList<>(DataLoader.getCountries());
        this.addAll(countries);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item,parent,false);
        }
        TextView tvName = (TextView)convertView.findViewById(R.id.name);
        TextView tvShort = (TextView)convertView.findViewById(R.id.shortDetails);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.flag);
        Country country = getItem(position);
        tvName.setText(country.getName());
        tvShort.setText(country.getShortDetails());
        int imageResource = context.getResources().getIdentifier(country.getFlag(), "drawable",parent.getContext().getPackageName());
        imageView.setImageResource(imageResource);
        return convertView;
    }
    public ArrayList<String> getMissingCountries(ArrayList<String> countries)
    {
        ArrayList<String> missing = new ArrayList<>();
        for (Country country:this.countries)
        {
            if(!countries.contains(country.getName()))
            {
                missing.add(country.getName());
            }
        }
        return missing;
    }

}
