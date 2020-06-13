package com.donor.needyturtle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.donor.needyturtle.R;
import com.donor.needyturtle.network.ApiConstant;
import com.donor.needyturtle.network.PostDataParser;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

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

        edt_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 6){
                    pincodeValidation(s.toString());
                }

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

        searchDonor();
    }

    private String Districtname = "", statename = "", Taluk = "";
    public void pincodeValidation(String pincode) {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("pincode", pincode);
        params.put("type", ApiConstant.pincode_validation);

        new PostDataParser(SearchActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                int is_valid = response.optInt("is_valid");
                                if (is_valid == 1) {

                                    Districtname = response.optString("Districtname");
                                    statename = response.optString("statename");
                                    Taluk = response.optString("Taluk");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void searchDonor() {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("search", blood_group);
        params.put("pincode", edt_pincode.getText().toString());
        params.put("states", statename);
        params.put("district", Districtname);
        params.put("city", Taluk);
        params.put("contact", edt_contact_no.getText().toString());
        params.put("donor_search_reason", edt_why.getText().toString());
        params.put("type", ApiConstant.search_donor);

        new PostDataParser(SearchActivity.this, url, params, true,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                String search_id = response.optString("search_id");
                                JSONArray search_result = response.getJSONArray("search_result");

                                if (search_result.length() > 0){
                                    Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                                    intent.putExtra("array", search_result.toString());
                                    intent.putExtra("blood_group", blood_group);
                                    startActivity(intent);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }
}