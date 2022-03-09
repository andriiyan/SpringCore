package com.github.andriiyan.sprongtraining.impl.model;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.model.UserImpl;
import com.github.andriiyan.sprongtraining.impl.model.UserImpl.UserJsonInstanceCreator;

import java.util.Date;

/**
 * Factory for creating models from parameters.
 */
public final class ModelsFactory {

    private ModelsFactory() {
        UserJsonInstanceCreator a;
    }

    /**
     * Factory method that creates instance of [Ticket]
     * @return instance of [Ticket]
     */
    public static Ticket createTicket(long id, long eventId, long userId, Ticket.Category category, int place) {
        return new TicketImpl(id, eventId, userId, category, place);
    }

    public static Event createEvent(long id, String title, Date date) {
        return new EventImpl(id, title, date);
    }

    public static User createUser(long id, String name, String email) {
        return new UserImpl(id, name, email);
    }

}
