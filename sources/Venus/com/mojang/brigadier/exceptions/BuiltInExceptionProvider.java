/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public interface BuiltInExceptionProvider {
    public Dynamic2CommandExceptionType doubleTooLow();

    public Dynamic2CommandExceptionType doubleTooHigh();

    public Dynamic2CommandExceptionType floatTooLow();

    public Dynamic2CommandExceptionType floatTooHigh();

    public Dynamic2CommandExceptionType integerTooLow();

    public Dynamic2CommandExceptionType integerTooHigh();

    public Dynamic2CommandExceptionType longTooLow();

    public Dynamic2CommandExceptionType longTooHigh();

    public DynamicCommandExceptionType literalIncorrect();

    public SimpleCommandExceptionType readerExpectedStartOfQuote();

    public SimpleCommandExceptionType readerExpectedEndOfQuote();

    public DynamicCommandExceptionType readerInvalidEscape();

    public DynamicCommandExceptionType readerInvalidBool();

    public DynamicCommandExceptionType readerInvalidInt();

    public SimpleCommandExceptionType readerExpectedInt();

    public DynamicCommandExceptionType readerInvalidLong();

    public SimpleCommandExceptionType readerExpectedLong();

    public DynamicCommandExceptionType readerInvalidDouble();

    public SimpleCommandExceptionType readerExpectedDouble();

    public DynamicCommandExceptionType readerInvalidFloat();

    public SimpleCommandExceptionType readerExpectedFloat();

    public SimpleCommandExceptionType readerExpectedBool();

    public DynamicCommandExceptionType readerExpectedSymbol();

    public SimpleCommandExceptionType dispatcherUnknownCommand();

    public SimpleCommandExceptionType dispatcherUnknownArgument();

    public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator();

    public DynamicCommandExceptionType dispatcherParseException();
}

