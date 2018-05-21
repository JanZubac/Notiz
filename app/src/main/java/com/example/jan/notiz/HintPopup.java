package com.example.jan.notiz;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.content.DialogInterface;
        import android.media.MediaPlayer;
        import android.os.Bundle;

public class HintPopup extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Hint: You can clear the notification list by shaking the phone");
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

