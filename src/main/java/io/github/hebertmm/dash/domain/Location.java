package io.github.***REMOVED***mm.dash.domain;

public class Location {
    private String type;
    private float[] location;

    public Location(String type, float lng, float lat) {
        this.type = type;
        this.location[0] = lng;
        this.location[1] = lat;
    }
}
