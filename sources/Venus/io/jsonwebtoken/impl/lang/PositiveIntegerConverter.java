/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PositiveIntegerConverter
implements Converter<Integer, Object> {
    public static final PositiveIntegerConverter INSTANCE = new PositiveIntegerConverter();

    @Override
    public Object applyTo(Integer n) {
        return n;
    }

    @Override
    public Integer applyFrom(Object object) {
        String string;
        int n;
        Assert.notNull(object, "Argument cannot be null.");
        if (object instanceof Byte || object instanceof Short || object instanceof Integer || object instanceof AtomicInteger) {
            n = ((Number)object).intValue();
        } else {
            string = String.valueOf(object);
            try {
                n = Integer.parseInt(string);
            } catch (NumberFormatException numberFormatException) {
                String string2 = "Value cannot be represented as a java.lang.Integer.";
                throw new IllegalArgumentException(string2, numberFormatException);
            }
        }
        if (n <= 0) {
            string = "Value must be a positive integer.";
            throw new IllegalArgumentException(string);
        }
        return n;
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((Integer)object);
    }
}

