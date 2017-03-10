package com.example.kamhi.ex11.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kamhi.ex11.R;

/**
 * Created by Kamhi on 16/12/2016.
 */

public class MyDialog extends DialogFragment {

    private int requestCode;
    final public static int EXIT = 3;
    final public static int ADD = 2;
    private ResultsListener listener;
    final String[] selected=new String[1];
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        this.requestCode = getArguments().getInt("requestCode");
        if(this.requestCode == ADD){
            return buildSpinnerDialog().create();
        }
        else
        {
            return buildsExitDialog().create();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        try
        {
            if (getParentFragment()!=null)
                this.listener = (ResultsListener) getParentFragment();
            else
                listener=(ResultsListener) activity;
        }catch(ClassCastException e)
        {

            throw new ClassCastException("this fragment host must implements ResultsListner");
        }
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }
    public static MyDialog newInstance(int requestCode) {
        Bundle args = new Bundle();
        MyDialog fragment = new MyDialog();
        args.putInt("requestCode",requestCode);
        fragment.setArguments(args);
        return fragment;
    }

    /*private AlertDialog.Builder buildExitDialog() {
       Log.d("ex11", "MyDialog buildExitDialog");
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.closing)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode, "ok");

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }*/

    private AlertDialog.Builder buildsExitDialog(){
        return new AlertDialog.Builder(getActivity())
                .setTitle("Closing the application")
                .setMessage("Are you sure")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode, "ok");
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }

    private AlertDialog.Builder buildSpinnerDialog(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout1, null);
        Spinner countriesList=(Spinner)view.findViewById(R.id.spinner);
        ItemsFragment ifrag=(ItemsFragment)getParentFragment();
        countriesList.setAdapter(
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        ifrag.getMissingCountry())
        );
        countriesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected[0]=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle("add")
                .setView(view)
                .setPositiveButton("ok" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnfinishDialog(requestCode,selected[0]);

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }

    public interface ResultsListener {
        public void OnfinishDialog(int requestCode, Object result);
    }
}