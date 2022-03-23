package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.EventDao;
import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.impl.TestModelsFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {

    @Mock
    private EventDao eventDao;

    // TODO: 3/20/2022 - Please use @InjectMocks where possible
    @InjectMocks
    private EventServiceImpl eventService;
/*
    @Before
    public void init() {
        eventService = new EventServiceImpl();
        eventService.setEventDao(eventDao);
    }*/

    @Test
    public void getEventById_shouldReturnSameModelAsDao() {
        final long eventId = 100;
        final Event returningEvent = TestModelsFactory.generateSingleEvent();
        Mockito.when(eventDao.findById(eventId)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.getEventById(eventId);
        Assert.assertEquals(returningEvent, returnedEvent);
        Mockito.verify(eventDao).findById(eventId);
    }

    @Test
    public void getEventsByTitle_shouldReturnCorrectAnswer() {
        final String title = "test title";
        final int pageSize = 3;
        final int pageNum = 1;

        // generating events for the 5 pages
        final List<Event> returningEvents = TestModelsFactory.generateEvents(5 * pageSize, new TestModelsFactory.DefaultEventCountInstanceFactory() {
            @Override
            protected String title(int count) {
                return title + count;
            }
        });

        Mockito.when(eventDao.findAll()).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventService.getEventsByTitle(title, pageSize, pageNum);

        returnedEvents.forEach(event -> Assert.assertTrue(event.getTitle().contains(title)));
        Assert.assertEquals(returningEvents.subList(pageSize * pageNum, pageSize * pageNum + pageSize), returnedEvents);

        Mockito.verify(eventDao).findAll();
    }

    @Test
    public void getEventsForDay_shouldReturnCorrectAnswer() {
        final Date date = new Date() ;
        final int pageSize = 3;
        final int pageNum = 1;

        // generating events for the 5 pages
        final List<Event> returningEvents = TestModelsFactory.generateEvents(5 * pageSize, new TestModelsFactory.DefaultEventCountInstanceFactory() {
            @Override
            protected Date date(int count) {
                return date;
            }
        });

        Mockito.when(eventDao.findAll()).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventService.getEventsForDay(date, pageSize, pageNum);

        Assert.assertEquals(returningEvents.subList(pageSize * pageNum, pageSize * pageNum + pageSize), returnedEvents);

        Mockito.verify(eventDao).findAll();
    }

    @Test
    public void createEvent_shouldReturnSameModelAsDao() {
        final Event savingEvent = TestModelsFactory.generateSingleEvent();
        final Event returningEvent = TestModelsFactory.generateSingleEvent();
        Mockito.when(eventDao.save(savingEvent)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.createEvent(savingEvent);
        Assert.assertEquals(returningEvent, returnedEvent);
        Mockito.verify(eventDao).save(savingEvent);
    }

    @Test
    public void updateEvent_shouldReturnSameModelAsDao() {
        final Event updatingEvent = TestModelsFactory.generateSingleEvent();
        final Event returningEvent = TestModelsFactory.generateSingleEvent();
        Mockito.when(eventDao.update(updatingEvent)).thenReturn(returningEvent);

        final Event returnedEvent = eventService.updateEvent(updatingEvent);
        Assert.assertEquals(returningEvent, returnedEvent);
        Mockito.verify(eventDao).update(updatingEvent);
    }

    @Test
    public void delete_shouldReturnSameModelAsDao() {
        final long eventId = 100;
        Mockito.when(eventDao.delete(eventId)).thenReturn(true);

        Assert.assertTrue(eventService.deleteEvent(eventId));
        Mockito.verify(eventDao).delete(eventId);
    }

}