package io.lazluiz.arcchallenge.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Route;

/**
 * Created by luiz on 09/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class RoutesAdapter extends ArrayAdapter<Route> {

    public RoutesAdapter(Context context, int resource, List<Route> data) {
        super(context, resource, data);
        repopulateList(data);
    }

    // Custom method to refresh the list with new routes
    public void repopulateList(List<Route> routes){
        clear();
        addAll(routes);
        notifyDataSetChanged();
    }

    // A view holder for better performance when retrieving new views
    private static class ViewHolder {
        TextView tvShortName;
        TextView tvLongName;

        public ViewHolder(View view){
            tvShortName = (TextView) view.findViewById(R.id.text_short_name);
            tvLongName = (TextView) view.findViewById(R.id.text_long_name);
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

        Route route = getItem(pos);
        holder.tvShortName.setText(route.getShortName());
        holder.tvLongName.setText(route.getLongName());

        return v;
    }

}
