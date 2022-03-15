package com.github.andriiyan.sprongtraining.impl.model;

import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.impl.utils.JsonInstanceCreator;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

class TicketImpl implements Ticket {
    private static final long serialVersionUID = 1L;

    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public TicketImpl(long id, long eventId, long userId, Category category, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
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
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    public static class TicketJsonInstanceCreator implements JsonInstanceCreator<Ticket> {

        @Override
        public Collection<Ticket> createInstances(String source, Gson gson) {
            return Arrays.asList(gson.fromJson(source, TicketImpl[].class));
        }

        @Override
        public Class<Ticket> getType() {
            return Ticket.class;
        }
    }
}
