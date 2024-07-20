/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opencl;

public class OpenCLException
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OpenCLException() {
    }

    public OpenCLException(String message) {
        super(message);
    }

    public OpenCLException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenCLException(Throwable cause) {
        super(cause);
    }
}

