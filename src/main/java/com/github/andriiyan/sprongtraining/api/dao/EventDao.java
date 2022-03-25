package com.github.andriiyan.sprongtraining.api.dao;

import com.github.andriiyan.sprongtraining.api.model.Event;

import java.util.Date;
import java.util.List;

public interface EventDao extends BaseDao<Event> {

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(Date day, int pageSize, int pageNum);

}
