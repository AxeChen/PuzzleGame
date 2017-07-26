package com.mg.axe.puzzlegame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mg.axe.puzzlegame.R;

/**
 * Created by Axe on 2017/7/15.
 */

public class SuccessDialog extends DialogFragment {

    public OnButtonClickListener buttonClickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.success));
        builder.setMessage(getString(R.string.success_description)).
                setPositiveButton(getString(R.string.next_level), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (buttonClickListener != null) {
                            buttonClickListener.nextLevelClick();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (buttonClickListener != null) {
                            buttonClickListener.cancelClick();
                        }
                    }
                });

        return builder.create();

    }

    public void addButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public interface OnButtonClickListener {
        public void nextLevelClick();

        public void cancelClick();
    }
}
