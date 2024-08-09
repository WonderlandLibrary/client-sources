/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import org.apache.http.ProtocolException;

public class MalformedChallengeException
extends ProtocolException {
    private static final long serialVersionUID = 814586927989932284L;

    public MalformedChallengeException() {
    }

    public MalformedChallengeException(String string) {
        super(string);
    }

    public MalformedChallengeException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

