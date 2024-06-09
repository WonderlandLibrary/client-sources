/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.event.exception;

import java.lang.reflect.Method;

public class InvalidEventMethodException
extends RuntimeException {
    public InvalidEventMethodException(Method method) {
        super("The method \"" + method.getName() + "\" got the EventListener annotation, but no event?!");
    }
}

