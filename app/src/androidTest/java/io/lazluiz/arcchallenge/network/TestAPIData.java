package io.lazluiz.arcchallenge.network;

import android.test.InstrumentationTestCase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class TestAPIData extends InstrumentationTestCase {

    private static final String LOG = TestAPIData.class.getSimpleName();
    private static final String URL_API_RoutesByStopName = "https://api.appglu.com/v1/queries/findRoutesByStopName/run";
    private static final String URL_API_StopsByRouteId = "https://api.appglu.com/v1/queries/findStopsByRouteId/run";
    private static final String URL_API_DeparturesByRouteId = "https://api.appglu.com/v1/queries/findDeparturesByRouteId/run";

    public void testAPIData() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);
        final QueueNetwork queueNetwork = QueueNetwork.getInstance();

        String strRoutesByStopName = "{\"params\": {" +
                "\"stopName\": \"%lauro linhares%\" }}";
        String strStopsByRouteId = "{\"params\": {" +
                "\"routeId\": 35 }}";
        String strDeparturesByRouteId = "{\"params\": {" +
                "\"routeId\": 17 }}";

        final JSONObject data = new JSONObject(strRoutesByStopName);


        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                queueNetwork.doPost(URL_API_RoutesByStopName, LOG, data,new QueueNetwork.NetworkRequestCallback<JSONObject>() {
                    @Override
                    public void onRequestResponse(JSONObject response) {
                        assertNotNull("The API should give us at least a bracket", response);
                        try {
                            Log.i(LOG, "JSON coming!!!\n" + response.toString(2));
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

        // Timeout
        signal.await(10, TimeUnit.SECONDS);
    }

}
