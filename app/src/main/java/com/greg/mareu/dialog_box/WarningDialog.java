package com.greg.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.greg.mareu.R;

import butterknife.BindView;

public class WarningDialog extends AppCompatDialogFragment {

    @BindView(R.id.okWarningDialog) Button mOkWarning;
    @BindView(R.id.warningLogo) ImageView mWarningLogo;
    @BindView(R.id.warningTitle) TextView mWarningTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_warning, null);
        builder.setView(view);

        mWarningLogo = view.findViewById(R.id.warningLogo);
        mWarningTitle = view.findViewById(R.id.warningTitle);

        mOkWarning = view.findViewById(R.id.okWarningDialog);
        mOkWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }
}
