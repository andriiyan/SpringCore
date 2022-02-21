package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.BaseDao;
import com.github.andriiyan.sprongtraining.api.model.Identifierable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class BaseDaoImpl<T extends Identifierable> implements BaseDao<T> {

    protected final Map<Long, T> mStorage = new HashMap<>();

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
}
