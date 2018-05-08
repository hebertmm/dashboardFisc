package io.github.***REMOVED***mm.dash.domain;

import org.springframework.data.annotation.Id;

public class Person {
    @Id
    public String id;

    public String name;
    public String lastName;

    public Person(){}

    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
