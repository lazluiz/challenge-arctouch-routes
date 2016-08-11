package io.lazluiz.arcchallenge.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luiz on 11/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class DepartureGroup {

    private String Name;
    private List<Departure> Departures;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public List<Departure> getDepartures() {
        return Departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.Departures = departures;
    }

}
