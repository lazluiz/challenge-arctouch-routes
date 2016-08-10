package io.lazluiz.arcchallenge.network;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.lazluiz.arcchallenge.models.Departure;
import io.lazluiz.arcchallenge.models.Route;
import io.lazluiz.arcchallenge.models.Stop;

/**
 * Created by luiz on 10/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class APIData {

    private static final String LOG = APIData.class.getSimpleName();

    private static final String URI_API = "https://api.appglu.com/v1/queries/%s/run";
    private static final String URL_API_ROUTES = "findRoutesByStopName";
    private static final String URL_API_DEPARTURES = "findDeparturesByRouteId";
    private static final String URL_API_STOPS = "findStopsByRouteId";

    private static final String API_NODE_PARAMS = "params";
    private static final String API_NODE_ROWS = "rows";
    private static final String API_NODE_STOP_NAME = "stopName";
    private static final String API_NODE_ROUTE_ID = "routeId";

    private final QueueNetwork mQueueNetwork;
    private final Gson mGson;

    /**
     * This is a helper class to get the API data from appglu
     */
    public APIData() {
        mQueueNetwork = QueueNetwork.getInstance();
        mGson = new Gson();
    }

    // Get Routes by stop name
    public void callForRoutes(String stopName, final CallbackForRoutes c) {
        mQueueNetwork.doPost(String.format(URI_API, URL_API_ROUTES), LOG, serializeData(stopName),
                new QueueNetwork.NetworkRequestCallback<JSONObject>() {

            @Override
            public void onRequestResponse(JSONObject response) {
                try {
                    JSONArray dataArr = response.getJSONArray(API_NODE_ROWS);
                    c.result(mGson.fromJson(dataArr.toString(), Route[].class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError(Exception error) {
                Log.e(LOG, error.getMessage());
            }
        });
    }

    // Get Departures by route id
    public void callForDepartures(Integer routeId, final CallbackForDepartures c) {
        mQueueNetwork.doPost(String.format(URI_API, URL_API_DEPARTURES), LOG, serializeData(routeId),
                new QueueNetwork.NetworkRequestCallback<JSONObject>() {

                    @Override
                    public void onRequestResponse(JSONObject response) {
                        try {
                            JSONArray dataArr = response.getJSONArray(API_NODE_ROWS);
                            c.result(mGson.fromJson(dataArr.toString(), Departure[].class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onRequestError(Exception error) {
                        Log.e(LOG, error.getMessage());
                    }
                });
    }

    // Get Stops by route id
    public void callForStops(Integer routeId, final CallbackForStops c) {
        mQueueNetwork.doPost(String.format(URI_API, URL_API_STOPS), LOG, serializeData(routeId),
                new QueueNetwork.NetworkRequestCallback<JSONObject>() {

                    @Override
                    public void onRequestResponse(JSONObject response) {
                        try {
                            JSONArray dataArr = response.getJSONArray(API_NODE_ROWS);
                            c.result(mGson.fromJson(dataArr.toString(), Stop[].class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onRequestError(Exception error) {
                        Log.e(LOG, error.getMessage());
                    }
                });
    }

    // We've got to encapsulate the parameters (stopName / routeId) in a JSON object "params"
    private JSONObject serializeData(Object data) {
        JSONObject jsonData = new JSONObject();
        try {
            JSONObject jsonParams = new JSONObject();
            if (data instanceof String) {
                jsonParams.put(API_NODE_STOP_NAME, String.format("%%%s%%", data));
            } else if(data instanceof Integer) {
                jsonParams.put(API_NODE_ROUTE_ID, (int) data);
            }
            jsonData.put(API_NODE_PARAMS, jsonParams);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    // Callbacks to return the data from the API call
    public interface CallbackForRoutes {
        void result(Route[] routes);
    }

    public interface CallbackForDepartures {
        void result(Departure[] departures);
    }

    public interface CallbackForStops {
        void result(Stop[] stops);
    }

}
