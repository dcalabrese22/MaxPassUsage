package com.dcalabrese22.dan.maxpassusage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dcalabrese on 12/28/2017.
 */

public class SkiAreaAdapter extends RecyclerView.Adapter<SkiAreaAdapter.SkiAreaViewHolder> {

    private Context mContext;
    private ArrayList<String> mResorts;
    private ArrayList<String> mResortsCopy;
    private SkiAreaClickHandler mHandler;

    public SkiAreaAdapter(SkiAreaClickHandler handler) {
        mResorts = createResortArrayList();
        mResortsCopy = createResortArrayList();
        mHandler = handler;
    }

    private ArrayList<String> createResortArrayList() {
        return new ArrayList<>(Arrays.asList(ResortsBuilder.getResortNames()));
    }

    @Override
    public SkiAreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layout = R.layout.ski_area;
        View view = inflater.inflate(layout, parent, false);
        return new SkiAreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SkiAreaViewHolder holder, int position) {
        TextView target = holder.mSkiAreaName;
        String name = mResorts.get(position);
        target.setText(name);
    }

    @Override
    public int getItemCount() {
        return mResorts.size();
    }

    public void filter(String text) {
        mResorts.clear();
        if (text.isEmpty()) {
            mResorts.addAll(mResortsCopy);
        } else {
            text = text.toLowerCase();
            for (String item : mResortsCopy) {
                if (item.toLowerCase().contains(text)) {
                    mResorts.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class SkiAreaViewHolder extends RecyclerView.ViewHolder {

        TextView mSkiAreaName;

        public SkiAreaViewHolder(View view) {
            super(view);
            mSkiAreaName = view.findViewById(R.id.tv_ski_area_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String area = mResorts.get(getAdapterPosition());
                    mHandler.onSkiAreaClick(area);
                }
            });
        }
    }
}
