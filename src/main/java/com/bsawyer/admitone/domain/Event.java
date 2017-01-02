package com.bsawyer.admitone.domain;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Event implements Serializable {

    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    protected Event() { }

    public Event(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
