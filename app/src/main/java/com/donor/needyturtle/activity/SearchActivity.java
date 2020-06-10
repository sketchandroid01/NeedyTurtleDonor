package com.donor.needyturtle.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.donor.needyturtle.R;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinner_blood_group;
    EditText edt_pincode, edt_contact_no, edt_why;
    Button btn_search;

    String blood_group = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search for donor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews(){
        toolbar = findViewById(R.id.toolbar);
        spinner_blood_group = findViewById(R.id.spinner_blood_group);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_contact_no = findViewById(R.id.edt_contact_no);
        edt_why = findViewById(R.id.edt_why);
        btn_search = findViewById(R.id.btn_search);


        String[] array_1 = getResources().getStringArray(R.array.blood_group);
        List<String> array_blood_group = new ArrayList<String>(Arrays.asList(array_1));
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, array_blood_group);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(spinnerArrayAdapter1);

        spinner_blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0){
                    blood_group = array_blood_group.get(position);
                }else {
                    blood_group = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_search.setOnClickListener(v -> {
            validateEntries();
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void validateEntries(){

        if (blood_group.isEmpty()){
            TastyToast.makeText(getApplicationContext(),
                    "Please select blood group",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }

        if (edt_pincode.getText().toString().trim().isEmpty()){
            TastyToast.makeText(getApplicationContext(),
                    "Please enter your area pincode",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }


        if (edt_contact_no.getText().toString().trim().isEmpty()){
            TastyToast.makeText(getApplicationContext(),
                    "Please enter your contact number",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }
        if (edt_why.getText().toString().trim().isEmpty()){
            TastyToast.makeText(getApplicationContext(),
                    "Please enter your contact number",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }


    }

}