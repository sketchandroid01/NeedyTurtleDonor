package com.donor.needyturtle.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.donor.needyturtle.R;
import com.donor.needyturtle.utils.BannerAdapter;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_name, edt_contact, edt_alternate_contact, edt_email,
            edt_pincode, edt_city, edt_district, edt_states, edt_current_address,
            edt_dob, edt_years, edt_height, edt_weight;
    private Spinner spinner_blood_group, spinner_gender;
    private RadioGroup radioGroup;
    private TextView tv_pincode_valid_text, tv_no_disease_text;
    private CheckBox checkbox_no_disease;
    private LinearLayout linear_checkbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




    }


}