package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.greg.mareu.R;
import com.greg.mareu.picker.Pick;

import butterknife.BindView;

public class DateDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialogStartDateEdit) EditText mStartEdit;
    @BindView(R.id.dialogEndDateEdit) EditText mEndEdit;
    private Pick mPick;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_date, null);

        mStartEdit = view.findViewById(R.id.dialogStartDateEdit);
        mEndEdit = view.findViewById(R.id.dialogEndDateEdit);

        mStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pick.pickDate(mStartEdit, getActivity());
            }
        });

        mEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick.pickDate(mEndEdit, getActivity());
            }
        });

        builder.setTitle("Choix des dates")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String start = mStartEdit.getText().toString().trim();
                        String end = mEndEdit.getText().toString().trim();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(view);
        return builder.create();
    }

    public String selectedStartHour(){
        String start = mStartEdit.getText().toString().trim();
        return start;
    }

    public String selectedEndHour(){
        String end = mEndEdit.getText().toString().trim();
        return end;
    }
}
