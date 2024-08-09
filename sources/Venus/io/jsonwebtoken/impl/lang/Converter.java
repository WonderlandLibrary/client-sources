/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

public interface Converter<A, B> {
    public B applyTo(A var1);

    public A applyFrom(B var1);
}

