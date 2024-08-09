/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JweHeader;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.lang.Assert;

public abstract class LocatorAdapter<T>
implements Locator<T> {
    @Override
    public final T locate(Header header) {
        Assert.notNull(header, "Header cannot be null.");
        if (header instanceof ProtectedHeader) {
            ProtectedHeader protectedHeader = (ProtectedHeader)header;
            return this.locate(protectedHeader);
        }
        return this.doLocate(header);
    }

    protected T locate(ProtectedHeader protectedHeader) {
        if (protectedHeader instanceof JwsHeader) {
            return this.locate((JwsHeader)protectedHeader);
        }
        Assert.isInstanceOf(JweHeader.class, protectedHeader, "Unrecognized ProtectedHeader type.");
        return this.locate((JweHeader)protectedHeader);
    }

    protected T locate(JweHeader jweHeader) {
        return null;
    }

    protected T locate(JwsHeader jwsHeader) {
        return null;
    }

    protected T doLocate(Header header) {
        return null;
    }
}

