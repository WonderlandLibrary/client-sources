/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.nio.charset.CharacterCodingException;

public class MessageConstraintException
extends CharacterCodingException {
    private static final long serialVersionUID = 6077207720446368695L;
    private final String message;

    public MessageConstraintException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

