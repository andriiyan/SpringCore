package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.utils.DumpUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContainerTest {

    public <T> void verifyNotContainsInConfig(ApplicationContext context, Class<T> type) {
        try {
            final T model = context.getBean(type);
            Assert.fail(type + " should not be present in the default config");
        } catch (NoSuchBeanDefinitionException ignored) {

        }
    }

    @Test
    public void defaultConfig() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
        );
        // DumpUtils should not be persist in the container
        verifyNotContainsInConfig(context, DumpUtils.class);

        final EventDao eventDao = context.getBean(EventDao.class);
        final TicketDao ticketDao = context.getBean(TicketDao.class);
        final UserDao userDao = context.getBean(UserDao.class);
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
    }

    @Test
    public void dumpConfig() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
        );
        context.getEnvironment().setActiveProfiles("byte", "dump");
        context.refresh();

        final DumpUtils dumpUtils = context.getBean(DumpUtils.class);
        final EventDao eventDao = context.getBean(EventDao.class);
        final TicketDao ticketDao = context.getBean(TicketDao.class);
        final UserDao userDao = context.getBean(UserDao.class);
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        Assert.assertNotNull(dumpUtils);
        Assert.assertNotNull(eventService);
        Assert.assertNotNull(ticketService);
        Assert.assertNotNull(userService);
        Assert.assertNotNull(bookingFacade);
        Assert.assertNotNull(eventDao);
        Assert.assertNotNull(ticketDao);
        Assert.assertNotNull(userDao);
        Assert.assertNotNull(eventDao);
    }

}
