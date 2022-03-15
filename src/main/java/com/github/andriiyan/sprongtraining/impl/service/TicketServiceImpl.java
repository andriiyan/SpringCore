package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.impl.model.ModelsFactory;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        final Ticket ticket = ticketDao.save(ModelsFactory.createTicket(0, eventId, userId, category, place));
        logger.info("bookTicket was invoked with userId=" + userId + ", eventId=" + eventId + ", place=" + place +
                ", category=" + category + " and returning " + ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final List<Ticket> tickets = StreamUtils.paging(
                ticketDao.findAll()
                        .stream()
                        .filter(ticket -> ticket.getUserId() == user.getId()),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.info("getBookedTickets was invoked user=" + user + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returning " + tickets.toString());
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = StreamUtils.paging(
                ticketDao.findAll()
                        .stream()
                        .filter(ticket -> ticket.getEventId() == event.getId()),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.info("getBookedTickets was invoked with event=" + event + ", pageSize=" + pageSize +
                ", pageNum=" + pageNum + " and returning " + tickets);
        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        final boolean result = ticketDao.delete(ticketId);
        logger.info("cancelTicket was invoked with ticketId=" + ticketId + " and returning " + result);
        return result;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
