/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import net.minecraft.command.CommandException;

public class SyntaxErrorException
extends CommandException {
    public SyntaxErrorException() {
        this("commands.generic.snytax", new Object[0]);
    }

    public SyntaxErrorException(String message, Object ... replacements) {
        super(message, replacements);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

