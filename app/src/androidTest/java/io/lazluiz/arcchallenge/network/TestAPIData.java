package io.lazluiz.arcchallenge.network;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.lazluiz.arcchallenge.models.Departure;
import io.lazluiz.arcchallenge.models.Route;
import io.lazluiz.arcchallenge.models.Stop;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class TestAPIData extends InstrumentationTestCase {

    private static final String LOG = TestAPIData.class.getSimpleName();
    private static final String URL_API_RoutesByStopName = "https://api.appglu.com/v1/queries/findRoutesByStopName/run";
    private static final String URL_API_DeparturesByRouteId = "https://api.appglu.com/v1/queries/findDeparturesByRouteId/run";
    private static final String URL_API_StopsByRouteId = "https://api.appglu.com/v1/queries/findStopsByRouteId/run";

    /**
     * Here we are testing all the three requests to the API, asserting that all requests
     * are bringing us some data.
     */
    public void testAPIData() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(3);

        // The request params are encapsulated in a JSON object called "params"
        String requestFormat = "{\"params\": { \"%s\" : %s }}";

        String strRoutesByStopName = String.format(requestFormat, "stopName", "\"%lauro linhares%\"");
        String strDeparturesByRouteId = String.format(requestFormat, "routeId", 17);
        String strStopsByRouteId = String.format(requestFormat, "routeId", 35);

        final JSONObject jsonRoutesByStopName = new JSONObject(strRoutesByStopName);
        final JSONObject jsonDeparturesByRouteId = new JSONObject(strStopsByRouteId);
        final JSONObject jsonStopsByRouteId = new JSONObject(strDeparturesByRouteId);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                makeResquest(URL_API_RoutesByStopName, jsonRoutesByStopName, Route.class, signal);
                makeResquest(URL_API_DeparturesByRouteId, jsonDeparturesByRouteId, Departure.class, signal);
                makeResquest(URL_API_StopsByRouteId, jsonStopsByRouteId, Stop.class, signal);
            }
        });

        // Timeout
        signal.await(999, TimeUnit.SECONDS);
    }

    private void makeResquest(String url, JSONObject data, final Type classType, final CountDownLatch signal) {
        final QueueNetwork queueNetwork = QueueNetwork.getInstance();
        final Gson gson = new Gson();

        queueNetwork.doPost(url, LOG, data, new QueueNetwork.NetworkRequestCallback<JSONObject>() {
            @Override
            public void onRequestResponse(JSONObject response) {
                assertNotNull("The API should give us at least a bracket", response);

                try {
                    Log.i(LOG, "JSON coming!!!\n" + response.toString(2));

                    // The data from the request comes in an JSON array called "rows"
                    JSONArray dataArr = response.getJSONArray("rows");
                    assertNotNull("It should be a not null array", dataArr);
                    assertTrue("There should be some data!", dataArr.length() > 0 );

                    // Here we check if the Serialization with GSON succeed.
                    if(classType == Route.class){
                        Route[] routes = gson.fromJson(dataArr.toString(), Route[].class);
                        assertTrue("There should be some routes!", routes != null && routes.length > 0 );
                    }else if(classType == Departure.class){
                        Departure[] departures = gson.fromJson(dataArr.toString(), Departure[].class);
                        assertTrue("There should be some departures!", departures != null && departures.length > 0 );
                    }else if(classType == Stop.class){
                        Stop[] stops = gson.fromJson(dataArr.toString(), Stop[].class);
                        assertTrue("There should be some stops!", stops != null && stops.length > 0 );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    signal.countDown();
                }
            }

            @Override
            public void onRequestError(Exception error) {
                signal.countDown();
                Log.e(LOG, "Error: " + error.getMessage());
            }
        });
    }

}
