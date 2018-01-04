package com.dcalabrese22.dan.skipassusage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dcalabrese on 1/4/2018.
 */

public class PassesUsedAdapter extends RecyclerView.Adapter<PassesUsedAdapter.PassesUsedViewHolder> {

    private List<SkiArea> mAreas;

    public PassesUsedAdapter(List<SkiArea> areas) {
        mAreas = areas;
    }

    @Override
    public PassesUsedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layout = R.layout.ski_area_with_pass;
        View view = inflater.inflate(layout, parent, false);
        return new PassesUsedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PassesUsedViewHolder holder, int position) {
        TextView name = holder.mSkiAreaName;
        TextView passesUsed = holder.mPassesUsed;
        SkiArea area = mAreas.get(position);
        name.setText(area.getResortName());
        passesUsed.setText(area.getTimesGone());
    }

    @Override
    public int getItemCount() {
        if (mAreas != null) {
            return mAreas.size();
        }
        return 0;
    }

    class PassesUsedViewHolder extends RecyclerView.ViewHolder {

        TextView mSkiAreaName;
        TextView mPassesUsed;

        public PassesUsedViewHolder(View itemView) {
            super(itemView);
            mSkiAreaName = itemView.findViewById(R.id.tv_ski_area_name);
            mPassesUsed = itemView.findViewById(R.id.tv_ski_area_pass);
        }
    }
}
