package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.storage.Storage;
import com.github.andriiyan.sprongtraining.impl.TestModelsFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoImplTest {

    @Mock
    private Storage<Event> eventStorage;

    @InjectMocks
    private EventDaoImpl eventDao;

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

        Mockito.when(eventStorage.findAll()).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventDao.getEventsByTitle(title, pageSize, pageNum);

        returnedEvents.forEach(event -> Assert.assertTrue(event.getTitle().contains(title)));
        Assert.assertEquals(returningEvents.subList(pageSize * pageNum, pageSize * pageNum + pageSize), returnedEvents);

        Mockito.verify(eventStorage).findAll();
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

        Mockito.when(eventStorage.findAll()).thenReturn(returningEvents);
        final List<Event> returnedEvents = eventDao.getEventsForDay(date, pageSize, pageNum);

        Assert.assertEquals(returningEvents.subList(pageSize * pageNum, pageSize * pageNum + pageSize), returnedEvents);

        Mockito.verify(eventStorage).findAll();
    }

}