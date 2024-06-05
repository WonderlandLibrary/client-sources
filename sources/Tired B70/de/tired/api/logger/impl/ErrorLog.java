package de.tired.api.logger.impl;

import de.tired.api.annotations.LoggerAnnotation;

@LoggerAnnotation(error = true)

public enum ErrorLog {

    ERROR_LOG;

    public void doLog(String error) {
        System.out.println("[ERROR] " + error);
    }

}
