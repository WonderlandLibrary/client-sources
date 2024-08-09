/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.socksx.v5.AbstractSocks5Message;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultSocks5InitialRequest
extends AbstractSocks5Message
implements Socks5InitialRequest {
    private final List<Socks5AuthMethod> authMethods;

    public DefaultSocks5InitialRequest(Socks5AuthMethod ... socks5AuthMethodArray) {
        if (socks5AuthMethodArray == null) {
            throw new NullPointerException("authMethods");
        }
        ArrayList<Socks5AuthMethod> arrayList = new ArrayList<Socks5AuthMethod>(socks5AuthMethodArray.length);
        for (Socks5AuthMethod socks5AuthMethod : socks5AuthMethodArray) {
            if (socks5AuthMethod == null) break;
            arrayList.add(socks5AuthMethod);
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("authMethods is empty");
        }
        this.authMethods = Collections.unmodifiableList(arrayList);
    }

    public DefaultSocks5InitialRequest(Iterable<Socks5AuthMethod> iterable) {
        if (iterable == null) {
            throw new NullPointerException("authSchemes");
        }
        ArrayList<Socks5AuthMethod> arrayList = new ArrayList<Socks5AuthMethod>();
        for (Socks5AuthMethod socks5AuthMethod : iterable) {
            if (socks5AuthMethod == null) break;
            arrayList.add(socks5AuthMethod);
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("authMethods is empty");
        }
        this.authMethods = Collections.unmodifiableList(arrayList);
    }

    @Override
    public List<Socks5AuthMethod> authMethods() {
        return this.authMethods;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = this.decoderResult();
        if (!decoderResult.isSuccess()) {
            stringBuilder.append("(decoderResult: ");
            stringBuilder.append(decoderResult);
            stringBuilder.append(", authMethods: ");
        } else {
            stringBuilder.append("(authMethods: ");
        }
        stringBuilder.append(this.authMethods());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

