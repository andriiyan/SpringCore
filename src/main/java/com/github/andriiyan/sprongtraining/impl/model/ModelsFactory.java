package com.github.andriiyan.sprongtraining.impl.model;

import com.github.andriiyan.sprongtraining.api.model.Ticket;

public final class ModelsFactory {

    private ModelsFactory() {}

    public static Ticket createTicket(long id, long eventId, long userId, Ticket.Category category, int place) {
        return new TicketImpl(id, eventId, userId, category, place);
    }

}
