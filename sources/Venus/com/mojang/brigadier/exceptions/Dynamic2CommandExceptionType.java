/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Dynamic2CommandExceptionType
implements CommandExceptionType {
    private final Function function;

    public Dynamic2CommandExceptionType(Function function) {
        this.function = function;
    }

    public CommandSyntaxException create(Object object, Object object2) {
        return new CommandSyntaxException(this, this.function.apply(object, object2));
    }

    public CommandSyntaxException createWithContext(ImmutableStringReader immutableStringReader, Object object, Object object2) {
        return new CommandSyntaxException(this, this.function.apply(object, object2), immutableStringReader.getString(), immutableStringReader.getCursor());
    }

    public static interface Function {
        public Message apply(Object var1, Object var2);
    }
}

