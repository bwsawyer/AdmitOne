package com.bsawyer.admitone.web;

import com.bsawyer.admitone.domain.Ticket;
import com.bsawyer.admitone.repository.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AdminControllerTest {

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
    public void testSearch() throws Exception {
        int begEventId = 1;
        int endEventId = 2;

        ticketRepository.save((new Ticket("bsawyer", 1)));
        ticketRepository.save((new Ticket("otherguy", 1)));
        ticketRepository.save((new Ticket("bsawyer", 1)));
        ticketRepository.save((new Ticket("bsawyer", 2)));
        ticketRepository.save((new Ticket("bsawyer", 2)));
        ticketRepository.save((new Ticket("bsawyer", 3)));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/search/"+ begEventId + "/" + endEventId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customer", is("bsawyer")))
                .andExpect(jsonPath("$[0].count", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[1].customer", is("otherguy")))
                .andExpect(jsonPath("$[2].customer", is("bsawyer")))
                .andExpect(jsonPath("$[2].count", is(2)));
    }
}
