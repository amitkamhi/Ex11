package com.example.kamhi.ex11;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kamhi on 3/1/2017.
 */

public class FragA extends Fragment implements View.OnClickListener{

    ClickHandler listener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            this.listener = (ClickHandler)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException("The class " + getActivity().getClass().getName() + " must implements the interfase 'ClickHandler");
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.poshMe).setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_a, container, false);
    }

    @Override
    public void onClick(View v) {
        this.listener.onClickEvent();
    }


    public interface ClickHandler{
        public void onClickEvent();
    }
}
