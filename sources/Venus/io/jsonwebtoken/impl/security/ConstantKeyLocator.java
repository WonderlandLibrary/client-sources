/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.LocatorAdapter;
import io.jsonwebtoken.impl.lang.Function;
import java.security.Key;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ConstantKeyLocator
extends LocatorAdapter<Key>
implements Function<Header, Key> {
    private final Key jwsKey;
    private final Key jweKey;

    public ConstantKeyLocator(Key key, Key key2) {
        this.jwsKey = key;
        this.jweKey = key2;
    }

    @Override
    protected Key locate(JwsHeader jwsHeader) {
        return this.jwsKey;
    }

    @Override
    protected Key locate(JweHeader jweHeader) {
        return this.jweKey;
    }

    @Override
    public Key apply(Header header) {
        return (Key)this.locate(header);
    }

    @Override
    protected Object locate(JwsHeader jwsHeader) {
        return this.locate(jwsHeader);
    }

    @Override
    protected Object locate(JweHeader jweHeader) {
        return this.locate(jweHeader);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((Header)object);
    }
}

