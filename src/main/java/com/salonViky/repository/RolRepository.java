package com.salonViky.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salonViky.model.Rol;


@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> {


}
