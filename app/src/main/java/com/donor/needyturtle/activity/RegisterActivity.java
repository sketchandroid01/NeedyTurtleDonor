package com.donor.needyturtle.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.donor.needyturtle.R;
import com.donor.needyturtle.utils.Commons;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_name, edt_contact, edt_alternate_contact, edt_email,
            edt_pincode, edt_city, edt_district, edt_states, edt_current_address,
            edt_dob, edt_years, edt_height, edt_weight, edt_any_disease, edt_id_number;
    private Spinner spinner_blood_group, spinner_gender, spinner_id_type;
    private RadioGroup radioGroup;
    private TextView tv_pincode_valid_text, tv_no_disease_text, tv_terms_condition;
    private CheckBox checkbox_no_disease, checkbox_terms;
    private LinearLayout linear_checkbox;
    private ImageView image;
    private Button btn_choose_pic, btn_register;


    private File p_image;
    private final int PICK_IMAGE_CAMERA = 11, PICK_IMAGE_GALLERY = 22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        iniViews();



    }

    private void iniViews(){

        edt_name = findViewById(R.id.edt_name);
        edt_contact = findViewById(R.id.edt_contact);
        edt_alternate_contact = findViewById(R.id.edt_alternate_contact);
        edt_email = findViewById(R.id.edt_email);
        edt_pincode = findViewById(R.id.edt_pincode);
        edt_city = findViewById(R.id.edt_city);
        edt_district = findViewById(R.id.edt_district);
        edt_states = findViewById(R.id.edt_states);
        edt_current_address = findViewById(R.id.edt_current_address);
        edt_dob = findViewById(R.id.edt_dob);
        edt_years = findViewById(R.id.edt_years);
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

        checkbox_no_disease = findViewById(R.id.checkbox_no_disease);
        checkbox_terms = findViewById(R.id.checkbox_terms);

        linear_checkbox = findViewById(R.id.linear_checkbox);
        image = findViewById(R.id.image);
        btn_choose_pic = findViewById(R.id.btn_choose_pic);
        btn_register = findViewById(R.id.btn_register);



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




        btn_choose_pic.setOnClickListener(v -> {
            if (checkPermission()){
                dialogSelect();
            }
        });


    }

    private void checkValidation(){

        if (edt_name.getText().toString().trim().length() == 0){
            TastyToast.makeText(getApplicationContext(),
                    "Please enter your name",
                    TastyToast.LENGTH_SHORT, TastyToast.INFO);
            return;
        }




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
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {

            try {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                writeBitmap(bitmap, image);
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




}