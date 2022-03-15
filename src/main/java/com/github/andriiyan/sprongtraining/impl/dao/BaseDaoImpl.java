package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.BaseDao;
import com.github.andriiyan.sprongtraining.api.model.Identifierable;
import com.github.andriiyan.sprongtraining.impl.utils.file.FileUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract class BaseDaoImpl<T extends Identifierable> implements BaseDao<T> {

    @NonNull
    private final Map<Long, T> mStorage = new HashMap<>();
    @Nullable
    private String initializationFilePath;
    @Nullable
    private FileUtils fileUtils;

    @Override
    public T save(T model) {
        long id = mStorage.size() + 1;
        model.setId(id);
        mStorage.put(id, model);
        return model;
    }

    @Override
    public T update(T model) {
        long id = model.getId();
        if (!mStorage.containsKey(id)) {
            return null;
        }
        mStorage.put(id, model);
        return model;
    }

    @Override
    public T findById(long id) {
        return mStorage.get(id);
    }

    @Override
    public Collection<T> findAll() {
        return mStorage.values();
    }

    @Override
    public boolean delete(long id) {
        return mStorage.remove(id) != null;
    }

    @Override
    public void clean() {
        mStorage.clear();
    }

    @Override
    public void initialize() {
        if (initializationFilePath != null && !initializationFilePath.isBlank() && fileUtils != null) {
            initialize(new File(initializationFilePath));
        }
    }

    protected void initialize(File file) {
        if (fileUtils == null) return;
        try {
            final Collection<T> models = fileUtils.readFromFile(file, getType());
            for (T model : models) {
                save(model);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setInitializationFilePath(String initializationFilePath) {
        this.initializationFilePath = initializationFilePath;
    }

    public void setFileUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

}
