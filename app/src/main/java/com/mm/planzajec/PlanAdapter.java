package com.mm.planzajec;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {

    private ArrayList<PlanItem> mPlanList;

    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHour;
        public TextView textViewName;
        public TextView textViewSupervisor;
        public TextView textViewRoom;


        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHour = itemView.findViewById(R.id.text_view_plan_hour);
            textViewName = itemView.findViewById(R.id.text_view_plan_name);
            textViewSupervisor = itemView.findViewById(R.id.text_view_plan_supervisor);
            textViewRoom = itemView.findViewById(R.id.text_view_plan_room);
        }
    }

    public PlanAdapter(ArrayList<PlanItem> planList) {
        mPlanList = planList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        PlanViewHolder planViewHolder = new PlanViewHolder(v);
        return planViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        PlanItem currentItem = mPlanList.get(position);
        holder.textViewHour.setText(currentItem.getHour());
        holder.textViewName.setText(currentItem.getName());
        holder.textViewSupervisor.setText(currentItem.getSupervisor());
        holder.textViewRoom.setText(currentItem.getRoom());
    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

}

