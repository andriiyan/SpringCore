package com.github.andriiyan.sprongtraining.impl.utils;

import com.google.gson.Gson;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface JsonInstanceCreator<T> {
    @NonNull
    Collection<T> createInstances(@NonNull String source, @NonNull Gson gson);
}
