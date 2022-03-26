package com.github.andriiyan.sprongtraining.impl.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.io.*;
import java.util.Collection;

/**
 * Utility class for writing and reading info from the file.
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

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
        boolean isReady = true;
        final File file = new File(path);
        if (!file.exists()) {
            final File folder = file.getParentFile();
            isReady = (folder.exists() || folder.mkdirs()) && file.createNewFile();
        }
        if (isReady) {
            isReady = file.setReadable(true) && file.setWritable(true);
        }
        if (isReady) {
            try (var fileOutputStream = new FileOutputStream(file, false)){
                isReady = serializer.serialize(items, fileOutputStream);
            } catch (Exception e) {
                logger.error("Could not write to file", e);
                isReady = false;
            }
        }
        return isReady;
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
            logger.error("File {} is not exists", file.getAbsoluteFile());
            throw new FileNotFoundException("File " + file.getAbsolutePath() + " not found.");
        }
        if (!file.canRead()) {
            logger.error("File {} is not readable", file.getAbsoluteFile());
            throw new IOException("File " + file.getAbsolutePath() + " not readable.");
        }
        try (var fileInputStream = new FileInputStream(file)) {
            return serializer.deserialize(fileInputStream, type);
        } catch (Exception e) {
            logger.error("Could not read from the file", e);
            throw e;
        }
    }

    /**
     * @return file's extension
     */
    @NonNull
    public String fileExtension() {
        return serializer.fileExtension();
    }

}
