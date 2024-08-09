/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.util.text.TranslationTextComponent;

public class MinMaxBoundsWrapped {
    public static final MinMaxBoundsWrapped UNBOUNDED = new MinMaxBoundsWrapped(null, null);
    public static final SimpleCommandExceptionType ERROR_INTS_ONLY = new SimpleCommandExceptionType(new TranslationTextComponent("argument.range.ints"));
    private final Float min;
    private final Float max;

    public MinMaxBoundsWrapped(@Nullable Float f, @Nullable Float f2) {
        this.min = f;
        this.max = f2;
    }

    @Nullable
    public Float getMin() {
        return this.min;
    }

    @Nullable
    public Float getMax() {
        return this.max;
    }

    public static MinMaxBoundsWrapped fromReader(StringReader stringReader, boolean bl, Function<Float, Float> function) throws CommandSyntaxException {
        Float f;
        if (!stringReader.canRead()) {
            throw MinMaxBounds.ERROR_EMPTY.createWithContext(stringReader);
        }
        int n = stringReader.getCursor();
        Float f2 = MinMaxBoundsWrapped.map(MinMaxBoundsWrapped.fromReader(stringReader, bl), function);
        if (stringReader.canRead(1) && stringReader.peek() == '.' && stringReader.peek(1) == '.') {
            stringReader.skip();
            stringReader.skip();
            f = MinMaxBoundsWrapped.map(MinMaxBoundsWrapped.fromReader(stringReader, bl), function);
            if (f2 == null && f == null) {
                stringReader.setCursor(n);
                throw MinMaxBounds.ERROR_EMPTY.createWithContext(stringReader);
            }
        } else {
            if (!bl && stringReader.canRead() && stringReader.peek() == '.') {
                stringReader.setCursor(n);
                throw ERROR_INTS_ONLY.createWithContext(stringReader);
            }
            f = f2;
        }
        if (f2 == null && f == null) {
            stringReader.setCursor(n);
            throw MinMaxBounds.ERROR_EMPTY.createWithContext(stringReader);
        }
        return new MinMaxBoundsWrapped(f2, f);
    }

    @Nullable
    private static Float fromReader(StringReader stringReader, boolean bl) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && MinMaxBoundsWrapped.isValidNumber(stringReader, bl)) {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        if (string.isEmpty()) {
            return null;
        }
        try {
            return Float.valueOf(Float.parseFloat(string));
        } catch (NumberFormatException numberFormatException) {
            if (bl) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(stringReader, string);
            }
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(stringReader, string);
        }
    }

    private static boolean isValidNumber(StringReader stringReader, boolean bl) {
        char c = stringReader.peek();
        if ((c < '0' || c > '9') && c != '-') {
            if (bl && c == '.') {
                return !stringReader.canRead(1) || stringReader.peek(1) != '.';
            }
            return true;
        }
        return false;
    }

    @Nullable
    private static Float map(@Nullable Float f, Function<Float, Float> function) {
        return f == null ? null : function.apply(f);
    }
}

