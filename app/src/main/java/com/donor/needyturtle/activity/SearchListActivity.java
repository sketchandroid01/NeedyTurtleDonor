package com.donor.needyturtle.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donor.needyturtle.R;
import com.donor.needyturtle.adapter.SearchAdapter;
import com.donor.needyturtle.utils.DataModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_view;
    ArrayList<DataModel> modelArrayList;
    String blood_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        toolbar = findViewById(R.id.toolbar);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donor List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        modelArrayList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String array = bundle.getString("array");
            blood_group = bundle.getString("blood_group");

            try {
                JSONArray jsonArray = new JSONArray(array);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);

                    DataModel dataModel = new DataModel();
                    dataModel.setId(object.optString("id"));
                    dataModel.setAge(object.optString("age"));
                    dataModel.setCurrent_address(object.optString("current_address"));
                    dataModel.setDieases(object.optString("dieases"));

                    modelArrayList.add(dataModel);

                }

                SearchAdapter searchAdapter = new SearchAdapter(modelArrayList,
                        SearchListActivity.this, blood_group);
                recycler_view.setAdapter(searchAdapter);


            }catch (Exception e){
                e.printStackTrace();
            }

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


}