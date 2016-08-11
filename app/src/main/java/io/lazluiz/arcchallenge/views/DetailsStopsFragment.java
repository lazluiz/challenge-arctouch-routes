package io.lazluiz.arcchallenge.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Route;
import io.lazluiz.arcchallenge.models.Stop;
import io.lazluiz.arcchallenge.network.APIData;
import io.lazluiz.arcchallenge.views.adapters.StopsAdapter;

/**
 * Created by luiz on 10/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class DetailsStopsFragment extends Fragment {

    ListView mListStops;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_stops, container, false);

        mListStops = (ListView) view.findViewById(R.id.list_stops);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Route route = (Route) getActivity().getIntent().getSerializableExtra(DetailsActivity.EXTRA_ROUTE);

        // Load data
        final APIData apiData = new APIData();
        apiData.callForStops(route.getId(), new APIData.CallbackForStops() {
            @Override
            public void result(Stop[] stops) {
                loadList(Arrays.asList(stops));
            }
        });
    }

    private void loadList(List<Stop> stops){
        mListStops.setAdapter(new StopsAdapter(getContext(), R.layout.item_list_stops, stops));
    }
}
