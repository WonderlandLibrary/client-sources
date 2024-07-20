/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import net.minecraft.command.SyntaxErrorException;

public class WrongUsageException
extends SyntaxErrorException {
    public WrongUsageException(String message, Object ... replacements) {
        super(message, replacements);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

