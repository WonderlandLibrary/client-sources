package de.tired.api.logger.impl;

import de.tired.api.annotations.LoggerAnnotation;

@LoggerAnnotation()

public enum SuccessLog {

    SUCCESS_LOG;

    public void doLog(String success) {
        System.out.println("[SUCCESS] " + success);
    }

}
