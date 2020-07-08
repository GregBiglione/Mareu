package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.events.SetParticipantEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

public class ParticipantsCheckBox extends AppCompatDialogFragment {

    @BindView(R.id.addParticipantsLyt) TextInputLayout mParticipantsInput;
    @BindView(R.id.addParticipantsEdit) TextInputEditText mParticipantsEditText;
    String[] listOfParticipants;
    boolean[] checkedParticipants;
    ArrayList<Integer> mUserParticipants = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        listOfParticipants = getResources().getStringArray(R.array.participants_array);
        checkedParticipants = new boolean[listOfParticipants.length];
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_reunion, null);

        mParticipantsEditText = view.findViewById(R.id.addParticipantsEdit);

        mBuilder.setTitle("Liste des participants");
        mBuilder.setMultiChoiceItems(listOfParticipants, checkedParticipants, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked)
            {
                if (isChecked){
                    if (!mUserParticipants.contains(position)) {
                        mUserParticipants.add(position);
                    }
                }
                else if(mUserParticipants.contains(position)) {
                    mUserParticipants.remove(mUserParticipants.indexOf(position));
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = "";
                for (int i = 0; i< mUserParticipants.size(); i++) {
                    item = item + listOfParticipants[mUserParticipants.get(i)];
                    if (i != mUserParticipants.size() - 1) {
                        item = item + ", ";
                    }
                }
                EventBus.getDefault().post(new SetParticipantEvent(item));
            }
        });
        mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mBuilder.setNeutralButton("Tout effacer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i =0; i < checkedParticipants.length; i++) {
                    checkedParticipants[i] = false;
                    mUserParticipants.clear();
                    EventBus.getDefault().post(new SetParticipantEvent(" "));
                }
            }
        });
        AlertDialog mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        dismiss();
        return mBuilder.create();
    }
}
