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

    private ArrayList<DataModel> arrayList;
    private Context context;
    private String blood_group;

    public SearchAdapter(ArrayList<DataModel> arrayList, Context context, String blood_group){
        this.arrayList= arrayList;
        this.context = context;
        this.blood_group = blood_group;
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

        DataModel dataModel = arrayList.get(position);

        holder.tv_age.setText(dataModel.getAge());
        holder.tv_blood_group.setText(blood_group);
        holder.tv_location.setText(dataModel.getCurrent_address());
        holder.tv_disease.setText(dataModel.getDieases());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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