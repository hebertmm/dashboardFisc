package io.github.***REMOVED***mm.dash.domain;

import org.springframework.data.annotation.Id;

public class Target {
    @Id
    private String id;
    private String address;
    private Location location;
    private String description;
}
