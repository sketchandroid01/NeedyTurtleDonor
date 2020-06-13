package com.donor.needyturtle.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.donor.needyturtle.R;
import com.donor.needyturtle.network.ApiConstant;
import com.donor.needyturtle.network.PostDataParser;
import com.donor.needyturtle.utils.Commons;
import com.donor.needyturtle.utils.Util;
import com.donor.needyturtle.utils.Validations;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText edt_name, edt_contact, edt_alternate_contact, edt_email,
            edt_pincode, edt_city, edt_district, edt_states, edt_current_address,
            edt_age, edt_height, edt_weight, edt_any_disease, edt_id_number;
    private Spinner spinner_blood_group, spinner_gender, spinner_id_type;
    private RadioGroup radioGroup;
    private TextView tv_pincode_valid_text, tv_no_disease_text, tv_terms_condition,
            tv_dob, tv_verify_mobile, tv_verify_email;
    private CheckBox checkbox_no_disease, checkbox_terms;
    private LinearLayout linear_checkbox;
    private ImageView image;
    private Button btn_choose_pic, btn_register;


    private File p_image;
    private final int PICK_IMAGE_CAMERA = 11, PICK_IMAGE_GALLERY = 22;
    private String blood_group = "", gender = "", id_type = "", is_contact_visible = "";
    private Validations validations;
    private ArrayList<String> listDisease;
    private String pic_upload_id = "";
    boolean is_mobile_verified = false;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniViews();



    }

    private void iniViews(){

        validations = new Validations(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        edt_name = findViewById(R.id.edt_name);
        edt_contact = findViewById(R.id.edt_contact);
        edt_alternate_contact = findViewById(R.id.edt_alternate_contact);
        edt_email = findViewById(R.id.edt_email);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_city = findViewById(R.id.edt_city);
        edt_district = findViewById(R.id.edt_district);
        edt_states = findViewById(R.id.edt_states);
        edt_current_address = findViewById(R.id.edt_current_address);
        tv_dob = findViewById(R.id.tv_dob);
        edt_age = findViewById(R.id.edt_age);
        edt_height = findViewById(R.id.edt_height);
        edt_weight = findViewById(R.id.edt_weight);
        edt_any_disease = findViewById(R.id.edt_any_disease);
        edt_id_number = findViewById(R.id.edt_id_number);

        spinner_blood_group = findViewById(R.id.spinner_blood_group);
        spinner_gender = findViewById(R.id.spinner_gender);
        spinner_id_type = findViewById(R.id.spinner_id_type);
        radioGroup = findViewById(R.id.radioGroup);

        tv_pincode_valid_text = findViewById(R.id.tv_pincode_valid_text);
        tv_no_disease_text = findViewById(R.id.tv_no_disease_text);
        tv_terms_condition = findViewById(R.id.tv_terms_condition);
        tv_verify_mobile = findViewById(R.id.tv_verify_mobile);
        tv_verify_email = findViewById(R.id.tv_verify_email);

        checkbox_no_disease = findViewById(R.id.checkbox_no_disease);
        checkbox_terms = findViewById(R.id.checkbox_terms);

        linear_checkbox = findViewById(R.id.linear_checkbox);
        image = findViewById(R.id.image);
        btn_choose_pic = findViewById(R.id.btn_choose_pic);
        btn_register = findViewById(R.id.btn_register);

        edt_city.setEnabled(false);
        edt_district.setEnabled(false);
        edt_states.setEnabled(false);
        edt_age.setEnabled(false);
        tv_pincode_valid_text.setVisibility(View.GONE);
        tv_no_disease_text.setVisibility(View.GONE);

        listDisease = new ArrayList<>();

        setSpinnerData();

        viewClick();



    }

    private void viewClick(){

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

        edt_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 10){
                    phoneNumberValidation(s.toString());
                }

            }
        });

        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0 && Commons.isValidEmail(s.toString())){
                    emailValidation(s.toString());
                }

            }
        });

        tv_dob.setOnClickListener(v -> {

            new DatePickerDialog(RegisterActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        btn_choose_pic.setOnClickListener(v -> {
            if (checkPermission()){
                dialogSelect();
            }
        });

        tv_terms_condition.setOnClickListener(v -> {
            String url = "https://donor.needyturtle.com/terms.php";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        });


        is_contact_visible = "Yes";
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.radio_yes){
                is_contact_visible = "Yes";
            }else if (checkedId == R.id.radio_no){
                is_contact_visible = "No";
            }
        });

        btn_register.setOnClickListener(v -> {
            checkValidation();
        });
        tv_verify_mobile.setOnClickListener(v -> {
            if (!validations.validateEditText(edt_contact, "Please enter your contact no")){
                return;
            }
            dialogVerifyOtp("mobile");
        });
        tv_verify_email.setOnClickListener(v -> {
            if (!validations.validateEditText(edt_email, "Please enter your email")){
                return;
            }
            dialogVerifyOtp("email");
        });



    }

    private void setSpinnerData(){

        String[] array_1 = getResources().getStringArray(R.array.blood_group);
        String[] array_2r = getResources().getStringArray(R.array.gender);
        String[] array_3 = getResources().getStringArray(R.array.disease_list);
        String[] array_4 = getResources().getStringArray(R.array.id_array_list);

        ArrayList<String> array_blood_group = new ArrayList<String>(Arrays.asList(array_1));
        ArrayList<String> array_gender = new ArrayList<String>(Arrays.asList(array_2r));
        ArrayList<String> array_disease_list = new ArrayList<String>(Arrays.asList(array_3));
        ArrayList<String> array_ids = new ArrayList<String>(Arrays.asList(array_4));


        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, array_blood_group);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_blood_group.setAdapter(spinnerArrayAdapter1);


        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, array_gender);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(spinnerArrayAdapter2);

        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, array_ids);
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_id_type.setAdapter(spinnerArrayAdapter3);

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

        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    gender = array_gender.get(position);
                }else {
                    gender = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_id_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    id_type = array_ids.get(position);
                }else {
                    id_type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkbox_no_disease.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                if (listDisease.size() > 0){
                    linear_checkbox.removeAllViews();
                    for (int i = 0; i < array_disease_list.size(); i++){
                        linear_checkbox.addView(getCheckView(array_disease_list.get(i)));
                    }
                }

                listDisease.clear();
                tv_no_disease_text.setVisibility(View.VISIBLE);
            }else {
                tv_no_disease_text.setVisibility(View.GONE);
            }

        });

        for (int i = 0; i < array_disease_list.size(); i++){
            linear_checkbox.addView(getCheckView(array_disease_list.get(i)));
        }

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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            tv_dob.setText(sdf.format(myCalendar.getTime()));

            edt_age.setText(Util.getAge(year, monthOfYear, dayOfMonth));
        }

    };


    private void checkValidation(){

        if (!validations.validateEditText(edt_name, "Please enter your name")){
            return;
        }
        if (!validations.validateText(blood_group, "Please select blood group")){
            return;
        }
        if (!validations.validateEditText(edt_contact, "Please enter your contact no")){
            return;
        }
        if (!validations.validateEditText(edt_pincode, "Please enter your area pincode")){
            return;
        }
        if (!validations.validateEditText(edt_current_address, "Please enter your address")){
            return;
        }
        if (!validations.validateText(tv_dob.getText().toString(), "Please enter DOB")){
            return;
        }
        if (!validations.validateText(gender, "Please select your gender")){
            return;
        }
        if (!checkbox_no_disease.isChecked() && listDisease.size() == 0){
            TastyToast.makeText(getApplicationContext(), "Please select your disease",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }
        if (!validations.validateText(id_type, "Please select your ID type")){
            return;
        }
        if (!validations.validateEditText(edt_id_number, "Please enter your area ID's number")){
            return;
        }
        if (!validations.validateText(pic_upload_id, "Please upload your ID's image")){
            return;
        }

        if (!checkbox_terms.isChecked()){
            TastyToast.makeText(getApplicationContext(), "Please agree our terms & conditions",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }

        if (!is_mobile_verified){
            TastyToast.makeText(getApplicationContext(), "Please verify your mobile",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }



        register_donor();

    }

    //// choose image ..

    private boolean checkPermission() {

        List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) RegisterActivity.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    121);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 121:
                if (permissions.length == 1 && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED
                        ||
                        (permissions.length == 2 && grantResults[0]
                                == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)){

                }

        }
    }

    private void dialogSelect() {

        String[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setCancelable(false);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (which == 1) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (which == 2) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        p_image = null;
        /// profile ...
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                writeBitmap(bitmap, image);
                uploadPic();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {

            try {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                writeBitmap(bitmap, image);
                uploadPic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void writeBitmap(Bitmap bitmap, ImageView imageView){

        bitmap = Commons.getResizedBitmap(bitmap, 480, 520);

        final String dir = Commons.getFolderDirectoryImage();

        File file = new File(dir);
        if (!file.exists())
            file.mkdir();


        String files = dir + "/P_"+System.currentTimeMillis() +".jpg";
        File newfile = new File(files);

        try {

            imageView.setImageBitmap(bitmap);

            newfile.delete();
            OutputStream outFile = null;
            try {

                p_image = newfile;

                Log.d(Commons.TAG, "file = "+p_image);

                outFile = new FileOutputStream(newfile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outFile);
                outFile.flush();
                outFile.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public View getCheckView(String st) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_checkbox, null, false);
        CheckBox checkBox = v.findViewById(R.id.checkbox2);
        checkBox.setText(st);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                listDisease.add(checkBox.getText().toString());
            }else {
                listDisease.remove(checkBox.getText().toString());
            }

            if (listDisease.size() > 0){
                checkbox_no_disease.setChecked(false);
            }

        });


        return v;
    }


    private void dialogVerifyOtp(String type){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_verify_mobile, null);
        dialogBuilder.setView(dialogView);

        TextView tv_phn_number = dialogView.findViewById(R.id.tv_phn_number);
        EditText edt_otp = dialogView.findViewById(R.id.edt_otp);
        TextView tv_msg = dialogView.findViewById(R.id.tv_msg);
        TextView tv_resend_otp = dialogView.findViewById(R.id.tv_resend_otp);
        Button btn_verify = dialogView.findViewById(R.id.btn_verify);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        if (type.equals("mobile")){
            tv_phn_number.setText("Otp send to your mobile number");
            sendOtp(edt_contact.getText().toString(), type, tv_msg);
        }else {
            tv_phn_number.setText("Otp send to your email");
            sendOtp(edt_email.getText().toString(), type, tv_msg);
        }

        tv_resend_otp.setOnClickListener(v -> {
            reSendOtp(tv_msg);
        });

        btn_verify.setOnClickListener(v -> {
            if (edt_otp.getText().toString().trim().length() == 0){
                TastyToast.makeText(getApplicationContext(),
                        "Please enter OTP",
                        TastyToast.LENGTH_SHORT, TastyToast.INFO);
                return;
            }

            verify_otp(edt_otp.getText().toString(), alertDialog);

        });

    }


    ////////////// api call ...

    public void pincodeValidation(String pincode) {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("pincode", pincode);
        params.put("type", ApiConstant.pincode_validation);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                int is_valid = response.optInt("is_valid");
                                if (is_valid == 1) {
                                    tv_pincode_valid_text.setVisibility(View.GONE);

                                    String Districtname = response.optString("Districtname");
                                    String statename = response.optString("statename");
                                    String Taluk = response.optString("Taluk");

                                    edt_city.setText(Taluk);
                                    edt_district.setText(Districtname);
                                    edt_states.setText(statename);

                                } else {
                                    tv_pincode_valid_text.setVisibility(View.VISIBLE);

                                    edt_city.setText("");
                                    edt_district.setText("");
                                    edt_states.setText("");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void phoneNumberValidation(String phonenumber) {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("phonenumber", phonenumber);
        params.put("type", ApiConstant.phone_existance);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                int is_used = response.optInt("is_used");
                                if (is_used == 1) {
                                    edt_contact.setError("Mobile number already exits");
                                } else {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void emailValidation(String email_address) {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("email_address", email_address);
        params.put("type", ApiConstant.email_existance);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                int is_used = response.optInt("is_used");
                                if (is_used == 1) {
                                    edt_email.setError("Email already exits");
                                } else {

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    String otp_id = "";
    public void sendOtp(String email_mobile, String type, TextView tv_msg) {

        String url = ApiConstant.BASE_URL;
        tv_msg.setText("");
        otp_id = "";

        HashMap<String, String> params = new HashMap<>();
        params.put("type_value", email_mobile);
        params.put("otp_for", type);
        params.put("type", ApiConstant.generate_otp);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {

                                String otp = response.optString("otp");
                                otp_id = response.optString("otp_id");
                                String otp_message = response.optString("otp_message");

                                tv_msg.setText(otp_message);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void reSendOtp(TextView tv_msg) {

        String url = ApiConstant.BASE_URL;
        tv_msg.setText("");

        HashMap<String, String> params = new HashMap<>();
        params.put("otp_id", otp_id);
        params.put("type", ApiConstant.resend_otp);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {

                                String resend_otp_status = response.optString("resend_otp_status");
                                if (resend_otp_status.equals("1")){
                                    tv_msg.setText("Otp send");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void verify_otp(String otp, AlertDialog alertDialog) {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("otp_id", otp_id);
        params.put("otp", otp);
        params.put("type", ApiConstant.verify_otp);

        new PostDataParser(RegisterActivity.this, url, params, false,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {

                                String otv_verified = response.optString("otv_verified");
                                if (otv_verified.equals("1")){
                                    alertDialog.dismiss();
                                    is_mobile_verified = true;
                                }else {
                                    is_mobile_verified = false;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void register_donor() {

        String url = ApiConstant.BASE_URL;

        HashMap<String, String> params = new HashMap<>();
        params.put("type", ApiConstant.register_donor);

        params.put("fullname", edt_name.getText().toString());
        params.put("blood_group", blood_group);
        params.put("primary_contact", edt_contact.getText().toString());
        params.put("alternate_contact", edt_alternate_contact.getText().toString());
        params.put("email_address", edt_email.getText().toString());
        params.put("is_contact_visible", is_contact_visible);
        params.put("pincode", edt_pincode.getText().toString());
        params.put("city", edt_city.getText().toString());
        params.put("district", edt_district.getText().toString());
        params.put("states", edt_states.getText().toString());
        params.put("current_address", edt_current_address.getText().toString());
        params.put("dateofbirth", tv_dob.getText().toString());
        params.put("age", edt_age.getText().toString());
        params.put("sex", gender);
        params.put("height", edt_height.getText().toString());
        params.put("weight", edt_weight.getText().toString());

        params.put("id_type", id_type);
        params.put("id_number", edt_id_number.getText().toString());
        params.put("id_picture", pic_upload_id);

        for (int i = 0; i < listDisease.size(); i++){
            params.put("dieases["+i+"]", listDisease.get(i));
        }


        new PostDataParser(RegisterActivity.this, url, params, true,
                new PostDataParser.OnGetResponseListner() {
                    @Override
                    public void onGetResponse(JSONObject response) {
                        if (response != null) {

                            try {
                                String register_id = response.optString("register_id");
                                String fullname = response.optString("fullname");

                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    public void uploadPic(){

        ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String url = ApiConstant.BASE_URL;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("type", ApiConstant.id_upload);

        try{
            if (p_image != null){
                params.put("picture", p_image);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        Log.d(Commons.TAG , "URL "+url);
        Log.d(Commons.TAG , "params "+params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    Log.d(Commons.TAG, "add_item- " + response.toString());
                    try {

                        progressDialog.dismiss();
                        pic_upload_id = response.optString("asset_id");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });


    }


}