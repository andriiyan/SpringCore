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
        final List<Ticket> tickets = findPage(pageNum, pageSize, ticket -> ticket.getUserId() == user.getId());
        logger.debug("getBookedTickets was invoked user={}, pageSize={}, pageNum={} and returning {}", user, pageSize, pageNum, tickets);
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        final List<Ticket> tickets = findPage(pageNum, pageSize, ticket -> ticket.getEventId() == event.getId());
        logger.debug("getBookedTickets was invoked with event={}, pageSize={}, pageNum={} and returning {}", event, pageSize, pageNum, tickets);
        return tickets;
    }
}
