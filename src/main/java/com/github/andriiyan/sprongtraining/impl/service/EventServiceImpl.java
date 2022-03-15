package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class EventServiceImpl implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDao eventDao;

    @Override
    public Event getEventById(long eventId) {
        final Event event = eventDao.findById(eventId);
        logger.info("getEventById was invoked with userId=" + eventId + " and returning " + event);
        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> events = StreamUtils.paging(eventDao.findAll()
                .stream()
                .filter(event -> event.getTitle().contains(title)), pageNum, pageSize)
                .collect(Collectors.toList());
        logger.info("getEventsByTitle was invoked with title=" + title + ", pageSize=" + pageSize + ", pageNum=" + pageNum
                + " and returning " + events.toString());
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final Instant requestedDay = day.toInstant().truncatedTo(ChronoUnit.DAYS);
        final List<Event> events = StreamUtils.paging(
                eventDao.findAll()
                        .stream()
                        .filter(event -> event.getDate().toInstant().truncatedTo(ChronoUnit.DAYS).equals(requestedDay)),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.info("getEventsForDay was invoked with day=" + day + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returning " + events.toString());
        return events;
    }

    @Override
    public Event createEvent(Event event) {
        final Event mEvent = eventDao.save(event);
        logger.info("createEvent was invoked with event=" + event + " and returning " + mEvent);
        return mEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        final Event mEvent = eventDao.update(event);
        logger.info("updateEvent was invoked with event=" + event  + " and returning " + mEvent);
        return mEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        final boolean result = eventDao.delete(eventId);
        logger.info("deleteEvent was invoked with eventId=" + eventId + " and returning " + result);
        return result;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
