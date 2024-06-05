package de.tired.api.logger;

import de.tired.api.annotations.LoggerAnnotation;

public class Logger {

    public boolean error = getClass().getAnnotation(LoggerAnnotation.class).error();

}
