package com.github.andriiyan.sprongtraining;

import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.utils.DumpUtils;
import com.github.andriiyan.sprongtraining.impl.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;

// TODO: 3/20/2022 - please add a readme file with description how to run the app
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static void dumpIfCan(@NonNull ApplicationContext context) {
        // TODO: 3/20/2022 - more intelligent way to check whether bean was created or not is to use 'context.getBeansOfType(DumpUtils.class).isEmpty() '
        try {
            logger.debug("Dump started");
            final DumpUtils dumpUtils = context.getBean(DumpUtils.class);
            dumpUtils.dump();
        } catch (NoSuchBeanDefinitionException ignored) {
            logger.debug("No dump bean was found in configuration");
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
        logger.info("Container has been configured");
        final EventService eventService = context.getBean(EventService.class);
        final TicketService ticketService = context.getBean(TicketService.class);
        final UserService userService = context.getBean(UserService.class);
        final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        // TODO: 3/20/2022 - remove unused variable
        boolean a = true;
        logger.info("App finishes it work");
    }

}
