package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.BaseDao;
import com.github.andriiyan.sprongtraining.api.model.Identifierable;
import com.github.andriiyan.sprongtraining.impl.utils.file.FileUtils;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// TODO: 3/20/2022 - any reason in using default access modifier here and in other similar cases?
abstract class BaseDaoImpl<T extends Identifierable> implements BaseDao<T> {

    // TODO: 3/20/2022 - See Main task: 4. Storage should be implemented as a separate spring bean. Implement the ability to initialize storage with some prepared data from the file during the application start (use spring bean post-processing features). Path to the concrete file should be set using property placeholder and external property file.
    @NonNull
    private final Map<Long, T> mStorage = new HashMap<>();
    @Nullable
    private String initializationFilePath;
    @Nullable
    private FileUtils fileUtils;

    protected abstract Logger getLogger();

    @Override
    public T save(T model) {
        // TODO: 3/20/2022 - avoid string concatenation in logging, use parametrized templates instead
        getLogger().info("Saving model {}", model);
        // TODO: 3/20/2022 - What if you have 3 item in storage, and remove one with id 1? You next id will be 3, so you'll end up with two elements with same id
        long id = mStorage.size() + 1;
        model.setId(id);
        mStorage.put(id, model);
        getLogger().info("Model " + model + " has been saved");
        return model;
    }

    @Override
    public T update(T model) {
        getLogger().info("Updating model " + model.toString());
        long id = model.getId();
        // TODO: 3/20/2022 - even if there's no update, client will think everything went fine. It would be better to throw some exception here
        if (!mStorage.containsKey(id)) {
            // TODO: 3/20/2022 - for readability it's better to follow single return rule
            return null;
        }
        mStorage.put(id, model);
        getLogger().info("Model " + model + " has been updated");
        return model;
    }

    @Override
    public T findById(long id) {
        final T model = mStorage.get(id);
        getLogger().info("findById invoked with an " + id + " and returned " + model);
        return model;
    }

    @Override
    public Collection<T> findAll() {
        final Collection<T> models = mStorage.values();
        getLogger().info("findAll invoked and returned " + models);
        return models;
    }

    @Override
    public boolean delete(long id) {
        final boolean result = mStorage.remove(id) != null;
        getLogger().info("delete invoked with " + id + " and return " + result);
        return result;
    }

    @Override
    public void clean() {
        mStorage.clear();
        getLogger().info("clean invoked");
    }

    @Override
    public void initialize() {
        if (initializationFilePath != null && !initializationFilePath.isBlank() && fileUtils != null) {
            initialize(new File(initializationFilePath));
        }
    }

    protected void initialize(File file) {
        if (fileUtils == null) return;
        getLogger().info("initialization invoked with path: " + initializationFilePath);
        try {
            final Collection<T> models = fileUtils.readFromFile(file, getType());
            for (T model : models) {
                save(model);
            }
            getLogger().info("storage was initialized with " + models.size() + " items");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            getLogger().error(e.toString());
        }
    }

    public void setInitializationFilePath(String initializationFilePath) {
        this.initializationFilePath = initializationFilePath;
    }

    public void setFileUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

}
