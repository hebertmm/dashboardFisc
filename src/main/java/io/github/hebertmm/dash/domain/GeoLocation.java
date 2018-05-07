package io.github.***REMOVED***mm.dash.domain;

public class GeoLocation {
    private String type;
    private String coordinates[] = new String[2];

    public GeoLocation(){this.type = "POINT";}
    public GeoLocation(String type, String lng, String lat) {
        this.type = type;
        this.coordinates[0] = lng;
        this.coordinates[1] = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }
}
