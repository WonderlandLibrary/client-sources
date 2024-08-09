/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.util.text.TranslationTextComponent;

public class TranslatableExceptionProvider
implements BuiltInExceptionProvider {
    private static final Dynamic2CommandExceptionType DOUBLE_TOO_LOW = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$0);
    private static final Dynamic2CommandExceptionType DOUBLE_TOO_HIGH = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$1);
    private static final Dynamic2CommandExceptionType FLOAT_TOO_LOW = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$2);
    private static final Dynamic2CommandExceptionType FLOAT_TOO_HIGH = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$3);
    private static final Dynamic2CommandExceptionType INTEGER_TOO_LOW = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$4);
    private static final Dynamic2CommandExceptionType INTEGER_TOO_HIGH = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$5);
    private static final Dynamic2CommandExceptionType field_218035_g = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$6);
    private static final Dynamic2CommandExceptionType field_218036_h = new Dynamic2CommandExceptionType(TranslatableExceptionProvider::lambda$static$7);
    private static final DynamicCommandExceptionType LITERAL_INCORRECT = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$8);
    private static final SimpleCommandExceptionType READER_EXPECTED_START_OF_QUOTE = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.quote.expected.start"));
    private static final SimpleCommandExceptionType READER_EXPECTED_END_OF_QUOTE = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.quote.expected.end"));
    private static final DynamicCommandExceptionType READER_INVALID_ESCAPE = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$9);
    private static final DynamicCommandExceptionType READER_INVALID_BOOL = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$10);
    private static final DynamicCommandExceptionType READER_INVALID_INT = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$11);
    private static final SimpleCommandExceptionType READER_EXPECTED_INT = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.int.expected"));
    private static final DynamicCommandExceptionType field_218037_p = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$12);
    private static final SimpleCommandExceptionType field_218038_q = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.long.expected"));
    private static final DynamicCommandExceptionType READER_INVALID_DOUBLE = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$13);
    private static final SimpleCommandExceptionType READER_EXPECTED_DOUBLE = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.double.expected"));
    private static final DynamicCommandExceptionType READER_INVALID_FLOAT = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$14);
    private static final SimpleCommandExceptionType READER_EXPECTED_FLOAT = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.float.expected"));
    private static final SimpleCommandExceptionType READER_EXPECTED_BOOL = new SimpleCommandExceptionType(new TranslationTextComponent("parsing.bool.expected"));
    private static final DynamicCommandExceptionType READER_EXPECTED_SYMBOL = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$15);
    private static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_COMMAND = new SimpleCommandExceptionType(new TranslationTextComponent("command.unknown.command"));
    private static final SimpleCommandExceptionType DISPATCHER_UNKNOWN_ARGUMENT = new SimpleCommandExceptionType(new TranslationTextComponent("command.unknown.argument"));
    private static final SimpleCommandExceptionType DISPATCHER_EXPECTED_ARGUMENT_SEPARATOR = new SimpleCommandExceptionType(new TranslationTextComponent("command.expected.separator"));
    private static final DynamicCommandExceptionType DISPATCHER_PARSE_EXCEPTION = new DynamicCommandExceptionType(TranslatableExceptionProvider::lambda$static$16);

    @Override
    public Dynamic2CommandExceptionType doubleTooLow() {
        return DOUBLE_TOO_LOW;
    }

    @Override
    public Dynamic2CommandExceptionType doubleTooHigh() {
        return DOUBLE_TOO_HIGH;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooLow() {
        return FLOAT_TOO_LOW;
    }

    @Override
    public Dynamic2CommandExceptionType floatTooHigh() {
        return FLOAT_TOO_HIGH;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooLow() {
        return INTEGER_TOO_LOW;
    }

    @Override
    public Dynamic2CommandExceptionType integerTooHigh() {
        return INTEGER_TOO_HIGH;
    }

    @Override
    public Dynamic2CommandExceptionType longTooLow() {
        return field_218035_g;
    }

    @Override
    public Dynamic2CommandExceptionType longTooHigh() {
        return field_218036_h;
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
        return field_218037_p;
    }

    @Override
    public SimpleCommandExceptionType readerExpectedLong() {
        return field_218038_q;
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
        return new TranslationTextComponent("command.exception", object);
    }

    private static Message lambda$static$15(Object object) {
        return new TranslationTextComponent("parsing.expected", object);
    }

    private static Message lambda$static$14(Object object) {
        return new TranslationTextComponent("parsing.float.invalid", object);
    }

    private static Message lambda$static$13(Object object) {
        return new TranslationTextComponent("parsing.double.invalid", object);
    }

    private static Message lambda$static$12(Object object) {
        return new TranslationTextComponent("parsing.long.invalid", object);
    }

    private static Message lambda$static$11(Object object) {
        return new TranslationTextComponent("parsing.int.invalid", object);
    }

    private static Message lambda$static$10(Object object) {
        return new TranslationTextComponent("parsing.bool.invalid", object);
    }

    private static Message lambda$static$9(Object object) {
        return new TranslationTextComponent("parsing.quote.escape", object);
    }

    private static Message lambda$static$8(Object object) {
        return new TranslationTextComponent("argument.literal.incorrect", object);
    }

    private static Message lambda$static$7(Object object, Object object2) {
        return new TranslationTextComponent("argument.long.big", object2, object);
    }

    private static Message lambda$static$6(Object object, Object object2) {
        return new TranslationTextComponent("argument.long.low", object2, object);
    }

    private static Message lambda$static$5(Object object, Object object2) {
        return new TranslationTextComponent("argument.integer.big", object2, object);
    }

    private static Message lambda$static$4(Object object, Object object2) {
        return new TranslationTextComponent("argument.integer.low", object2, object);
    }

    private static Message lambda$static$3(Object object, Object object2) {
        return new TranslationTextComponent("argument.float.big", object2, object);
    }

    private static Message lambda$static$2(Object object, Object object2) {
        return new TranslationTextComponent("argument.float.low", object2, object);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new TranslationTextComponent("argument.double.big", object2, object);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("argument.double.low", object2, object);
    }
}

