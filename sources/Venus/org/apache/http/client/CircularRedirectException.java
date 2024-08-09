/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.client.RedirectException;

public class CircularRedirectException
extends RedirectException {
    private static final long serialVersionUID = 6830063487001091803L;

    public CircularRedirectException() {
    }

    public CircularRedirectException(String string) {
        super(string);
    }

    public CircularRedirectException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

