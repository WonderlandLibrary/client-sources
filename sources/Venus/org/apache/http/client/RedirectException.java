/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.ProtocolException;

public class RedirectException
extends ProtocolException {
    private static final long serialVersionUID = 4418824536372559326L;

    public RedirectException() {
    }

    public RedirectException(String string) {
        super(string);
    }

    public RedirectException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

