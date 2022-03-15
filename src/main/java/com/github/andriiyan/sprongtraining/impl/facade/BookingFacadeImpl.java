package com.github.andriiyan.sprongtraining.impl.facade;

import com.github.andriiyan.sprongtraining.api.facade.BookingFacade;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

class BookingFacadeImpl implements BookingFacade {

    private static final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;

    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public Event getEventById(long eventId) {
        final Event event = eventService.getEventById(eventId);
        logger.info("getEventById invoked with " + eventId + " returning " + event);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> events = eventService.getEventsByTitle(title, pageSize, pageNum);
        logger.info("getEventsByTitle invoked with title=" + title + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returned " + events.toString());
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final List<Event> events = eventService.getEventsForDay(day, pageSize, pageNum);
        logger.info("getEventsByTitle invoked with day=" + day + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returned " + events.toString());
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        final Event mEvent = eventService.createEvent(event);
        logger.info("createEvent was invoked with " + event.toString() + " and returning " + mEvent);
        return mEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        final Event mEvent = eventService.updateEvent(event);
        logger.info("updateEvent was invoked with " + event.toString() + " and returning " + mEvent);
        return mEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        final boolean result = eventService.deleteEvent(eventId);
        logger.info("deleteEvent was invoked with " + eventId + " and returning " + result);
        return result;
    }

    @Override
    public User getUserById(long userId) {
        final User user = userService.getUserById(userId);
        logger.info("getUserById was invoked with " + user + " and returning " + user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        final User user = userService.getUserByEmail(email);
        logger.info("getUserByEmail was invoked with " + email + " and returning " + user);
        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> users = userService.getUsersByName(name, pageSize, pageNum);
        logger.info("getUsersByName was invoked with name=" + name + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returning " + users.toString());
        return users;
    }

    @Override
    public User createUser(User user) {
        final User mUser = userService.createUser(user);
        logger.info("createUser was invoked with " + user + " and returning " + mUser);
        return mUser;
    }

    @Override
    public User updateUser(User user) {
        final User mUser = userService.updateUser(user);
        logger.info("updateUser was invoked with " + user + " and returning " + mUser);
        return mUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        final boolean result = userService.deleteUser(userId);
        logger.info("deleteUser was invoked with " + userId + " and returning " + result);
        return result;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        final Ticket ticket = ticketService.bookTicket(userId, eventId, place, category);
        logger.info("bookTicket was invoked with userId=" + userId + ", eventId=" + eventId + ", place=" + place +
                ", category=" + category + " and returning " + ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketService.getBookedTickets(user, pageSize, pageNum);
        logger.info("getBookedTickets was invoked with user=" + user + ", pageSize=" + pageSize + ", pageNum=" + pageNum
                + " and returning " + tickets.toString());
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = ticketService.getBookedTickets(event, pageSize, pageNum);
        logger.info("getBookedTickets was invoked with event=" + event + ", pageSize=" + pageSize + ", pageNum=" +
                pageNum + " and returning " + tickets.toString());
        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        final boolean result = ticketService.cancelTicket(ticketId);
        logger.info("cancelTicket was invoked with " + ticketId + " and returning " + result);
        return result;
    }
}
