package io.lazluiz.arcchallenge.application;

import android.app.Application;

import io.lazluiz.arcchallenge.network.QueueNetwork;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class CustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Volley
        QueueNetwork.getInstance().init(this);

    }

}
