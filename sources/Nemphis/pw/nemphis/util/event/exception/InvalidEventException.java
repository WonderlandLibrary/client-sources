/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.event.exception;

public class InvalidEventException
extends RuntimeException {
    public InvalidEventException(Class<?> eventClass) {
        super("The event \"" + eventClass.getSimpleName() + "\" doesn't even implpement \"Event\"!");
    }
}

