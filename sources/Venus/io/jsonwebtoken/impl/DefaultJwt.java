/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtVisitor;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;

public class DefaultJwt<H extends Header, P>
implements Jwt<H, P> {
    private final H header;
    private final P payload;

    public DefaultJwt(H h, P p) {
        this.header = (Header)Assert.notNull(h, "header cannot be null.");
        this.payload = Assert.notNull(p, "payload cannot be null.");
    }

    @Override
    public H getHeader() {
        return this.header;
    }

    @Override
    public P getBody() {
        return this.getPayload();
    }

    @Override
    public P getPayload() {
        return this.payload;
    }

    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("header=").append(this.header).append(",payload=");
        if (this.payload instanceof byte[]) {
            String string = Encoders.BASE64URL.encode((byte[])this.payload);
            stringBuilder.append(string);
        } else {
            stringBuilder.append(this.payload);
        }
        return stringBuilder;
    }

    public final String toString() {
        return this.toStringBuilder().toString();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof Jwt) {
            Jwt jwt = (Jwt)object;
            return Objects.nullSafeEquals(this.header, jwt.getHeader()) && Objects.nullSafeEquals(this.payload, jwt.getPayload());
        }
        return true;
    }

    public int hashCode() {
        return Objects.nullSafeHashCode(this.header, this.payload);
    }

    @Override
    public <T> T accept(JwtVisitor<T> jwtVisitor) {
        return jwtVisitor.visit(this);
    }
}

