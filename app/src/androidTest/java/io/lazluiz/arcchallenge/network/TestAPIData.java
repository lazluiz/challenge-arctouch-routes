package io.lazluiz.arcchallenge.network;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.gson.Gson;

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

    public void testAPIData() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);
        final QueueNetwork queueNetwork = QueueNetwork.getInstance();
        final Gson gson = new Gson();

        String strRoutesByStopName = "{\"params\": {" +
                "\"stopName\": \"%lauro linhares%\" }}";
        String strStopsByRouteId = "{\"params\": {" +
                "\"routeId\": 35 }}";
        String strDeparturesByRouteId = "{\"params\": {" +
                "\"routeId\": 17 }}";

        //final JSONObject data = new JSONObject(strRoutesByStopName);
        //final JSONObject data = new JSONObject(strDeparturesByRouteId);
        final JSONObject data = new JSONObject(strStopsByRouteId);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                queueNetwork.doPost(URL_API_StopsByRouteId, LOG, data,new QueueNetwork.NetworkRequestCallback<JSONObject>() {
                    @Override
                    public void onRequestResponse(JSONObject response) {
                        assertNotNull("The API should give us at least a bracket", response);
                        Route[] routes = null;
                        Departure[] departure = null;
                        Stop[] stops = null;
                        try {
                            Log.i(LOG, "JSON coming!!!\n" + response.toString(2));

                            String dataList = response.getJSONArray("rows").toString();


                            //routes = gson.fromJson(dataList, Route[].class);
                            //departure = gson.fromJson(dataList, Departure[].class);
                            stops = gson.fromJson(dataList, Stop[].class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        signal.countDown();
                    }

                    @Override
                    public void onRequestError(Exception error) {
                        Log.e(LOG, "Error: " + error.getMessage());
                        signal.countDown();
                    }
                });
            }
        });

        makeResquest(URL_API_DeparturesByRouteId, new JSONObject(strRoutesByStopName), Route.class, signal);



        // Timeout
        signal.await(999, TimeUnit.SECONDS);
    }

    private void makeResquest(String url, JSONObject data, final Type classType, final CountDownLatch signal) throws Throwable {





    }

}
