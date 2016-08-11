package io.lazluiz.arcchallenge.views.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Departure;
import io.lazluiz.arcchallenge.models.DepartureGroup;

/**
 * Created by luiz on 11/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class DeparturesAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<DepartureGroup> mDeparturesGroups;

    public DeparturesAdapter(Context c, List<DepartureGroup> departureGroups) {
        Collections.reverse(departureGroups);

        mContext = c;
        mDeparturesGroups = departureGroups;
    }

    @Override
    public int getGroupCount() {
        return mDeparturesGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDeparturesGroups.get(groupPosition).getDepartures().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDeparturesGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDeparturesGroups.get(groupPosition).getDepartures().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.item_list_departures_group, null);
        }
        DepartureGroup departureGroup = (DepartureGroup) getGroup(groupPosition);

        TextView tvHeader = (TextView) convertView.findViewById(R.id.item_text_header);
        tvHeader.setText(departureGroup.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_list_departures, null);
        }

        Departure departure = (Departure) getChild(groupPosition, childPosition);

        TextView tvTime = (TextView) convertView.findViewById(R.id.item_text_time);
        tvTime.setText(departure.getTime());

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}