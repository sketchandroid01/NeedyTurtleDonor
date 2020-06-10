package com.donor.needyturtle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.donor.needyturtle.R;
import com.donor.needyturtle.utils.DataModel;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private ArrayList<DataModel> wish_list;
    private Context context;

    public SearchAdapter(ArrayList<DataModel> wish_list, Context context){
        this.wish_list= wish_list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_listitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return wish_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_blood_group, tv_age, tv_location, tv_disease;

        public MyViewHolder(View view) {
            super(view);
            tv_blood_group = view.findViewById(R.id.tv_blood_group);
            tv_age = view.findViewById(R.id.tv_age);
            tv_location = view.findViewById(R.id.tv_location);
            tv_disease = view.findViewById(R.id.tv_disease);

        }
    }
}