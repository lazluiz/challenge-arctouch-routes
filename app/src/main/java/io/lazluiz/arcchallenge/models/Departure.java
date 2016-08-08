package io.lazluiz.arcchallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class Departure {

    @SerializedName("id")
    private int Id;
    @SerializedName("calendar")
    private String Calendar;
    @SerializedName("time")
    private String Time;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCalendar() {
        return Calendar;
    }

    public void setCalendar(String calendar) {
        Calendar = calendar;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
