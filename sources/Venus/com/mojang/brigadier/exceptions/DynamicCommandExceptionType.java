/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;

public class DynamicCommandExceptionType
implements CommandExceptionType {
    private final Function<Object, Message> function;

    public DynamicCommandExceptionType(Function<Object, Message> function) {
        this.function = function;
    }

    public CommandSyntaxException create(Object object) {
        return new CommandSyntaxException(this, this.function.apply(object));
    }

    public CommandSyntaxException createWithContext(ImmutableStringReader immutableStringReader, Object object) {
        return new CommandSyntaxException(this, this.function.apply(object), immutableStringReader.getString(), immutableStringReader.getCursor());
    }
}

