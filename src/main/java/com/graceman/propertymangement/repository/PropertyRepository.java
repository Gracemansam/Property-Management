package com.graceman.propertymangement.repository;

import com.graceman.propertymangement.model.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    //@Query("SELECT p FROM PropertyEntity p WHERE p.userEntity.id = :userId AND p.title = :title")
    //List<Property> findAllByUserEntityId(@Param("userId") Long userId, @Param("title") Long title);
    List<Property> findAllByUserId(@Param("userId") Long userId);
}
