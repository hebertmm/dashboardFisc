package io.github.***REMOVED***mm.dash.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    public Person findByFirstName(String firstName);
    public List<Person> findByLastName(String lastName);
}
