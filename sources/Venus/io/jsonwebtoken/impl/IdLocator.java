/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;

public class IdLocator<H extends Header, R extends Identifiable>
implements Locator<R>,
Function<H, R> {
    private final Parameter<String> param;
    private final String requiredMsg;
    private final boolean valueRequired;
    private final Registry<String, R> registry;

    public IdLocator(Parameter<String> parameter, Registry<String, R> registry, String string) {
        this.param = Assert.notNull(parameter, "Header param cannot be null.");
        this.requiredMsg = Strings.clean(string);
        this.valueRequired = Strings.hasText(this.requiredMsg);
        Assert.notEmpty(registry, "Registry cannot be null or empty.");
        this.registry = registry;
    }

    private static String type(Header header) {
        if (header instanceof JweHeader) {
            return "JWE";
        }
        if (header instanceof JwsHeader) {
            return "JWS";
        }
        return "JWT";
    }

    @Override
    public R locate(Header header) {
        String string;
        Assert.notNull(header, "Header argument cannot be null.");
        Object v = header.get(this.param.getId());
        String string2 = string = v != null ? v.toString() : null;
        if (!Strings.hasText(string)) {
            if (this.valueRequired) {
                throw new MalformedJwtException(this.requiredMsg);
            }
            return null;
        }
        try {
            return (R)((Identifiable)this.registry.forKey(string));
        } catch (Exception exception) {
            String string3 = "Unrecognized " + IdLocator.type(header) + " " + this.param + " header value: " + string;
            throw new UnsupportedJwtException(string3, exception);
        }
    }

    @Override
    public R apply(H h) {
        return (R)this.locate((Header)h);
    }

    @Override
    public Object locate(Header header) {
        return this.locate(header);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((H)((Header)object));
    }
}

