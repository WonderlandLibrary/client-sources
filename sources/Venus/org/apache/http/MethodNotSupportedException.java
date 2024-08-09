/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.HttpException;

public class MethodNotSupportedException
extends HttpException {
    private static final long serialVersionUID = 3365359036840171201L;

    public MethodNotSupportedException(String string) {
        super(string);
    }

    public MethodNotSupportedException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

