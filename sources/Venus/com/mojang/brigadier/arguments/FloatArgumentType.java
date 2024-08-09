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
public class FloatArgumentType
implements ArgumentType<Float> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0", "1.2", ".5", "-1", "-.5", "-1234.56");
    private final float minimum;
    private final float maximum;

    private FloatArgumentType(float f, float f2) {
        this.minimum = f;
        this.maximum = f2;
    }

    public static FloatArgumentType floatArg() {
        return FloatArgumentType.floatArg(-3.4028235E38f);
    }

    public static FloatArgumentType floatArg(float f) {
        return FloatArgumentType.floatArg(f, Float.MAX_VALUE);
    }

    public static FloatArgumentType floatArg(float f, float f2) {
        return new FloatArgumentType(f, f2);
    }

    public static float getFloat(CommandContext<?> commandContext, String string) {
        return commandContext.getArgument(string, Float.class).floatValue();
    }

    public float getMinimum() {
        return this.minimum;
    }

    public float getMaximum() {
        return this.maximum;
    }

    @Override
    public Float parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        float f = stringReader.readFloat();
        if (f < this.minimum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.floatTooLow().createWithContext(stringReader, Float.valueOf(f), Float.valueOf(this.minimum));
        }
        if (f > this.maximum) {
            stringReader.setCursor(n);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.floatTooHigh().createWithContext(stringReader, Float.valueOf(f), Float.valueOf(this.maximum));
        }
        return Float.valueOf(f);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof FloatArgumentType)) {
            return true;
        }
        FloatArgumentType floatArgumentType = (FloatArgumentType)object;
        return this.maximum == floatArgumentType.maximum && this.minimum == floatArgumentType.minimum;
    }

    public int hashCode() {
        return (int)(31.0f * this.minimum + this.maximum);
    }

    public String toString() {
        if (this.minimum == -3.4028235E38f && this.maximum == Float.MAX_VALUE) {
            return "float()";
        }
        if (this.maximum == Float.MAX_VALUE) {
            return "float(" + this.minimum + ")";
        }
        return "float(" + this.minimum + ", " + this.maximum + ")";
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

