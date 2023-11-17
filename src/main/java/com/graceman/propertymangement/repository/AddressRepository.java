package com.graceman.propertymangement.repository;

import com.graceman.propertymangement.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
