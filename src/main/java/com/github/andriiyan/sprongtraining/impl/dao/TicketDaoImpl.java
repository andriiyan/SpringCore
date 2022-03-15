package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TicketDaoImpl extends BaseDaoImpl<Ticket> implements TicketDao {

    private static final Logger logger = LoggerFactory.getLogger(TicketDaoImpl.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class<Ticket> getType() {
        return Ticket.class;
    }
}
