/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.MessageFormatter;

public class NormalizedParameters {
    final String message;
    final Object[] arguments;
    final Throwable throwable;

    public NormalizedParameters(String string, Object[] objectArray, Throwable throwable) {
        this.message = string;
        this.arguments = objectArray;
        this.throwable = throwable;
    }

    public NormalizedParameters(String string, Object[] objectArray) {
        this(string, objectArray, null);
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getArguments() {
        return this.arguments;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public static Throwable getThrowableCandidate(Object[] objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            return null;
        }
        Object object = objectArray[objectArray.length - 1];
        if (object instanceof Throwable) {
            return (Throwable)object;
        }
        return null;
    }

    public static Object[] trimmedCopy(Object[] objectArray) {
        if (objectArray == null || objectArray.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        int n = objectArray.length - 1;
        Object[] objectArray2 = new Object[n];
        if (n > 0) {
            System.arraycopy(objectArray, 0, objectArray2, 0, n);
        }
        return objectArray2;
    }

    public static NormalizedParameters normalize(String string, Object[] objectArray, Throwable throwable) {
        if (throwable != null) {
            return new NormalizedParameters(string, objectArray, throwable);
        }
        if (objectArray == null || objectArray.length == 0) {
            return new NormalizedParameters(string, objectArray, throwable);
        }
        Throwable throwable2 = NormalizedParameters.getThrowableCandidate(objectArray);
        if (throwable2 != null) {
            Object[] objectArray2 = MessageFormatter.trimmedCopy(objectArray);
            return new NormalizedParameters(string, objectArray2, throwable2);
        }
        return new NormalizedParameters(string, objectArray);
    }

    public static NormalizedParameters normalize(LoggingEvent loggingEvent) {
        return NormalizedParameters.normalize(loggingEvent.getMessage(), loggingEvent.getArgumentArray(), loggingEvent.getThrowable());
    }
}

