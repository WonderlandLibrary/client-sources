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
public class LongArgumentType
implements ArgumentType<Long> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0", "123", "-123");
    private final long minimum;
    private final long maximum;

    private LongArgumentType(long l, long l2) {
        this.minimum = l;
        this.maximum = l2;
    }

    public static LongArgumentType longArg() {
        return LongArgumentType.longArg(Long.MIN_VALUE);
    }

    public static LongArgumentType longArg(long l) {
        return LongArgumentType.longArg(l, Long.MAX_VALUE);
    }

    public static LongArgumentType longArg(long l, long l2) {
        return new LongArgumentType(l, l2);
    }

    public static long getLong(CommandContext<?> commandContext, String string) {
        return commandContext.getArgument(string, Long.TYPE);
    }

    public long getMinimum() {
        return this.minimum;
    }

    public long getMaximum() {
        return this.maximum;
    }

    @Override
    public Long parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        long l = stringReader.readLong();
        if (l < this.minimum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.longTooLow().createWithContext(stringReader, l, this.minimum);
        }
        if (l > this.maximum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.longTooHigh().createWithContext(stringReader, l, this.maximum);
        }
        return l;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LongArgumentType)) {
            return true;
        }
        LongArgumentType longArgumentType = (LongArgumentType)object;
        return this.maximum == longArgumentType.maximum && this.minimum == longArgumentType.minimum;
    }

    public int hashCode() {
        return 31 * Long.hashCode(this.minimum) + Long.hashCode(this.maximum);
    }

    public String toString() {
        if (this.minimum == Long.MIN_VALUE && this.maximum == Long.MAX_VALUE) {
            return "longArg()";
        }
        if (this.maximum == Long.MAX_VALUE) {
            return "longArg(" + this.minimum + ")";
        }
        return "longArg(" + this.minimum + ", " + this.maximum + ")";
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

