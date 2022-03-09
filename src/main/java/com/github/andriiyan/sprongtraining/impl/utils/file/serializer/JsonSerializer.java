package com.github.andriiyan.sprongtraining.impl.utils.file.serializer;

import com.github.andriiyan.sprongtraining.impl.utils.JsonInstanceCreator;
import com.github.andriiyan.sprongtraining.impl.utils.file.Serializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

class JsonSerializer implements Serializer {

    private static final String FILE_EXT = ".json";
    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();

    @Override
    public <T> boolean serialize(Collection<T> models, FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.write(gson.toJson(models).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public <T> Collection<T> deserialize(FileInputStream fileInputStream, JsonInstanceCreator<T> instanceCreator) {
        if (instanceCreator == null) {
            throw new NullPointerException("JsonInstanceCreator should be specified in case of json deserialization");
        }
        final String json;
        try {
            json = new String(fileInputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return instanceCreator.createInstances(json, gson);
    }

    @Override
    public String fileExtension() {
        return FILE_EXT;
    }
}
