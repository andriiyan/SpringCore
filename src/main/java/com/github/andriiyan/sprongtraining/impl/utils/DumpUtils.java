package com.github.andriiyan.sprongtraining.impl.utils;

import com.github.andriiyan.sprongtraining.api.model.Event;
import com.github.andriiyan.sprongtraining.api.model.Ticket;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.model.ModelsFactory;
import com.github.andriiyan.sprongtraining.impl.utils.file.FileUtils;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class DumpUtils {

    @NonNull
    private final FileUtils fileUtils;
    @NonNull
    private final String rootFolder;
    private final int itemCount;

    public DumpUtils(@NonNull String rootFolder, @NonNull FileUtils fileUtils, int itemCount) {
        this.rootFolder = rootFolder;
        this.fileUtils = fileUtils;
        this.itemCount = itemCount;
    }

    public void dump() {
        try {
            dumpUsers();
            dumpEvents();
            dumpTickets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dumpEvents() throws IOException {
        Collection<Event> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(ModelsFactory.createEvent(i, "Test #" + i, new Date()));
        }
        fileUtils.writeIntoFile(rootFolder + "events" + suffix(), events);
    }

    private void dumpTickets() throws IOException {
        Collection<Ticket> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(ModelsFactory.createTicket(i, i, i, Ticket.Category.PREMIUM, i));
        }
        fileUtils.writeIntoFile(rootFolder + "tickets" + suffix(), events);
    }

    private void dumpUsers() throws IOException {
        Collection<User> events = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            events.add(ModelsFactory.createUser(i, "Test #" + i, "email #" + i));
        }
        fileUtils.writeIntoFile(rootFolder + "users" + suffix(), events);
    }

    @NonNull
    private String suffix() {
        return fileUtils.fileExtension();
    }

}
