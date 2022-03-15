package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.model.ModelsFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FacadeIntegrationTest {

    @Test
    public void facade_should_write_data_into_the_storage() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
        );
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);
        final User user = bookingFacade.createUser(ModelsFactory.createUser(0, "Andrii", "Test"));
        final Ticket ticket = bookingFacade.bookTicket(user.getId(), 3, 4, Ticket.Category.PREMIUM);
        final UserDao userDao = context.getBean(UserDao.class);
        final TicketDao ticketDao = context.getBean(TicketDao.class);
        Assert.assertEquals(user, userDao.findAll().toArray()[0]);
        Assert.assertEquals(ticket, ticketDao.findAll().toArray()[0]);
        Assert.assertEquals(ticket, bookingFacade.getBookedTickets(user, 1, 0).toArray()[0]);
    }

}
