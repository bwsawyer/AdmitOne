package com.bsawyer.admitone.repository;

import com.bsawyer.admitone.domain.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findByCustomerAndEventId(String username, int eventId, Pageable pageable);

    List<Ticket> findByEventIdBetweenOrderByEventIdAscCustomerAsc(int fromEventId, int toEventId);

}
