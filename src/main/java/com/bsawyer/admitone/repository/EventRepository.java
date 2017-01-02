package com.bsawyer.admitone.repository;

import com.bsawyer.admitone.domain.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {

}
