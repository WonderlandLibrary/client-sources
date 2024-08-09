/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SimpleCommandExceptionType
implements CommandExceptionType {
    private final Message message;

    public SimpleCommandExceptionType(Message message) {
        this.message = message;
    }

    public CommandSyntaxException create() {
        return new CommandSyntaxException(this, this.message);
    }

    public CommandSyntaxException createWithContext(ImmutableStringReader immutableStringReader) {
        return new CommandSyntaxException(this, this.message, immutableStringReader.getString(), immutableStringReader.getCursor());
    }

    public String toString() {
        return this.message.getString();
    }
}

