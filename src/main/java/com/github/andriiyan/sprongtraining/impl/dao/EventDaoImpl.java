package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.storage.Storage;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class EventDaoImpl extends BaseDaoImpl<Event> implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(EventDaoImpl.class);

    public EventDaoImpl(Storage<Event> storage) {
        super(storage);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }


    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> events = findPage(pageNum, pageSize, event -> event.getTitle().contains(title));
        logger.debug("getEventsByTitle was invoked with title={}, pageSize={}, pageNum={} and returning {}", title, pageSize, pageNum, events);
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final Instant requestedDay = day.toInstant().truncatedTo(ChronoUnit.DAYS);
        final List<Event> events = findPage(
                pageNum,
                pageSize,
                event -> event.getDate().toInstant().truncatedTo(ChronoUnit.DAYS).equals(requestedDay)
        );
        logger.debug("getEventsForDay was invoked with day={}, pageSize={}, pageNum={} and returning {}", day, pageSize, pageNum, events);
        return events;
    }
}
