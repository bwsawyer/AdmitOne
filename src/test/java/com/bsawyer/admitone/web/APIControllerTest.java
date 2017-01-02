package com.bsawyer.admitone.web;

import com.bsawyer.admitone.domain.Ticket;
import com.bsawyer.admitone.repository.TicketRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class APIControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        ticketRepository.deleteAll();
    }

    @Test
    public void testPurchase() throws Exception {
        String username = "bsawyer";
        int eventId = 2;
        String body = "3";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/rest/" + username + "/purchase/" + eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Iterable<Ticket> tickets = ticketRepository.findAll();
        int count = 0;
        for (Ticket ticket : tickets) {
            assertEquals(username, ticket.getCustomer());
            assertEquals(eventId, ticket.getEventId());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void testCancel() throws Exception {
        String username = "bsawyer";
        int eventId = 2;
        String body = "3";

        for (int i = 0; i < 4; i++) {
            ticketRepository.save((new Ticket(username, eventId)));
        }
        Iterable<Ticket> tickets = ticketRepository.findAll();

        assertEquals(4, Lists.newArrayList(tickets).size());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rest/" + username + "/cancel/" + eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        tickets = ticketRepository.findAll();

        assertEquals(1, Lists.newArrayList(tickets).size());

        //Test for an error if the user attempts to cancel too many tickets
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rest/" + username + "/cancel/" + eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    public void testExchange() throws Exception {
        String username = "bsawyer";
        int fromEventId = 2;
        int numTickets = 3;
        int toEventId = 3;
        String body = "{ \"fromEventId\": " + fromEventId + ", \"toEventId\": " + toEventId + ", \"numTickets\": " + numTickets + " }";

        for (int i = 0; i < 4; i++) {
            ticketRepository.save((new Ticket(username, fromEventId)));
        }
        ticketRepository.save((new Ticket("othername", fromEventId)));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/" + username + "/exchange/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        List<Ticket> tickets = ticketRepository.findByCustomerAndEventId(username, toEventId, new PageRequest(0, 10));
        for (Ticket ticket : tickets) {
            assertEquals(username, ticket.getCustomer());
            assertEquals(toEventId, ticket.getEventId());
        }
        assertEquals(3, tickets.size());

        //Test for an error if the user attempts to exchange too many tickets
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/" + username + "/exchange/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
