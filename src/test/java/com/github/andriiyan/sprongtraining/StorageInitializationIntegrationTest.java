package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.utils.DumpUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StorageInitializationIntegrationTest {

    private void testDumpUsingConfig(@NonNull final String config) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
        );
        context.getEnvironment().setActiveProfiles(config, "dump");
        context.refresh();

        final DumpUtils dumpUtils = context.getBean(DumpUtils.class);
        final DumpUtils.DumpResult result = dumpUtils.dump();

        final List<User> users = new ArrayList<>(context.getBean(UserDao.class).findAll());
        final List<Ticket> tickets = new ArrayList<>(context.getBean(TicketDao.class).findAll());
        final List<Event> events = new ArrayList<>(context.getBean(EventDao.class).findAll());

        for (int i = 0; i < dumpUtils.getItemCount(); i++) {
            final Event dumpEvent = result.getDumpedEvents().get(i);
            final Event event = events.get(i);
            Assert.assertEquals(dumpEvent.getTitle(), event.getTitle());
            final User dumpUser = result.getDumpedUsers().get(i);
            final User user = users.get(i);
            Assert.assertEquals(dumpUser.getName(), user.getName());
            Assert.assertEquals(dumpUser.getEmail(), user.getEmail());
            final Ticket dumpTicket = result.getDumpedTickets().get(i);
            final Ticket ticket = tickets.get(i);
            Assert.assertEquals(dumpTicket.getCategory(), ticket.getCategory());
            Assert.assertEquals(dumpTicket.getEventId(), ticket.getEventId());
            Assert.assertEquals(dumpTicket.getPlace(), ticket.getPlace());
            Assert.assertEquals(dumpTicket.getUserId(), ticket.getUserId());
        }
    }

    @Test
    public void byteSerializer_storageShouldInitializeFromTheFile() {
        testDumpUsingConfig("byte");
    }

    @Test
    public void jsonSerializer_storageShouldInitializeFromTheFile() {
        testDumpUsingConfig("json");
    }


}
