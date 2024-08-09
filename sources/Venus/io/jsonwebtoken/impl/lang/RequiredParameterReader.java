/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.lang.Nameable;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.MalformedKeyException;

public class RequiredParameterReader
implements ParameterReadable {
    private final ParameterReadable src;

    public RequiredParameterReader(Header header) {
        this(Assert.isInstanceOf(ParameterReadable.class, header, "Header implementations must implement ParameterReadable: "));
    }

    public RequiredParameterReader(ParameterReadable parameterReadable) {
        this.src = Assert.notNull(parameterReadable, "Source ParameterReadable cannot be null.");
        Assert.isInstanceOf(Nameable.class, parameterReadable, "ParameterReadable implementations must implement Nameable.");
    }

    private String name() {
        return ((Nameable)((Object)this.src)).getName();
    }

    private JwtException malformed(String string) {
        if (this.src instanceof JwkContext || this.src instanceof Jwk) {
            return new MalformedKeyException(string);
        }
        return new MalformedJwtException(string);
    }

    @Override
    public <T> T get(Parameter<T> parameter) {
        T t = this.src.get(parameter);
        if (t == null) {
            String string = this.name() + " is missing required " + parameter + " value.";
            throw this.malformed(string);
        }
        return t;
    }
}

