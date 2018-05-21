package io.github.hebertmm.dash.domain;

import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;

public interface TargetRepository extends CrudRepository<Target, Integer> {
}
