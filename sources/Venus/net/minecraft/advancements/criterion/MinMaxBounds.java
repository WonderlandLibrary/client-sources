/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class MinMaxBounds<T extends Number> {
    public static final SimpleCommandExceptionType ERROR_EMPTY = new SimpleCommandExceptionType(new TranslationTextComponent("argument.range.empty"));
    public static final SimpleCommandExceptionType ERROR_SWAPPED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.range.swapped"));
    protected final T min;
    protected final T max;

    protected MinMaxBounds(@Nullable T t, @Nullable T t2) {
        this.min = t;
        this.max = t2;
    }

    @Nullable
    public T getMin() {
        return this.min;
    }

    @Nullable
    public T getMax() {
        return this.max;
    }

    public boolean isUnbounded() {
        return this.min == null && this.max == null;
    }

    public JsonElement serialize() {
        if (this.isUnbounded()) {
            return JsonNull.INSTANCE;
        }
        if (this.min != null && this.min.equals(this.max)) {
            return new JsonPrimitive((Number)this.min);
        }
        JsonObject jsonObject = new JsonObject();
        if (this.min != null) {
            jsonObject.addProperty("min", (Number)this.min);
        }
        if (this.max != null) {
            jsonObject.addProperty("max", (Number)this.max);
        }
        return jsonObject;
    }

    protected static <T extends Number, R extends MinMaxBounds<T>> R fromJson(@Nullable JsonElement jsonElement, R r, BiFunction<JsonElement, String, T> biFunction, IBoundFactory<T, R> iBoundFactory) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            if (JSONUtils.isNumber(jsonElement)) {
                Number number = (Number)biFunction.apply(jsonElement, "value");
                return iBoundFactory.create(number, number);
            }
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "value");
            Number number = jsonObject.has("min") ? (Number)((Number)biFunction.apply(jsonObject.get("min"), "min")) : (Number)null;
            Number number2 = jsonObject.has("max") ? (Number)((Number)biFunction.apply(jsonObject.get("max"), "max")) : (Number)null;
            return iBoundFactory.create(number, number2);
        }
        return r;
    }

    protected static <T extends Number, R extends MinMaxBounds<T>> R fromReader(StringReader stringReader, IBoundReader<T, R> iBoundReader, Function<String, T> function, Supplier<DynamicCommandExceptionType> supplier, Function<T, T> function2) throws CommandSyntaxException {
        if (!stringReader.canRead()) {
            throw ERROR_EMPTY.createWithContext(stringReader);
        }
        int n = stringReader.getCursor();
        try {
            Number number;
            Number number2 = (Number)MinMaxBounds.optionallyFormat(MinMaxBounds.readNumber(stringReader, function, supplier), function2);
            if (stringReader.canRead(1) && stringReader.peek() == '.' && stringReader.peek(1) == '.') {
                stringReader.skip();
                stringReader.skip();
                number = (Number)MinMaxBounds.optionallyFormat(MinMaxBounds.readNumber(stringReader, function, supplier), function2);
                if (number2 == null && number == null) {
                    throw ERROR_EMPTY.createWithContext(stringReader);
                }
            } else {
                number = number2;
            }
            if (number2 == null && number == null) {
                throw ERROR_EMPTY.createWithContext(stringReader);
            }
            return iBoundReader.create(stringReader, number2, number);
        } catch (CommandSyntaxException commandSyntaxException) {
            stringReader.setCursor(n);
            throw new CommandSyntaxException(commandSyntaxException.getType(), commandSyntaxException.getRawMessage(), commandSyntaxException.getInput(), n);
        }
    }

    @Nullable
    private static <T extends Number> T readNumber(StringReader stringReader, Function<String, T> function, Supplier<DynamicCommandExceptionType> supplier) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && MinMaxBounds.isAllowedInputChat(stringReader)) {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        if (string.isEmpty()) {
            return (T)((Number)null);
        }
        try {
            return (T)((Number)function.apply(string));
        } catch (NumberFormatException numberFormatException) {
            throw supplier.get().createWithContext(stringReader, string);
        }
    }

    private static boolean isAllowedInputChat(StringReader stringReader) {
        char c = stringReader.peek();
        if ((c < '0' || c > '9') && c != '-') {
            if (c != '.') {
                return true;
            }
            return !stringReader.canRead(1) || stringReader.peek(1) != '.';
        }
        return false;
    }

    @Nullable
    private static <T> T optionallyFormat(@Nullable T t, Function<T, T> function) {
        return t == null ? null : (T)function.apply(t);
    }

    @FunctionalInterface
    public static interface IBoundFactory<T extends Number, R extends MinMaxBounds<T>> {
        public R create(@Nullable T var1, @Nullable T var2);
    }

    @FunctionalInterface
    public static interface IBoundReader<T extends Number, R extends MinMaxBounds<T>> {
        public R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
    }

    public static class IntBound
    extends MinMaxBounds<Integer> {
        public static final IntBound UNBOUNDED = new IntBound((Integer)null, (Integer)null);
        private final Long minSquared;
        private final Long maxSquared;

        private static IntBound create(StringReader stringReader, @Nullable Integer n, @Nullable Integer n2) throws CommandSyntaxException {
            if (n != null && n2 != null && n > n2) {
                throw ERROR_SWAPPED.createWithContext(stringReader);
            }
            return new IntBound(n, n2);
        }

        @Nullable
        private static Long square(@Nullable Integer n) {
            return n == null ? null : Long.valueOf(n.longValue() * n.longValue());
        }

        private IntBound(@Nullable Integer n, @Nullable Integer n2) {
            super(n, n2);
            this.minSquared = IntBound.square(n);
            this.maxSquared = IntBound.square(n2);
        }

        public static IntBound exactly(int n) {
            return new IntBound(n, n);
        }

        public static IntBound atLeast(int n) {
            return new IntBound(n, (Integer)null);
        }

        public boolean test(int n) {
            if (this.min != null && (Integer)this.min > n) {
                return true;
            }
            return this.max == null || (Integer)this.max >= n;
        }

        public static IntBound fromJson(@Nullable JsonElement jsonElement) {
            return IntBound.fromJson(jsonElement, UNBOUNDED, JSONUtils::getInt, IntBound::new);
        }

        public static IntBound fromReader(StringReader stringReader) throws CommandSyntaxException {
            return IntBound.fromReader(stringReader, IntBound::lambda$fromReader$0);
        }

        public static IntBound fromReader(StringReader stringReader, Function<Integer, Integer> function) throws CommandSyntaxException {
            return IntBound.fromReader(stringReader, IntBound::create, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, function);
        }

        private static Integer lambda$fromReader$0(Integer n) {
            return n;
        }
    }

    public static class FloatBound
    extends MinMaxBounds<Float> {
        public static final FloatBound UNBOUNDED = new FloatBound((Float)null, (Float)null);
        private final Double minSquared;
        private final Double maxSquared;

        private static FloatBound create(StringReader stringReader, @Nullable Float f, @Nullable Float f2) throws CommandSyntaxException {
            if (f != null && f2 != null && f.floatValue() > f2.floatValue()) {
                throw ERROR_SWAPPED.createWithContext(stringReader);
            }
            return new FloatBound(f, f2);
        }

        @Nullable
        private static Double square(@Nullable Float f) {
            return f == null ? null : Double.valueOf(f.doubleValue() * f.doubleValue());
        }

        private FloatBound(@Nullable Float f, @Nullable Float f2) {
            super(f, f2);
            this.minSquared = FloatBound.square(f);
            this.maxSquared = FloatBound.square(f2);
        }

        public static FloatBound atLeast(float f) {
            return new FloatBound(Float.valueOf(f), (Float)null);
        }

        public boolean test(float f) {
            if (this.min != null && ((Float)this.min).floatValue() > f) {
                return true;
            }
            return this.max == null || !(((Float)this.max).floatValue() < f);
        }

        public boolean testSquared(double d) {
            if (this.minSquared != null && this.minSquared > d) {
                return true;
            }
            return this.maxSquared == null || !(this.maxSquared < d);
        }

        public static FloatBound fromJson(@Nullable JsonElement jsonElement) {
            return FloatBound.fromJson(jsonElement, UNBOUNDED, JSONUtils::getFloat, FloatBound::new);
        }

        public static FloatBound fromReader(StringReader stringReader) throws CommandSyntaxException {
            return FloatBound.fromReader(stringReader, FloatBound::lambda$fromReader$0);
        }

        public static FloatBound fromReader(StringReader stringReader, Function<Float, Float> function) throws CommandSyntaxException {
            return FloatBound.fromReader(stringReader, FloatBound::create, Float::parseFloat, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidFloat, function);
        }

        private static Float lambda$fromReader$0(Float f) {
            return f;
        }
    }
}

