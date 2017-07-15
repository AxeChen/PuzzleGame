package com.mg.axe.puzzlegame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Axe on 2017/7/15.
 */

public class SuccessDialog extends DialogFragment {

    public OnButtonClickListener buttonClickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("成功！");
        builder.setMessage("你已经完成拼图，是否挑战下一个等级？").
                setPositiveButton("下一等级", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(buttonClickListener!=null){
                            buttonClickListener.nextLevelClick();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(buttonClickListener!=null){
                            buttonClickListener.cancelClick();
                        }
                    }
                });

        return builder.create();

    }

    public void addButtonClickListener(OnButtonClickListener listener){
        this.buttonClickListener = listener;
    }

    public interface OnButtonClickListener {
        public void nextLevelClick();

        public void cancelClick();
    }
}
