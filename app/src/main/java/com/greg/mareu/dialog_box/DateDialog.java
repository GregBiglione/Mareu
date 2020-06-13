package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.greg.mareu.R;
import com.greg.mareu.events.FilterByDateEvent;
import com.greg.mareu.picker.Pick;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class DateDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialogStartDateEdit) EditText mStartEdit;
    @BindView(R.id.dialogEndDateEdit) EditText mEndEdit;
    @BindView(R.id.okDateDialog) Button mOkDate;
    @BindView(R.id.cancelDateDialog) Button mCancelDate;
    private Pick mPick;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_date, null);

        mStartEdit = view.findViewById(R.id.dialogStartDateEdit);
        mEndEdit = view.findViewById(R.id.dialogEndDateEdit);
        mOkDate = view.findViewById(R.id.okDateDialog);
        mCancelDate = view.findViewById(R.id.cancelDateDialog);

        mStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Pick.pickDate(mStartEdit, getActivity()); }
        });

        mEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pick.pickDate(mEndEdit, getActivity());
            }
        });

        builder.setTitle("Choix des dates");

        mOkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date start = simpleDateFormat.parse(mStartEdit.getText().toString().trim());
                    Date end = simpleDateFormat.parse(mEndEdit.getText().toString().trim());
                    EventBus.getDefault().post(new FilterByDateEvent(start, end));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });
        mCancelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);

        return builder.create();
    }
}
