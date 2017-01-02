package com.bsawyer.admitone.web;

import com.bsawyer.admitone.domain.Ticket;
import com.bsawyer.admitone.domain.Transaction;
import com.bsawyer.admitone.repository.TicketRepository;
import com.bsawyer.admitone.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("/rest")
//Contains the RESTful API for purchasing, cancelling, and exchanging tickets
public class APIController {

    private static final Logger log = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Allows a user to purchase the given number of tickets for the given event.
     * @param username username of the customer requesting tickets
     * @param eventId id of the desired event
     * @param numTickets number of tickets to purchase
     */
    @RequestMapping(value="/{username}/purchase/{eventId}", method= RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void purchase(@PathVariable String username, @PathVariable int eventId, @RequestBody int numTickets) {
        for(int i = 0; i < numTickets; i++) {
            ticketRepository.save(new Ticket(username, eventId));
        }
        transactionRepository.save(new Transaction(username, eventId, "purchase", numTickets, LocalDate.now()));
    }

    /**
     * Allows a customer to cancel tickets for an event.
     * @param username username of the customer cancelling tickets
     * @param eventId id of the event to cancel
     * @param numTickets number of tickets to cancel. Cannot be greater than the number of purchased tickets.
     */
    @RequestMapping(value="/{username}/cancel/{eventId}", method=RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void cancel(@PathVariable String username, @PathVariable int eventId, @RequestBody int numTickets) {
        Pageable page = new PageRequest(0, numTickets);
        log.info(numTickets + "");
        List<Ticket> tickets = ticketRepository.findByCustomerAndEventId(username, eventId, page);

        log.info(tickets.size() +"");

        if (numTickets > tickets.size()) {
            throw new TooManyTicketsException("Number of tickets to cancel exceeds number of tickets in possession for that show");
        }

        for (Ticket t : tickets) {
            ticketRepository.delete(t);
        }
        transactionRepository.save(new Transaction(username, eventId, "cancel", numTickets, LocalDate.now()));
    }

    /**
     * Allows a customer to exchange tickets from one event to another.
     * @param username username of the customer exchanging tickets
     * @param request contains details of events and number of tickets to exchange. Number of tickets cannot be greater than the number of tickets owned.
     */
    @RequestMapping(value="/{username}/exchange", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void exchange(@PathVariable String username, @RequestBody ExchangeRequest request) {
        Pageable page = new PageRequest(0, request.getNumTickets());
        List<Ticket> tickets = ticketRepository.findByCustomerAndEventId(username, request.getFromEventId(), page);

        if (request.getNumTickets() > tickets.size()) {
            throw new TooManyTicketsException("Number of tickets to exchange exceeds number of tickets in possession for that show");
        }

        //Lock?
        for (Ticket t : tickets) {
            t.setEventId(request.getToEventId());
            ticketRepository.save(t);
        }
        transactionRepository.save(new Transaction(username, request.getToEventId(), "purchase", request.getNumTickets(), LocalDate.now()));
        transactionRepository.save(new Transaction(username, request.getFromEventId(), "cancel", request.getNumTickets(), LocalDate.now()));
    }

    @RequestMapping(value="/{username}/history", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> history(@PathVariable String username) {
        return transactionRepository.findByCustomer(username);
    }
}
