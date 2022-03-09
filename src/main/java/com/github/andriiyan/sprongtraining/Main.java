package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.utils.DumpUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

public class Main {

    private static void dumpIfCan(@NonNull ApplicationContext context) {
        try {
            final DumpUtils dumpUtils = context.getBean(DumpUtils.class);
            dumpUtils.dump();
        } catch (NoSuchBeanDefinitionException ignored) {

        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml",
                "application-byte.xml",
                "application-json.xml",
                "application-dump.xml"
                );
        dumpIfCan(context);
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        boolean a = true;
    }

}
