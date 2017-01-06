package com.example.kamhi.ex10;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity implements FragA.ClickHandler, FragB.DataReporter{

    private int counter = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            counter = savedInstanceState.getInt("counter");
        }

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            FragmentManager fm = getFragmentManager();
            if(savedInstanceState != null && fm.findFragmentByTag("AAA")!=null)
            {
                return;
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragContainer, new FragA(), "AAA").commit();
        }
    }

    @Override
    public void onClickEvent() {
        this.counter++;
        FragB fragB;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            fragB = (FragB) getFragmentManager().findFragmentById(R.id.fragmentB);
        }
        else{
            fragB = new FragB();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragContainer, fragB, "BBB")
                    .addToBackStack("BBB")
                    .commit();
            fm.executePendingTransactions();
        }
        fragB.onCounterChange(this.counter);
    }

    @Override
    public int getData() {
        return this.counter;
    }
}
