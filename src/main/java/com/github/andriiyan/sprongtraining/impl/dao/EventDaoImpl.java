package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class<Event> getType() {
        return Event.class;
    }
}
