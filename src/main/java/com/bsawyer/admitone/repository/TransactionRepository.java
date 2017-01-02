package com.bsawyer.admitone.repository;

import com.bsawyer.admitone.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByCustomer(String customer);

}
