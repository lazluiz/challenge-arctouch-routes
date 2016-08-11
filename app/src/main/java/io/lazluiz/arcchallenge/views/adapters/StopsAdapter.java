package io.lazluiz.arcchallenge.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Stop;

/**
 * Created by luiz on 10/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class StopsAdapter extends ArrayAdapter<Stop> {

    public StopsAdapter(Context context, int resource, List<Stop> stops) {
        super(context, resource, stops);
    }

    // Custom method to refresh the list with new routes
    public void repopulateList(List<Stop> stops){
        clear();
        addAll(stops);
        notifyDataSetChanged();
    }

    // A view holder for better performance when retrieving new views
    private static class ViewHolder {
        TextView tvSequence;
        TextView tvStopName;

        public ViewHolder(View view){
            tvSequence = (TextView) view.findViewById(R.id.item_text_short_name);
            tvStopName = (TextView) view.findViewById(R.id.item_text_long_name);
            view.setTag(this);
        }
    }

    @Override
    public View getView(int pos, View v, ViewGroup parent) {

        ViewHolder holder;

        if (v == null) {
            v = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.item_list_routes, parent, false);
            holder = new ViewHolder(v);
        }else {
            holder = (ViewHolder) v.getTag();
        }

        Stop stop = getItem(pos);
        holder.tvSequence.setText(formatSequence(stop.getSequence()));
        holder.tvStopName.setText(stop.getName());

        return v;
    }

    private String formatSequence(int sequence){
        String seqStr = String.valueOf(sequence);
        char lastNum = seqStr.charAt(seqStr.length() - 1);

        return seqStr.equals("1") ? seqStr.concat("st")
                : seqStr.equals("2") ? seqStr.concat("nd")
                : seqStr.equals("3") ? seqStr.concat("rd")
                : seqStr.concat("th");
    }
}
