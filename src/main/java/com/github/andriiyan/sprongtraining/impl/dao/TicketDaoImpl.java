package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.TicketDao;
import com.github.andriiyan.sprongtraining.api.model.Ticket;

class TicketDaoImpl extends BaseDaoImpl<Ticket> implements TicketDao {

    @Override
    public Class<Ticket> getType() {
        return Ticket.class;
    }
}
