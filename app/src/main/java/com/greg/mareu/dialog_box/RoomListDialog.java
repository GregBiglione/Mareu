package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.greg.mareu.R;
import com.greg.mareu.events.FilterByRoomEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class RoomListDialog extends AppCompatDialogFragment {

    @BindView(R.id.dialogRoomSpinner) Spinner mSpinnerDialog;
    @BindView(R.id.okRoomDialog) Button mOkRoom;
    @BindView(R.id.cancelRoomDialog) Button mCancelRoom;

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mOkRoom = view.findViewById(R.id.okRoomDialog);
        mCancelRoom = view.findViewById(R.id.cancelRoomDialog);

        mBuilder.setTitle("Choix de la salle");
        mOkRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pickedRoom = mSpinnerDialog.getSelectedItem().toString().trim();
                EventBus.getDefault().post(new FilterByRoomEvent(pickedRoom));
                dismiss();
            }
        });
        mCancelRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBuilder.setView(view);
        return mBuilder.create();
    }
}
