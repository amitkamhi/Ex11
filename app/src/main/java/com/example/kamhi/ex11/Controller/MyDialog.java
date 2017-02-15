package com.example.kamhi.ex11.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kamhi.ex11.R;

/**
 * Created by Kamhi on 16/12/2016.
 */

public class MyDialog extends DialogFragment {

    private int requestCode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return buildDialog().create();
    }

    private AlertDialog.Builder buildDialog(){

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete)
                .setMessage(R.string.delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFinishedDialog();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
    }

    private DialogListener listener;

    public interface DialogListener{
        public String onFinishedDialog();
    }
}
