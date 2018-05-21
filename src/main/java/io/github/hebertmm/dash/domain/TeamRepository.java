package io.github.hebertmm.dash.domain;


import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    public Team findByRemoteDevice(RemoteDevice remote);
    public  Team findByRemoteDevice_Id(Integer id);

}
