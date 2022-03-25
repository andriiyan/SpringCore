package com.github.andriiyan.sprongtraining.impl.dao.exception;

public class ModelNotFoundException extends Throwable {

    public ModelNotFoundException(long modelId, String className) {
        super("Model " + className + " with id=" + modelId + " not found");
    }
}
