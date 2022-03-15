package com.github.andriiyan.sprongtraining.impl.utils.file;

import org.springframework.lang.NonNull;

import java.io.*;
import java.util.Collection;

/**
 * Utility class for writing and reading info from the file.
 */
public class FileUtils {

    private final Serializer serializer;

    /**
     * Creates instance of the FileUtils class.
     *
     * @param serializer uses for serialization and deserialization process.
     */
    public FileUtils(Serializer serializer) {
        this.serializer = serializer;
    }

    /**
     * Writes models to the file.
     *
     * @param path path of the file where models will be written.
     * @param items models that should be written.
     * @return true if all models were saved, otherwise - false.
     * @throws IOException in case any IO exceptions during serialization.
     */
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

    /**
     * Reads models from the file.
     *
     * @param file file from which models will be read.
     * @param type type of the model.
     * @return [Collection] of deserialized items.
     * @throws IOException in case any IO exception during deserialization.
     * @throws ClassNotFoundException in case model's class is not loaded.
     */
    public <T> Collection<T> readFromFile(
            @NonNull final File file,
            @NonNull final Class<T> type
    ) throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " not found.");
        }
        if (!file.canRead()) {
            throw new IOException("File " + file.getAbsolutePath() + " not readable.");
        }
        final FileInputStream fileInputStream = new FileInputStream(file);
        final Collection<T> readObjects = serializer.deserialize(fileInputStream, type);
        fileInputStream.close();
        return readObjects;
    }

    /**
     * @return file's extension
     */
    @NonNull
    public String fileExtension() {
        return serializer.fileExtension();
    }

}
