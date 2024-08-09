/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import org.apache.http.auth.AuthenticationException;

public class NTLMEngineException
extends AuthenticationException {
    private static final long serialVersionUID = 6027981323731768824L;

    public NTLMEngineException() {
    }

    public NTLMEngineException(String string) {
        super(string);
    }

    public NTLMEngineException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

