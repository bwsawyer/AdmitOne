package com.bsawyer.admitone.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    String customer;
    int eventId;
    String transaction;
    int numTickets;

    LocalDate transactionDate;

    public Transaction() {}

    public Transaction(String customer, int eventId, String transaction, int numTickets, LocalDate transactionDate) {
        this.customer = customer;
        this.eventId = eventId;
        this.transaction = transaction;
        this.numTickets = numTickets;
        this.transactionDate = transactionDate;
    }

    public String getCustomer() {
        return customer;
    }

    public int getEventId() {
        return eventId;
    }

    public String getTransaction() {
        return transaction;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
