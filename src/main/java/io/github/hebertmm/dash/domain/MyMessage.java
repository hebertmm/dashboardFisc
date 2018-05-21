package io.github.hebertmm.dash.domain;

import javax.persistence.*;

@Entity
public class MyMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer firebaseId;
    @ManyToOne
    private Team team;
    private String text;
    private String type;
    private Long timestamp;


    //private final String SEND = "send";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(Integer firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
