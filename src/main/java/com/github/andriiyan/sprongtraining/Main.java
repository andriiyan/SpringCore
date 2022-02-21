package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("configuration.xml");
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);
        boolean a = true;
    }

}
