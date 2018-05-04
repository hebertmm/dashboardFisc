package io.github.***REMOVED***mm.dash.domain;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private List<Person> members = new ArrayList<>();
    private String description;
    private RemoteDevice remoteDevice;
    private Location localAtual;
    private Target objetivo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }

    public void addMember(Person person){
        this.members.add(person);
    }

    public void removeMember(int index){
        this.members.remove(index);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RemoteDevice getRemoteDevice() {
        return remoteDevice;
    }

    public void setRemoteDevice(RemoteDevice remoteDevice) {
        this.remoteDevice = remoteDevice;
    }

    public Target getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Target objetivo) {
        this.objetivo = objetivo;
    }
}
