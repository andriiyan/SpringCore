package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.storage.Storage;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

class TicketDaoImpl extends BaseDaoImpl<Ticket> implements TicketDao {

    private static final Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

    public TicketDaoImpl(Storage<Ticket> storage) {
        super(storage);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        final List<Ticket> tickets = StreamUtils.paging(
                findAll()
                        .stream()
                        .filter(ticket -> ticket.getUserId() == user.getId()),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.debug("getBookedTickets was invoked user=" + user + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returning " + tickets.toString());
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = StreamUtils.paging(
                findAll()
                        .stream()
                        .filter(ticket -> ticket.getEventId() == event.getId()),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.debug("getBookedTickets was invoked with event=" + event + ", pageSize=" + pageSize +
                ", pageNum=" + pageNum + " and returning " + tickets);
        return tickets;
    }
}
