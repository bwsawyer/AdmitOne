package com.bsawyer.admitone.repository;

import com.bsawyer.admitone.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
