package com.example.jgreenb2.mymondrian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class InfoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message).
                setPositiveButton(R.string.visit_moma,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // set an intent and visit
                        Log.i(MainActivity.TAG, "visit moma");
                        // create URI intent and show the MOMA web page
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
                        startActivity(intent);
                    }
                }).
                setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // don't do it
                        Log.i(MainActivity.TAG, "don't visit moma");
                    }
                });


        return builder.create();
    }
}
