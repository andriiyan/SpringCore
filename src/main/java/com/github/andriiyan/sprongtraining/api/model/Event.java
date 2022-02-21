package com.github.andriiyan.sprongtraining.api.model;

import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event extends Identifierable {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    long getId();
    void setId(long id);
    String getTitle();
    void setTitle(String title);
    Date getDate();
    void setDate(Date date);
}
