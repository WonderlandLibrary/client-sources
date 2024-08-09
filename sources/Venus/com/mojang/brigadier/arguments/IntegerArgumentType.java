/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.brigadier.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntegerArgumentType
implements ArgumentType<Integer> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0", "123", "-123");
    private final int minimum;
    private final int maximum;

    private IntegerArgumentType(int n, int n2) {
        this.minimum = n;
        this.maximum = n2;
    }

    public static IntegerArgumentType integer() {
        return IntegerArgumentType.integer(Integer.MIN_VALUE);
    }

    public static IntegerArgumentType integer(int n) {
        return IntegerArgumentType.integer(n, Integer.MAX_VALUE);
    }

    public static IntegerArgumentType integer(int n, int n2) {
        return new IntegerArgumentType(n, n2);
    }

    public static int getInteger(CommandContext<?> commandContext, String string) {
        return commandContext.getArgument(string, Integer.TYPE);
    }

    public int getMinimum() {
        return this.minimum;
    }

    public int getMaximum() {
        return this.maximum;
    }

    @Override
    public Integer parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        int n2 = stringReader.readInt();
        if (n2 < this.minimum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooLow().createWithContext(stringReader, n2, this.minimum);
        }
        if (n2 > this.maximum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooHigh().createWithContext(stringReader, n2, this.maximum);
        }
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof IntegerArgumentType)) {
            return true;
        }
        IntegerArgumentType integerArgumentType = (IntegerArgumentType)object;
        return this.maximum == integerArgumentType.maximum && this.minimum == integerArgumentType.minimum;
    }

    public int hashCode() {
        return 31 * this.minimum + this.maximum;
    }

    public String toString() {
        if (this.minimum == Integer.MIN_VALUE && this.maximum == Integer.MAX_VALUE) {
            return "integer()";
        }
        if (this.maximum == Integer.MAX_VALUE) {
            return "integer(" + this.minimum + ")";
        }
        return "integer(" + this.minimum + ", " + this.maximum + ")";
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

