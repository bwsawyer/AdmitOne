package com.bsawyer.admitone.web;

import com.bsawyer.admitone.domain.Ticket;
import com.bsawyer.admitone.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TicketRepository repository;

    /**
     * Searches for tickets for events between the two given event IDs.
     * @param begEventId
     * @param endEventId
     * @return A list of events with the customers who have tickets and how many tickets they've purchased
     */
    @RequestMapping(value="/search/{begEventId}/{endEventId}", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<EventData> searchEvents(@PathVariable int begEventId, @PathVariable int endEventId) {
        List<Ticket> tickets = repository.findByEventIdBetweenOrderByEventIdAscCustomerAsc(begEventId, endEventId);

        List<EventData> toReturn = new ArrayList<>();

        if (tickets.size() == 0)
            return toReturn;

        //Count the number of tickets per customer per event
        //this logic could be done on the client side
        Ticket prevCustomer = tickets.get(0);
        int count = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getCustomer().equals(prevCustomer.getCustomer())) {
                count++;
            } else {
                toReturn.add(new EventData(prevCustomer.getCustomer(), prevCustomer.getEventId(), count));
                prevCustomer = ticket;
                count = 1;
            }
        }
        toReturn.add(new EventData(prevCustomer.getCustomer(), prevCustomer.getEventId(), count));
        return toReturn;
    }

    //Used to serialize the results of a call to search
    class EventData {
        String customer;
        int eventId;
        int count;

        public EventData() { }

        public EventData(String customer, int eventId, int count) {
            this.customer = customer;
            this.eventId = eventId;
            this.count = count;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
