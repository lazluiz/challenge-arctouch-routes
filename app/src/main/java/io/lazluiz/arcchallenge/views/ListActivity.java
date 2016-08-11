package io.lazluiz.arcchallenge.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import io.lazluiz.arcchallenge.R;
import io.lazluiz.arcchallenge.models.Route;
import io.lazluiz.arcchallenge.network.APIData;
import io.lazluiz.arcchallenge.views.adapters.RoutesAdapter;

public class ListActivity extends AppCompatActivity {

    private RoutesAdapter mRoutesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Search
        final EditText inputSearch = (EditText) toolbar.findViewById(R.id.input_search);
        Button btnSearch = (Button) toolbar.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSearch(inputSearch);
            }
        });

        // List
        mRoutesAdapter = new RoutesAdapter(this, R.layout.item_list_routes, new ArrayList<Route>());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mRoutesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClick(mRoutesAdapter.getItem(i));
            }
        });
    }

    // List item click
    private void itemClick(Route route){
        Intent it = new Intent(this, DetailsActivity.class);
        it.putExtra(DetailsActivity.EXTRA_ROUTE, route);
        startActivity(it);
    }

    // Button Search click
    private void doSearch(EditText input){
        String inputText = input.getText().toString();

        final APIData apiData = new APIData();
        apiData.callForRoutes(inputText, new APIData.CallbackForRoutes() {
            @Override
            public void result(Route[] routes) {
                mRoutesAdapter.repopulateList(Arrays.asList(routes));
            }
        });
    }
}
