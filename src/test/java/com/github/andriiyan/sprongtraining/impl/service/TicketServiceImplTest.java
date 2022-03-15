package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.TestModelsFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {

    @Mock
    private TicketDao ticketDao;

    private TicketServiceImpl ticketService;

    @Before
    public void init() {
        ticketService = new TicketServiceImpl();
        ticketService.setTicketDao(ticketDao);
    }

    @Test
    public void bookTicket_shouldInvokeDao() {
        final Ticket returningTicket = TestModelsFactory.generateSingleTicket();
        final long userId = 1;
        final long eventId = 1;
        final int place = 12;
        final Ticket.Category category = Ticket.Category.PREMIUM;
        Mockito.when(ticketDao.save(Mockito.any())).thenReturn(returningTicket);

        final Ticket returnedTicket = ticketService.bookTicket(userId, eventId, place, category);

        Assert.assertEquals(returningTicket, returnedTicket);
        Mockito.verify(ticketDao).save(Mockito.any());
    }

    @Test
    public void getBookedTicketsUser_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final User user = TestModelsFactory.generateSingleUser();
        final List<Ticket> allTickets = TestModelsFactory.generateTickers(pageSize * (pageNum + 1), new TestModelsFactory.DefaultTicketCountInstanceFactory() {
            @Override
            protected long userId(int count) {
                return user.getId();
            }
        });
        Mockito.when(ticketDao.findAll()).thenReturn(allTickets);


        final List<Ticket> returnedTickets = ticketService.getBookedTickets(user, pageSize, pageNum);

        Assert.assertEquals(allTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
        Mockito.verify(ticketDao).findAll();
    }

    @Test
    public void getBookedTicketsEvent_shouldReturnCorrectTickets() {
        final int pageSize = 5;
        final int pageNum = 2;

        final Event event = TestModelsFactory.generateSingleEvent();
        final List<Ticket> allTickets = TestModelsFactory.generateTickers(pageSize * (pageNum + 1), new TestModelsFactory.DefaultTicketCountInstanceFactory() {
            @Override
            protected long eventId(int count) {
                return event.getId();
            }
        });
        Mockito.when(ticketDao.findAll()).thenReturn(allTickets);


        final List<Ticket> returnedTickets = ticketService.getBookedTickets(event, pageSize, pageNum);

        Assert.assertEquals(allTickets.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedTickets);
        Mockito.verify(ticketDao).findAll();
    }


    @Test
    public void cancelTicket_shouldReturnSameModelAsDao() {
        final long ticketId = 100;
        Mockito.when(ticketDao.delete(ticketId)).thenReturn(true);

        Assert.assertTrue(ticketService.cancelTicket(ticketId));
        Mockito.verify(ticketDao).delete(ticketId);
    }

}