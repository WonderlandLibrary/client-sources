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
public class DoubleArgumentType
implements ArgumentType<Double> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0", "1.2", ".5", "-1", "-.5", "-1234.56");
    private final double minimum;
    private final double maximum;

    private DoubleArgumentType(double d, double d2) {
        this.minimum = d;
        this.maximum = d2;
    }

    public static DoubleArgumentType doubleArg() {
        return DoubleArgumentType.doubleArg(-1.7976931348623157E308);
    }

    public static DoubleArgumentType doubleArg(double d) {
        return DoubleArgumentType.doubleArg(d, Double.MAX_VALUE);
    }

    public static DoubleArgumentType doubleArg(double d, double d2) {
        return new DoubleArgumentType(d, d2);
    }

    public static double getDouble(CommandContext<?> commandContext, String string) {
        return commandContext.getArgument(string, Double.class);
    }

    public double getMinimum() {
        return this.minimum;
    }

    public double getMaximum() {
        return this.maximum;
    }

    @Override
    public Double parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        double d = stringReader.readDouble();
        if (d < this.minimum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.doubleTooLow().createWithContext(stringReader, d, this.minimum);
        }
        if (d > this.maximum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.doubleTooHigh().createWithContext(stringReader, d, this.maximum);
        }
        return d;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DoubleArgumentType)) {
            return true;
        }
        DoubleArgumentType doubleArgumentType = (DoubleArgumentType)object;
        return this.maximum == doubleArgumentType.maximum && this.minimum == doubleArgumentType.minimum;
    }

    public int hashCode() {
        return (int)(31.0 * this.minimum + this.maximum);
    }

    public String toString() {
        if (this.minimum == -1.7976931348623157E308 && this.maximum == Double.MAX_VALUE) {
            return "double()";
        }
        if (this.maximum == Double.MAX_VALUE) {
            return "double(" + this.minimum + ")";
        }
        return "double(" + this.minimum + ", " + this.maximum + ")";
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

