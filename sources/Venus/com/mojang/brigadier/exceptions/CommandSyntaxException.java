/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.BuiltInExceptions;
import com.mojang.brigadier.exceptions.CommandExceptionType;

public class CommandSyntaxException
extends Exception {
    public static final int CONTEXT_AMOUNT = 10;
    public static boolean ENABLE_COMMAND_STACK_TRACES = true;
    public static BuiltInExceptionProvider BUILT_IN_EXCEPTIONS = new BuiltInExceptions();
    private final CommandExceptionType type;
    private final Message message;
    private final String input;
    private final int cursor;

    public CommandSyntaxException(CommandExceptionType commandExceptionType, Message message) {
        super(message.getString(), null, ENABLE_COMMAND_STACK_TRACES, ENABLE_COMMAND_STACK_TRACES);
        this.type = commandExceptionType;
        this.message = message;
        this.input = null;
        this.cursor = -1;
    }

    public CommandSyntaxException(CommandExceptionType commandExceptionType, Message message, String string, int n) {
        super(message.getString(), null, ENABLE_COMMAND_STACK_TRACES, ENABLE_COMMAND_STACK_TRACES);
        this.type = commandExceptionType;
        this.message = message;
        this.input = string;
        this.cursor = n;
    }

    @Override
    public String getMessage() {
        String string = this.message.getString();
        String string2 = this.getContext();
        if (string2 != null) {
            string = string + " at position " + this.cursor + ": " + string2;
        }
        return string;
    }

    public Message getRawMessage() {
        return this.message;
    }

    public String getContext() {
        if (this.input == null || this.cursor < 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = Math.min(this.input.length(), this.cursor);
        if (n > 10) {
            stringBuilder.append("...");
        }
        stringBuilder.append(this.input.substring(Math.max(0, n - 10), n));
        stringBuilder.append("<--[HERE]");
        return stringBuilder.toString();
    }

    public CommandExceptionType getType() {
        return this.type;
    }

    public String getInput() {
        return this.input;
    }

    public int getCursor() {
        return this.cursor;
    }
}

