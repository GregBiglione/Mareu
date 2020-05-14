package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.greg.mareu.model.Reunion;

public class ParticipantsListDialog extends AppCompatDialogFragment {

    Reunion r;
    public ParticipantsListDialog(Reunion reunion) {
        this.r = reunion;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //String participants = getIntent().getStringExtra

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Liste des participants à la réunion sur " + r.getAboutReunion())
                .setMessage(r.getParticipants())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
