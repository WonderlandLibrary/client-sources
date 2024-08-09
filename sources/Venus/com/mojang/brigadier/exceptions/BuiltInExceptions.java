/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.exceptions;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class BuiltInExceptions
implements BuiltInExceptionProvider {
    private static final Dynamic2CommandExceptionType DOUBLE_TOO_SMALL = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$0);
    private static final Dynamic2CommandExceptionType DOUBLE_TOO_BIG = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$1);
    private static final Dynamic2CommandExceptionType FLOAT_TOO_SMALL = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$2);
    private static final Dynamic2CommandExceptionType FLOAT_TOO_BIG = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$3);
    private static final Dynamic2CommandExceptionType INTEGER_TOO_SMALL = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$4);
    private static final Dynamic2CommandExceptionType INTEGER_TOO_BIG = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$5);
    private static final Dynamic2CommandExceptionType LONG_TOO_SMALL = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$6);
    private static final Dynamic2CommandExceptionType LONG_TOO_BIG = new Dynamic2CommandExceptionType(BuiltInExceptions::lambda$static$7);
    private static final DynamicCommandExceptionType LITERAL_INCORRECT = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$8);
    private static final SimpleCommandExceptionType READER_EXPECTED_START_OF_QUOTE = new SimpleCommandExceptionType(new LiteralMessage("Expected quote to start a string"));
    private static final SimpleCommandExceptionType READER_EXPECTED_END_OF_QUOTE = new SimpleCommandExceptionType(new LiteralMessage("Unclosed quoted string"));
    private static final DynamicCommandExceptionType READER_INVALID_ESCAPE = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$9);
    private static final DynamicCommandExceptionType READER_INVALID_BOOL = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$10);
    private static final DynamicCommandExceptionType READER_INVALID_INT = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$11);
    private static final SimpleCommandExceptionType READER_EXPECTED_INT = new SimpleCommandExceptionType(new LiteralMessage("Expected integer"));
    private static final DynamicCommandExceptionType READER_INVALID_LONG = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$12);
    private static final SimpleCommandExceptionType READER_EXPECTED_LONG = new SimpleCommandExceptionType(new LiteralMessage("Expected long"));
    private static final DynamicCommandExceptionType READER_INVALID_DOUBLE = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$13);
    private static final SimpleCommandExceptionType READER_EXPECTED_DOUBLE = new SimpleCommandExceptionType(new LiteralMessage("Expected double"));
    private static final DynamicCommandExceptionType READER_INVALID_FLOAT = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$14);
    private static final SimpleCommandExceptionType READER_EXPECTED_FLOAT = new SimpleCommandExceptionType(new LiteralMessage("Expected float"));
    private static final SimpleCommandExceptionType READER_EXPECTED_BOOL = new SimpleCommandExceptionType(new LiteralMessage("Expected bool"));
    private static final DynamicCommandExceptionType READER_EXPECTED_SYMBOL = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$15);
    private static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_COMMAND = new SimpleCommandExceptionType(new LiteralMessage("Unknown command"));
    private static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_ARGUMENT = new SimpleCommandExceptionType(new LiteralMessage("Incorrect argument for command"));
    private static final SimpleCommandExceptionType DISPATCHER_EXPECTED_ARGUMENT_SEPARATOR = new SimpleCommandExceptionType(new LiteralMessage("Expected whitespace to end one argument, but found trailing data"));
    private static final DynamicCommandExceptionType DISPATCHER_PARSE_EXCEPTION = new DynamicCommandExceptionType(BuiltInExceptions::lambda$static$16);

    @Override
    public Dynamic2CommandExceptionType doubleTooLow() {
        return DOUBLE_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType doubleTooHigh() {
        return DOUBLE_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooLow() {
        return FLOAT_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooHigh() {
        return FLOAT_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooLow() {
        return INTEGER_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooHigh() {
        return INTEGER_TOO_BIG;
    }

    @Override
    public Dynamic2CommandExceptionType longTooLow() {
        return LONG_TOO_SMALL;
    }

    @Override
    public Dynamic2CommandExceptionType longTooHigh() {
        return LONG_TOO_BIG;
    }

    @Override
    public DynamicCommandExceptionType literalIncorrect() {
        return LITERAL_INCORRECT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedStartOfQuote() {
        return READER_EXPECTED_START_OF_QUOTE;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedEndOfQuote() {
        return READER_EXPECTED_END_OF_QUOTE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidEscape() {
        return READER_INVALID_ESCAPE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidBool() {
        return READER_INVALID_BOOL;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidInt() {
        return READER_INVALID_INT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedInt() {
        return READER_EXPECTED_INT;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidLong() {
        return READER_INVALID_LONG;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedLong() {
        return READER_EXPECTED_LONG;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidDouble() {
        return READER_INVALID_DOUBLE;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedDouble() {
        return READER_EXPECTED_DOUBLE;
    }

    @Override
    public DynamicCommandExceptionType readerInvalidFloat() {
        return READER_INVALID_FLOAT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedFloat() {
        return READER_EXPECTED_FLOAT;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedBool() {
        return READER_EXPECTED_BOOL;
    }

    @Override
    public DynamicCommandExceptionType readerExpectedSymbol() {
        return READER_EXPECTED_SYMBOL;
    }

    @Override
    public SimpleCommandExceptionType dispatcherUnknownCommand() {
        return DISPATCHER_UNKNOWN_COMMAND;
    }

    @Override
    public SimpleCommandExceptionType dispatcherUnknownArgument() {
        return DISPATCHER_UNKNOWN_ARGUMENT;
    }

    @Override
    public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
        return DISPATCHER_EXPECTED_ARGUMENT_SEPARATOR;
    }

    @Override
    public DynamicCommandExceptionType dispatcherParseException() {
        return DISPATCHER_PARSE_EXCEPTION;
    }

    private static Message lambda$static$16(Object object) {
        return new LiteralMessage("Could not parse command: " + object);
    }

    private static Message lambda$static$15(Object object) {
        return new LiteralMessage("Expected '" + object + "'");
    }

    private static Message lambda$static$14(Object object) {
        return new LiteralMessage("Invalid float '" + object + "'");
    }

    private static Message lambda$static$13(Object object) {
        return new LiteralMessage("Invalid double '" + object + "'");
    }

    private static Message lambda$static$12(Object object) {
        return new LiteralMessage("Invalid long '" + object + "'");
    }

    private static Message lambda$static$11(Object object) {
        return new LiteralMessage("Invalid integer '" + object + "'");
    }

    private static Message lambda$static$10(Object object) {
        return new LiteralMessage("Invalid bool, expected true or false but found '" + object + "'");
    }

    private static Message lambda$static$9(Object object) {
        return new LiteralMessage("Invalid escape sequence '" + object + "' in quoted string");
    }

    private static Message lambda$static$8(Object object) {
        return new LiteralMessage("Expected literal " + object);
    }

    private static Message lambda$static$7(Object object, Object object2) {
        return new LiteralMessage("Long must not be more than " + object2 + ", found " + object);
    }

    private static Message lambda$static$6(Object object, Object object2) {
        return new LiteralMessage("Long must not be less than " + object2 + ", found " + object);
    }

    private static Message lambda$static$5(Object object, Object object2) {
        return new LiteralMessage("Integer must not be more than " + object2 + ", found " + object);
    }

    private static Message lambda$static$4(Object object, Object object2) {
        return new LiteralMessage("Integer must not be less than " + object2 + ", found " + object);
    }

    private static Message lambda$static$3(Object object, Object object2) {
        return new LiteralMessage("Float must not be more than " + object2 + ", found " + object);
    }

    private static Message lambda$static$2(Object object, Object object2) {
        return new LiteralMessage("Float must not be less than " + object2 + ", found " + object);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new LiteralMessage("Double must not be more than " + object2 + ", found " + object);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new LiteralMessage("Double must not be less than " + object2 + ", found " + object);
    }
}

