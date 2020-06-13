package com.donor.needyturtle.utils;

import android.content.Context;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;

public class Validations {

    private Context context;

    public Validations(Context context) {
        this.context = context;
    }


    public boolean validateEditText(EditText editText, String msg){

        String text = editText.getText().toString().trim();

        if (text.length() == 0){
            TastyToast.makeText(context, msg,
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            editText.setError(msg);
            editText.requestFocus();

            return false;
        }

        return true;
    }

    public boolean validateText(String txt, String msg){

        if (txt.trim().length() == 0){
            TastyToast.makeText(context, msg,
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return false;
        }

        return true;
    }





}
