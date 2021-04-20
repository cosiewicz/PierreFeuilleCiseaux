package com.example.pierrefeuilleciseaux;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;


/*
 Show Box with Name input
 */
public class AlertBox {

    private String value;
    private String title;
    private AlertBox.Listener listener;

    public AlertBox(Context context, String title) {
        String txt = "";
        this.title = title;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                value = input.getText().toString();
                listener.onValidate(value);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /*
    Listener on press ok button
     */
    public interface Listener {
        void onValidate(String value);
    }
}
