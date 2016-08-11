package io.lazluiz.arcchallenge.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Departure;
import io.lazluiz.arcchallenge.models.DepartureGroup;
import io.lazluiz.arcchallenge.models.Route;
import io.lazluiz.arcchallenge.network.APIData;
import io.lazluiz.arcchallenge.views.adapters.DeparturesAdapter;

/**
 * Created by luiz on 10/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class DetailsDeparturesFragment extends Fragment {

    ExpandableListView mListDepartures;

    private static final String CALENDAR_WEEKDAY = "WEEKDAY";
    private static final String CALENDAR_SATURDAY = "SATURDAY";
    private static final String CALENDAR_SUNDAY = "SUNDAY";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_departure, container, false);

        mListDepartures = (ExpandableListView) view.findViewById(R.id.list_departures);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Route route = (Route) getActivity().getIntent().getSerializableExtra(DetailsActivity.EXTRA_ROUTE);

        // Load data
        final APIData apiData = new APIData();
        apiData.callForDepartures(route.getId(), new APIData.CallbackForDepartures() {
            @Override
            public void result(Departure[] departures) {
                loadList(Arrays.asList(departures));
            }
        });
    }

    private void loadList(List<Departure> departures) {
        List<DepartureGroup> departureGroups = new ArrayList<>();
        List<Departure> weekDepartures = new ArrayList<>();
        List<Departure> saturdayDepartures = new ArrayList<>();
        List<Departure> sundayDepartures = new ArrayList<>();

        for(Departure d : departures){
            if(d.getCalendar().equals(CALENDAR_WEEKDAY)) weekDepartures.add(d);
            else if (d.getCalendar().equals(CALENDAR_SATURDAY)) saturdayDepartures.add(d);
            else if (d.getCalendar().equals(CALENDAR_SUNDAY)) sundayDepartures.add(d);
        }

        DepartureGroup departureGroupOfSunday = new DepartureGroup();
        departureGroupOfSunday.setName(CALENDAR_SUNDAY);
        departureGroupOfSunday.setDepartures(sundayDepartures);
        departureGroups.add(departureGroupOfSunday);

        DepartureGroup departureGroupOfSaturday = new DepartureGroup();
        departureGroupOfSaturday.setName(CALENDAR_SATURDAY);
        departureGroupOfSaturday.setDepartures(saturdayDepartures);
        departureGroups.add(departureGroupOfSaturday);

        DepartureGroup departureGroupOfWeekdays = new DepartureGroup();
        departureGroupOfWeekdays.setName(CALENDAR_WEEKDAY);
        departureGroupOfWeekdays.setDepartures(weekDepartures);
        departureGroups.add(departureGroupOfWeekdays);

        mListDepartures.setAdapter(new DeparturesAdapter(getContext(), departureGroups));
    }

}
