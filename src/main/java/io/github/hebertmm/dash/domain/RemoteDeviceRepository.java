package io.github.***REMOVED***mm.dash.domain;


import org.springframework.data.repository.CrudRepository;

public interface RemoteDeviceRepository extends CrudRepository<RemoteDevice, Integer> {
    public RemoteDevice findByNumber(String number);
}
