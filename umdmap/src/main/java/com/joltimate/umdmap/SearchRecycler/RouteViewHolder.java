package com.joltimate.umdmap.SearchRecycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joltimate.umdmap.UmdMapModule;
import com.joltimate.umdmap.map.Building;
import com.joltimate.umdmap.other.DebuggingTools;


/**
 * Created by Chris on 7/12/2015.
 */
class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // each data item is just a string in this case
    Building b;
    public TextView mTextView;
    public ImageView imageView;
    public RouteViewHolder(View v) {
        super(v);

    }
    public LinearLayout linearLayout;
    public RouteViewHolder(LinearLayout l) {
        super(l);
        linearLayout = l;
        linearLayout.setOnClickListener(this);
        mTextView = (TextView) l.getChildAt(0);
//        imageView = (ImageView) l.getChildAt(1);
        //mTextView.setOnClickListener(this);
        //      imageView.setOnClickListener(this);
    }

    public void onClick(View v) {
        DebuggingTools.logd("RouteViewHolder", mTextView.getText().toString());
        UmdMapModule.setCenter(Double.valueOf(b.getLat()),Double.valueOf(b.getLng()));
    }
}