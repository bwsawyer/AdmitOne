package com.bsawyer.admitone;

import com.bsawyer.admitone.domain.Event;
import com.bsawyer.admitone.domain.Ticket;
import com.bsawyer.admitone.domain.Transaction;
import com.bsawyer.admitone.repository.EventRepository;
import com.bsawyer.admitone.repository.TicketRepository;
import com.bsawyer.admitone.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.time.LocalDate;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class AdmitOneApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdmitOneApp.class, args);
    }

    @Bean
    public CommandLineRunner startup(EventRepository eventRepository, TicketRepository ticketRepository, TransactionRepository transactionRepository) {
        //Add some items to seed the embedded data store
        return (args) -> {
            //some events
            eventRepository.save(new Event(1, "theShow"));
            eventRepository.save(new Event(2, "Another show"));
            eventRepository.save(new Event(3, "The best show"));

            //some tickets
            ticketRepository.save(new Ticket("mscott", 1));
            ticketRepository.save(new Ticket("mscott", 1));
            ticketRepository.save(new Ticket("dschrute", 1));
            ticketRepository.save(new Ticket("mscott", 2));
            ticketRepository.save(new Ticket("jhalpert", 2));
            ticketRepository.save(new Ticket("dschrute", 3));

            //the corresponding transactions
            transactionRepository.save(new Transaction("mscott", 1, "purchase", 2, LocalDate.now()));
            transactionRepository.save(new Transaction("dschrute", 1, "purchase", 1, LocalDate.now()));
            transactionRepository.save(new Transaction("mscott", 2, "purchase", 1, LocalDate.now()));
            transactionRepository.save(new Transaction("jhalpert", 2, "purchase", 1, LocalDate.now()));
            transactionRepository.save(new Transaction("dschrute", 3, "purchase", 1, LocalDate.now()));

        };
    }
}