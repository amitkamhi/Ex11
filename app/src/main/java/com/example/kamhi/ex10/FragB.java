package com.example.kamhi.ex10;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class FragB extends Fragment {

    TextView tVCounter;
    DataReporter listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            this.listener = (DataReporter)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'DataReporter");
        }
        super.onActivityCreated(savedInstanceState);
        tVCounter.setText(Integer.toString(this.listener.getData()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.tVCounter = (TextView)view.findViewById(R.id.counter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_b, container, false);
    }

    public void onCounterChange(int newCounter){
        this.tVCounter.setText(Integer.toString(newCounter));
    }

    public interface DataReporter{
        public int getData();
    }

}
