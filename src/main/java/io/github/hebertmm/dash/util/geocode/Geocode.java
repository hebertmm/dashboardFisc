package io.github.***REMOVED***mm.dash.util.geocode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geocode {
    private Result results[];
    private String status;


    public static String GEOCODE_URL_PREFIX = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static String GEOCODE_URL_SUFIX = "&key=AIzaSyC9Uc93DkXYoiGlPSymsQ1a2EQj75UVsxo";

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return (getStatus() + " Latitude: " + getResults()[0].getGeometry().getLocation().getLat()
                            + " Longitude: " + getResults()[0].getGeometry().getLocation().getLng()+"\n"
                            + "Endereço completo: " + getResults()[0].getFormatted_address()
        );
    }
}
