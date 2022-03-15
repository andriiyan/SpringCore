package com.github.andriiyan.sprongtraining.impl;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.model.ModelsFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class TestModelsFactory {

    public interface CountInstanceFactory<T> {
        T create(int count);
    }

    public static class DefaultEventCountInstanceFactory implements CountInstanceFactory<Event> {

        protected int count(int count) { return count; }

        protected String title(int count) { return "Title #" + count; }

        protected Date date(int count) { return new Date(); }

        @Override
        public Event create(int count) {
            return ModelsFactory.createEvent(count(count), title(count), date(count));
        }
    }

    public static class DefaultTicketCountInstanceFactory implements CountInstanceFactory<Ticket> {

        protected long id(int count) { return count; }

        protected long eventId(int count) { return count; }

        protected long userId(int count) { return count; }

        protected Ticket.Category category(int count) {
            final Ticket.Category[] values = Ticket.Category.values();
            return values[count % values.length];
        }

        protected int place(int count) { return count; }

        @Override
        public Ticket create(int count) {
            return ModelsFactory.createTicket(id(count), eventId(count), userId(count),category(count), place(count));
        }
    }

    public static class DefaultUserCountInstanceFactory implements CountInstanceFactory<User> {

        protected long id(int count) { return count; }

        protected String name(int count) { return "Name #" + count; }

        protected String email(int count) { return "email #" + count; }

        @Override
        public User create(int count) {
            return ModelsFactory.createUser(id(count), name(count), email(count));
        }
    }

    private static <T> List<T> generate(int count, final CountInstanceFactory<T> countInstanceFactory) {
        final List<T> modelList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            modelList.add(countInstanceFactory.create(i));
        }
        return modelList;
    }

    public static List<Event> generateEvents(int count, final CountInstanceFactory<Event> eventCountInstanceFactory) {
        return generate(count, eventCountInstanceFactory);
    }

    public static List<Ticket> generateTickers(int count, final CountInstanceFactory<Ticket> ticketCountInstanceFactory) {
        return generate(count, ticketCountInstanceFactory);
    }

    public static List<User> generateUsers(int count, final CountInstanceFactory<User> userCountInstanceFactory) {
        return generate(count, userCountInstanceFactory);
    }

    public static List<Event> generateEvents(int count) {
        return generateEvents(count, new DefaultEventCountInstanceFactory());
    }

    public static List<Ticket> generateTickets(int count) {
        return generateTickers(count, new DefaultTicketCountInstanceFactory());
    }

    public static List<User> generateUsers(int count) {
        return generateUsers(count, new DefaultUserCountInstanceFactory());
    }

    public static Event generateSingleEvent() {
        return generateEvents(1).get(0);
    }

    public static Ticket generateSingleTicket() {
        return generateTickets(1).get(0);
    }

    public static User generateSingleUser() {
        return generateUsers(1).get(0);
    }

}
