package io.github.***REMOVED***mm.dash.domain;

public class RemoteDevice {
    private String id;
    private String desc;
    private String number;
    private String messagingId;
    private GeoLocation lastGeoLocation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessagingId() {
        return messagingId;
    }

    public void setMessagingId(String messagingId) {
        this.messagingId = messagingId;
    }

    public GeoLocation getLastGeoLocation() {
        return lastGeoLocation;
    }

    public void setLastGeoLocation(GeoLocation lastGeoLocation) {
        this.lastGeoLocation = lastGeoLocation;
    }
}
