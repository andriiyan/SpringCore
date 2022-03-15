package com.github.andriiyan.sprongtraining.impl.utils.file;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Interface which allow to serialize/deserialize plain java objects.
 */
public interface Serializer {

    /**
     * @return file's extension.
     */
    @NonNull String fileExtension();

    /**
     * Serializes collection of items into file.
     *
     * @param models items that should be serialized.
     * @param outputStream stream in which serialized object should be written.
     * @return true if all object were serialized, otherwise - false.
     */
    <T> boolean serialize(
            @NonNull final Collection<T> models,
            @NonNull final OutputStream outputStream
    );

    /**
     * Deserializes collection of items from the file.
     *
     * @param inputStream stream from which java objects should be deserialized.
     * @return [Collection] of the [Serializable] objects which was deserialized from the file or null if error has
     * happened.
     */
    @Nullable
    <T> Collection<T> deserialize(@NonNull InputStream inputStream, @NonNull Class<T> type);
}
