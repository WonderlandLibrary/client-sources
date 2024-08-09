/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Supplier;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FormattedStringSupplier
implements Supplier<String> {
    private final String msg;
    private final Object[] args;

    public FormattedStringSupplier(String string, Object[] objectArray) {
        this.msg = Assert.hasText(string, "Message cannot be null or empty.");
        this.args = Assert.notEmpty(objectArray, "Arguments cannot be null or empty.");
    }

    @Override
    public String get() {
        return String.format(this.msg, this.args);
    }

    @Override
    public Object get() {
        return this.get();
    }
}

