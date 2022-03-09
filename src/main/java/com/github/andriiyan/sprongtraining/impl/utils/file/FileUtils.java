package com.github.andriiyan.sprongtraining.impl.utils.file;

import com.github.andriiyan.sprongtraining.impl.utils.JsonInstanceCreator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.*;
import java.util.Collection;

public class FileUtils {

    private final Serializer serializer;

    public FileUtils(Serializer serializer) {
        this.serializer = serializer;
    }

    public <T> boolean writeIntoFile(final String path, final Collection<T> items) throws IOException {
        final File file = new File(path);
        // create new file and set it's permission to the writable
        if (!file.exists()) {
            final File folder = file.getParentFile();
            if (!folder.exists() && !folder.mkdirs()) {
                return false;
            }
            if (!file.createNewFile()) return false;
        }
        if (!file.setWritable(true)) return false;
        if (!file.setReadable(true)) return false;
        final FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        final boolean result = serializer.serialize(items, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return result;
    }

    public <T> Collection<T> readFromFile(
            @NonNull final File file,
            @Nullable JsonInstanceCreator<T> instanceCreator
    ) throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " not found.");
        }
        if (!file.canRead()) {
            throw new IOException("File " + file.getAbsolutePath() + " not readable.");
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final Collection<T> readObjects = serializer.deserialize(fileInputStream, instanceCreator);
        fileInputStream.close();
        return readObjects;
    }

    @NonNull
    public String fileExtension() {
        return serializer.fileExtension();
    }

}
