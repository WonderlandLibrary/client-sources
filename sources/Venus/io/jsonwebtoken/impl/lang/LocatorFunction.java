/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;

public class LocatorFunction<T>
implements Function<Header, T> {
    private final Locator<T> locator;

    public LocatorFunction(Locator<T> locator) {
        this.locator = Assert.notNull(locator, "Locator instance cannot be null.");
    }

    @Override
    public T apply(Header header) {
        return this.locator.locate(header);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((Header)object);
    }
}

