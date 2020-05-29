package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.greg.mareu.R;

import butterknife.BindView;

public class RoomListDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialogRoomSpinner) Spinner mSpinnerDialog;
    private String mRoom;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_room_spinner, null);
        mSpinnerDialog = view.findViewById(R.id.dialogRoomSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.room_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDialog.setAdapter(adapter);

        mSpinnerDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoom = parent.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBuilder.setTitle("Choix de la salle")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String room = mSpinnerDialog.getSelectedItem().toString();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setView(view);
        return mBuilder.create();
    }

    public String getSelectedRoom()
    {
        //String pickedRoom = mSpinnerDialog.getSelectedItem().toString().trim();
        return mRoom;
    }
}
