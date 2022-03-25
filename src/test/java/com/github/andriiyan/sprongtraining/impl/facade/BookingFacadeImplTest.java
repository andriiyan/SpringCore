package com.github.andriiyan.sprongtraining.impl.facade;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.service.EventService;
import com.github.andriiyan.sprongtraining.api.service.TicketService;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.dao.exception.ModelNotFoundException;
import com.github.andriiyan.sprongtraining.impl.model.EventEntity;
import com.github.andriiyan.sprongtraining.impl.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeImplTest {

    @Mock
    private EventService eventService;
    @Mock
    private TicketService ticketService;
    @Mock
    private UserService userService;

    @InjectMocks
    private BookingFacadeImpl bookingFacade;

    /**
     * To make sure implementation not tied to some particular values.
     */
    private final Random random = new Random(System.currentTimeMillis());

    @Test
    public void getEventById_should_be_delegated() {
        Event returningEvent = Mockito.mock(Event.class);
        long eventId = random.nextLong();

        Mockito.when(eventService.getEventById(eventId)).thenReturn(returningEvent);

        Event facadeEvent = bookingFacade.getEventById(eventId);
        Assert.assertEquals(returningEvent, facadeEvent);

        Mockito.verify(eventService).getEventById(eventId);
        //Mockito.verify(bookingFacade).getEventById(eventId);
    }

    @Test
    public void getEventsByTitle_should_be_delegated() {
        Event event = Mockito.mock(Event.class);
        String title = String.valueOf(random.nextInt());
        int pageSize = random.nextInt();
        int pageNum = random.nextInt();
        List<Event> returningEvents = List.of(event);

        Mockito.when(eventService.getEventsByTitle(title, pageSize, pageNum)).thenReturn(returningEvents);

        List<Event> facadeEvents = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        Assert.assertEquals(returningEvents, facadeEvents);

        Mockito.verify(eventService).getEventsByTitle(title, pageSize, pageNum);
        //Mockito.verify(bookingFacade).getEventsByTitle(title, pageSize, pageNum);
    }

    @Test
    public void getEventsForDay_should_be_delegated() {
        Event event = Mockito.mock(Event.class);
        Date date = new Date(random.nextLong());
        int pageSize = random.nextInt();
        int pageNum = random.nextInt();
        List<Event> returningEvents = List.of(event);

        Mockito.when(eventService.getEventsForDay(date, pageSize, pageNum)).thenReturn(returningEvents);

        List<Event> facadeEvents = bookingFacade.getEventsForDay(date, pageSize, pageNum);
        Assert.assertEquals(returningEvents, facadeEvents);

        Mockito.verify(eventService).getEventsForDay(date, pageSize, pageNum);
    }

    @Test
    public void createEvent_should_be_delegated() {
        Event returningEvent = Mockito.mock(Event.class);
        Event creatingEvent = Mockito.mock(Event.class);
        Mockito.when(eventService.createEvent(creatingEvent)).thenReturn(returningEvent);

        Event facadeEvent = bookingFacade.createEvent(creatingEvent);
        Assert.assertEquals(returningEvent, facadeEvent);

        Mockito.verify(eventService).createEvent(creatingEvent);
    }

    @Test
    public void updateEvent_should_be_delegated() throws ModelNotFoundException {
        Event returningEvent = Mockito.mock(Event.class);
        Event updatingEvent = Mockito.mock(Event.class);
        Mockito.when(eventService.updateEvent(updatingEvent)).thenReturn(returningEvent);

        Event facadeEvent = bookingFacade.updateEvent(updatingEvent);
        Assert.assertEquals(returningEvent, facadeEvent);

        Mockito.verify(eventService).updateEvent(updatingEvent);
    }

    @Test(expected = ModelNotFoundException.class)
    public void updateEvent_should_throw_an_exception() throws ModelNotFoundException {
        long id = 100;
        Event updatingEvent = Mockito.mock(Event.class);
        Mockito.when(eventService.updateEvent(updatingEvent)).thenThrow(new ModelNotFoundException(id, EventEntity.class.getName()));

        bookingFacade.updateEvent(updatingEvent);
    }

    @Test
    public void deleteEvent_should_be_delegated() {
        long deletingEventId = random.nextLong();
        Mockito.when(eventService.deleteEvent(deletingEventId)).thenReturn(true);

        boolean facadeResult = bookingFacade.deleteEvent(deletingEventId);
        Assert.assertTrue(facadeResult);

        Mockito.verify(eventService).deleteEvent(deletingEventId);
    }

    @Test
    public void getUserById_should_be_delegated() {
        User returningUser = Mockito.mock(User.class);
        long userId = random.nextLong();
        Mockito.when(userService.getUserById(userId)).thenReturn(returningUser);

        User facadeUser = bookingFacade.getUserById(userId);
        Assert.assertEquals(returningUser, facadeUser);

        Mockito.verify(userService).getUserById(userId);
    }

    @Test
    public void getUserByEmail_should_be_delegated() {
        User returningUser = Mockito.mock(User.class);
        String userEmail = String.valueOf(random.nextLong());
        Mockito.when(userService.getUserByEmail(userEmail)).thenReturn(returningUser);

        User facadeUser = bookingFacade.getUserByEmail(userEmail);
        Assert.assertEquals(returningUser, facadeUser);

        Mockito.verify(userService).getUserByEmail(userEmail);
    }

    @Test
    public void getUsersByName_should_be_delegated() {
        List<User> returningUsers = List.of(Mockito.mock(User.class));
        String userName = String.valueOf(random.nextLong());
        int pageSize = random.nextInt();
        int pageNum = random.nextInt();
        Mockito.when(userService.getUsersByName(userName, pageSize, pageNum)).thenReturn(returningUsers);

        List<User> facadeUsers = bookingFacade.getUsersByName(userName, pageSize, pageNum);
        Assert.assertEquals(returningUsers, facadeUsers);

        Mockito.verify(userService).getUsersByName(userName, pageSize, pageNum);
    }


    @Test
    public void createUser_should_be_delegated() {
        User returningUser = Mockito.mock(User.class);
        User creatingUser = Mockito.mock(User.class);
        Mockito.when(userService.createUser(creatingUser)).thenReturn(returningUser);

        User facadeUser = bookingFacade.createUser(creatingUser);
        Assert.assertEquals(returningUser, facadeUser);

        Mockito.verify(userService).createUser(creatingUser);
    }

    @Test
    public void updateUser_should_be_delegated() throws ModelNotFoundException {
        User returningUser = Mockito.mock(User.class);
        User updatingUser = Mockito.mock(User.class);
        Mockito.when(userService.updateUser(updatingUser)).thenReturn(returningUser);

        User facadeUser = bookingFacade.updateUser(updatingUser);
        Assert.assertEquals(returningUser, facadeUser);

        Mockito.verify(userService).updateUser(updatingUser);
    }

    @Test(expected = ModelNotFoundException.class)
    public void updateUser_should_throw_an_exception() throws ModelNotFoundException {
        User updatingUser = Mockito.mock(User.class);
        long id = 10;
        Mockito.when(userService.updateUser(updatingUser)).thenThrow(new ModelNotFoundException(id, UserEntity.class.getName()));

        bookingFacade.updateUser(updatingUser);
    }

    @Test
    public void deleteUser_should_be_delegated() {
        long deletingUserId = random.nextLong();
        Mockito.when(userService.deleteUser(deletingUserId)).thenReturn(true);

        boolean facadeResult = bookingFacade.deleteUser(deletingUserId);
        Assert.assertTrue(facadeResult);

        Mockito.verify(userService).deleteUser(deletingUserId);
    }

    @Test
    public void bookTicket_should_be_delegated() {
        long userId = random.nextLong();
        long eventId = random.nextLong();
        int place = random.nextInt();
        Ticket returningTicket = Mockito.mock(Ticket.class);

        Mockito.when(ticketService.bookTicket(userId, eventId, place, Ticket.Category.BAR)).thenReturn(returningTicket);

        Ticket facadeTicket = bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.BAR);
        Assert.assertEquals(returningTicket, facadeTicket);

        Mockito.verify(ticketService).bookTicket(userId, eventId, place, Ticket.Category.BAR);
    }

    @Test
    public void getBookedTickets_should_be_delegated() {
        User user = Mockito.mock(User.class);
        int pageSize = random.nextInt();
        int pageNum = random.nextInt();
        List<Ticket> returningTickets = List.of(Mockito.mock(Ticket.class));

        Mockito.when(ticketService.getBookedTickets(user, pageSize, pageNum)).thenReturn(returningTickets);

        List<Ticket> facadeTickets = bookingFacade.getBookedTickets(user, pageSize, pageNum);
        Assert.assertEquals(returningTickets, facadeTickets);

        Mockito.verify(ticketService).getBookedTickets(user, pageSize, pageNum);
    }

    @Test
    public void getBookedTicketsEvent_should_be_delegated() {
        Event event = Mockito.mock(Event.class);
        int pageSize = random.nextInt();
        int pageNum = random.nextInt();
        List<Ticket> returningTickets = List.of(Mockito.mock(Ticket.class));

        Mockito.when(ticketService.getBookedTickets(event, pageSize, pageNum)).thenReturn(returningTickets);

        List<Ticket> facadeTickets = bookingFacade.getBookedTickets(event, pageSize, pageNum);
        Assert.assertEquals(returningTickets, facadeTickets);

        Mockito.verify(ticketService).getBookedTickets(event, pageSize, pageNum);
    }

    @Test
    public void cancelTicket_should_be_delegated() {
        long ticketId = random.nextLong();

        Mockito.when(ticketService.cancelTicket(ticketId)).thenReturn(true);

        boolean facadeResult = bookingFacade.cancelTicket(ticketId);
        Assert.assertTrue(facadeResult);

        Mockito.verify(ticketService).cancelTicket(ticketId);
    }

}