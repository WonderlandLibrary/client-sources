/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FormattedStringFunction<T>
implements Function<T, String> {
    private final String msg;

    public FormattedStringFunction(String string) {
        this.msg = Assert.hasText(string, "msg argument cannot be null or empty.");
    }

    @Override
    public String apply(T t) {
        return String.format(this.msg, t);
    }

    @Override
    public Object apply(Object object) {
        return this.apply(object);
    }
}

