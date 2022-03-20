package com.github.andriiyan.sprongtraining.impl.model;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;

import java.util.Date;

/**
 * Factory for creating models from parameters.
 */
// TODO: 3/20/2022 - No reason in this class because allyour factory methods have same arguments, as entity constructors
public final class ModelsFactory {

    private ModelsFactory() {
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
