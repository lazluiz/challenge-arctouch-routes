package io.lazluiz.arcchallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class Stop {

    @SerializedName("id")
    private int Id;
    @SerializedName("name")
    private String Name;
    @SerializedName("sequence")
    private int Sequence;
    @SerializedName("route_id")
    private int RouteId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }
}
