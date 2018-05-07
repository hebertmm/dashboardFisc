package io.github.***REMOVED***mm.dash.util.geoutils;

import io.github.***REMOVED***mm.dash.domain.GeoLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.URLEncoder;

@Service
public class Geoutils {

    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(Geoutils.class);

    public Geoutils() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public Geocode geocodeAddress(String address){
        String url = Geocode.GEOCODE_URL_PREFIX + address + Geocode.GEOCODE_URL_SUFIX;
        log.info(url);
        return restTemplate.getForObject(url, Geocode.class);
    }
    public GeoLocation geocodeForLocation(String address){
        Geocode geocode = geocodeAddress(address);
        GeoLocation location = new GeoLocation();
        location.setCoordinates(new String[]{
                geocode.getResults()[0].getGeometry().getLocation().getLat(),
                geocode.getResults()[0].getGeometry().getLocation().getLng()
        });
        return location;
    }
}
