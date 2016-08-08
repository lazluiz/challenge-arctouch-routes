package io.lazluiz.arcchallenge.network;

import android.app.Application;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.lazluiz.arcchallenge.BuildConfig;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class QueueNetwork {

    private static final String HEADER_CONTENT_TYPE_PARAM_KEY = "Content-Type";
    private static final String HEADER_CONTENT_TYPE_PARAM_VALUE = "application/json";
    private static final String HEADER_CONTENT_ENCODING_PARAM_KEY = "Content-Encoding";
    private static final String HEADER_CONTENT_ENCODING_PARAM_VALUE = "gzip";
    private static final String HEADER_ACCEPT_PARAM_KEY = "Accept";
    private static final String HEADER_ACCEPT_PARAM_VALUE = "application/json";
    private static final String HEADER_APPGLU_PARAM_KEY = "X-AppGlu-Environment";
    private static final String HEADER_APPGLU_PARAM_VALUE = "staging";
    private static final String HEADER_AUTH_PARAM_KEY = "Authorization";
    private static final String HEADER_AUTH_PARAM_VALUE = "Basic " + BuildConfig.API_KEY;

    private static QueueNetwork mInstance;
    private Context mApplicationContext;
    private RequestQueue mRequestQueue;

    // Prevent instantiation
    private QueueNetwork() {
        super();
    }

    // Singleton
    public static QueueNetwork getInstance() {
        if(mInstance == null) {
            mInstance = new QueueNetwork();
        }
        return mInstance;
    }

    public void init(Application application) {
        if(mApplicationContext == null) {
            mApplicationContext = application.getApplicationContext();
            mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        }
    }

    public void doPost(String url, Object tag, JSONObject data,
                       NetworkRequestCallback<JSONObject> callback) {
        JsonObjectRequest jsonObjectRequest = buildJSONRequest(
                Request.Method.POST,
                url,
                data,
                tag,
                callback);
        doRequest(jsonObjectRequest);
    }

    // Private Volley single object request
    private JsonObjectRequest buildJSONRequest(int method, String url, JSONObject jsonObject, Object tag,
                                               final NetworkRequestCallback<JSONObject> callback) {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                notifyOnResponse(response, callback);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                notifyOnErrorResponse(error, callback);
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(method, url, jsonObject, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return buildHeaders();
            }
        };
        request.setTag(tag);
        return request;
    }

    // Setup Headers
    private Map<String, String> buildHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HEADER_CONTENT_TYPE_PARAM_KEY, HEADER_CONTENT_TYPE_PARAM_VALUE);
        headers.put(HEADER_CONTENT_ENCODING_PARAM_KEY, HEADER_CONTENT_ENCODING_PARAM_VALUE);
        headers.put(HEADER_ACCEPT_PARAM_KEY, HEADER_ACCEPT_PARAM_VALUE);
        headers.put(HEADER_APPGLU_PARAM_KEY, HEADER_APPGLU_PARAM_VALUE);
        headers.put(HEADER_AUTH_PARAM_KEY, HEADER_AUTH_PARAM_VALUE);
        return headers;
    }

    private <T> void doRequest(Request<T> request) {
        mRequestQueue.add(request);
    }

    private <T> void notifyOnResponse(T response, NetworkRequestCallback<T> callback) {
        if(callback != null) {
            callback.onRequestResponse(response);
        }
    }

    private <T> void notifyOnErrorResponse(Exception error, NetworkRequestCallback<T> callback) {
        if(callback != null) {
            callback.onRequestError(error);
        }
    }

    public interface NetworkRequestCallback<T> {
        void onRequestResponse(T response);
        void onRequestError(Exception error);
    }

}
