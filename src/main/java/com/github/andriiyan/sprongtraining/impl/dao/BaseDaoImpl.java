package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.BaseDao;
import com.github.andriiyan.sprongtraining.api.model.Identifierable;
import com.github.andriiyan.sprongtraining.api.storage.Storage;
import com.github.andriiyan.sprongtraining.impl.dao.exception.ModelNotFoundException;
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

    private final Storage<T> storage;

    public BaseDaoImpl(Storage<T> storage) {
        this.storage = storage;
    }

    protected abstract Logger getLogger();

    @Override
    public T save(T model) {
        final T storedModel = storage.save(model);
        getLogger().debug("Model {} has been saved", storedModel);
        return model;
    }

    @Override
    public T update(T model) throws ModelNotFoundException {
        getLogger().debug("Updating model {}", model);
        final T storedModel = storage.update(model);
        getLogger().debug("Model {} has been updated", storedModel);
        return storedModel;
    }

    @Override
    public T findById(long id) {
        final T model = storage.findById(id);
        getLogger().debug("findById invoked with an " + id + " and returned " + model);
        return model;
    }

    @Override
    public Collection<T> findAll() {
        final Collection<T> models = storage.findAll();
        getLogger().debug("findAll invoked and returned {}", models);
        return models;
    }

    @Override
    public boolean delete(long id) {
        final boolean result = storage.delete(id);
        getLogger().debug("delete invoked with {} and return {}", id, result);
        return result;
    }

    @Override
    public void clean() {
        storage.clean();
        getLogger().debug("clean invoked");
    }

}
