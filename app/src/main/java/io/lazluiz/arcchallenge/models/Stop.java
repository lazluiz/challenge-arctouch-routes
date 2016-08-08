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
    private String Sequence;
    @SerializedName("route_id")
    private String RouteId;

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

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }

    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        RouteId = routeId;
    }
}
