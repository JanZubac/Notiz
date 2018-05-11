package com.example.jan.notiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

public class NotificationPopup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Have you completed the task in your notification?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.applause);
                        mp.start();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.groan);
                        mp.start();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
