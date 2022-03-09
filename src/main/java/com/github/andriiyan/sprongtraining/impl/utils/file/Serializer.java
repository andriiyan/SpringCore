package com.github.andriiyan.sprongtraining.impl.utils.file;

import com.github.andriiyan.sprongtraining.impl.utils.JsonInstanceCreator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;

/**
 * Interface which allow to serialize/deserialize plain java objects.
 */
public interface Serializer {

    @NonNull String fileExtension();

    /**
     * Serializes collection of items into file.
     *
     * @param models items that should be serialized.
     * @param fileOutputStream stream in which serialized object should be written.
     * @return true if all object were serialized, otherwise - false.
     */
    <T> boolean serialize(
            @NonNull final Collection<T> models,
            @NonNull final FileOutputStream fileOutputStream
    );

    /**
     * Deserializes collection of items from the file.
     *
     * @param fileInputStream stream from which java objects should be deserialized.
     * @return [Collection] of the [Serializable] objects which was deserialized from the file or null if error has
     * happened.
     */
    @Nullable
    <T> Collection<T> deserialize(@NonNull FileInputStream fileInputStream, @Nullable JsonInstanceCreator<T> instanceCreator);
}
