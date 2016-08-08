package io.lazluiz.arcchallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by luiz on 08/08/16.
 * E-mail: lf.lazzarin@gmail.com
 * GitHub: github.com/luizfelippe
 */
public class Route {

    @SerializedName("id")
    private int Id;
    @SerializedName("shortName")
    private String ShortName;
    @SerializedName("longName")
    private String LongName;
    @SerializedName("lastModifiedDate")
    private String LastModifiedDate;
    @SerializedName("agencyId")
    private int AgencyId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getLongName() {
        return LongName;
    }

    public void setLongName(String longName) {
        LongName = longName;
    }

    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public int getAgencyId() {
        return AgencyId;
    }

    public void setAgencyId(int agencyId) {
        AgencyId = agencyId;
    }
}
