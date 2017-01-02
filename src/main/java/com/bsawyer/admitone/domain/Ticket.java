package com.bsawyer.admitone.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Represents a ticket to an event
 */
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue
    Long id;

//    @ManyToOne
//    Customer customer;
//    @ManyToOne
//    Event event;

    String customer;
    int eventId;

//    String row;
//    String seat;

    protected Ticket() {}

    public Ticket(String customer, int eventId) {
        this.customer = customer;
        this.eventId = eventId;
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
}
