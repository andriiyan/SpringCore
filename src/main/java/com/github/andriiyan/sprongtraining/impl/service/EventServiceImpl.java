package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public Event getEventById(long eventId) {
        return eventDao.findById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return StreamUtils.paging(eventDao.findAll()
                .stream()
                .filter(event -> event.getTitle().contains(title)), pageNum, pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        final Instant requestedDay = day.toInstant().truncatedTo(ChronoUnit.DAYS);
        return StreamUtils.paging(
                eventDao.findAll()
                        .stream()
                        .filter(event -> event.getDate().toInstant().truncatedTo(ChronoUnit.DAYS).equals(requestedDay)),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        return eventDao.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventDao.update(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDao.delete(eventId);
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
