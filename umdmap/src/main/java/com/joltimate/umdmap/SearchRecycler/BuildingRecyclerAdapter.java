package com.joltimate.umdmap.SearchRecycler;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joltimate.umdmap.R;
import com.joltimate.umdmap.map.Building;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 7/9/2015.
 */
class BuildingRecyclerAdapter extends RecyclerView.Adapter<RouteViewHolder> {
    protected ArrayList<Building> mDataset;
    public BuildingRecyclerAdapter(){
        mDataset = new ArrayList<Building>();
        mDataset.add(new Building(""));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    private void add(Building entry) {
        mDataset.add(entry);

    }
    public void add(List<Building> entries){
        //DebuggingTools.logCurrentTask();
        mDataset.clear();
        for ( Building entry: entries){
            add(entry);
        }
        notifyDataSetChanged();
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        LinearLayout l= (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listrow, viewGroup, false);
        RouteViewHolder vh = new RouteViewHolder(l);
        return vh;
    }
    @Override
    public void onBindViewHolder(RouteViewHolder viewHolder, int i) {
        RouteViewHolder rvh = viewHolder;
        rvh.b = mDataset.get(i);
        TextView textView= (TextView) rvh.linearLayout.getChildAt(0);
        //textView.setTypeface(null, Typeface.ITALIC);
        String text;
        if ( rvh.b.getCode() != null && !rvh.b.getCode().equals("")){
            textView.setText(rvh.b.getName()+" ("+rvh.b.getCode()+")");
        } else {
            textView.setText(rvh.b.getName());
        }

    }

}
