package com.bsawyer.admitone.web;

/**
 * This class contains the arguments for a call to the exchange endpoint.
 */
public class ExchangeRequest {
    private int fromEventId;
    private int numTickets;
    private int toEventId;

    public ExchangeRequest() { }

    public ExchangeRequest(int fromEventId, int numTickets, int toEventId) {
        this.fromEventId = fromEventId;
        this.numTickets = numTickets;
        this.toEventId = toEventId;
    }

    public int getFromEventId() {
        return fromEventId;
    }

    public void setFromEventId(int fromEventId) {
        this.fromEventId = fromEventId;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public int getToEventId() {
        return toEventId;
    }

    public void setToEventId(int toEventId) {
        this.toEventId = toEventId;
    }
}


