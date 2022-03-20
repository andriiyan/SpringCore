package com.github.andriiyan.sprongtraining.impl.model;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

// TODO: 3/20/2022 - EventEntity is a better name for this class
class EventImpl implements Event {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private Date date;

    public EventImpl(long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    public static class EventJsonInstanceCreator implements JsonInstanceCreator<Event> {

        @Override
        public Collection<Event> createInstances(String source, Gson gson) {
            return Arrays.asList(gson.fromJson(source, EventImpl[].class));
        }

        @Override
        public Class<Event> getType() {
            return Event.class;
        }
    }
}
