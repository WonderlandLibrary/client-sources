/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl;

public class LWJGLException
extends Exception {
    private static final long serialVersionUID = 1L;

    public LWJGLException() {
    }

    public LWJGLException(String msg) {
        super(msg);
    }

    public LWJGLException(String message, Throwable cause) {
        super(message, cause);
    }

    public LWJGLException(Throwable cause) {
        super(cause);
    }
}

