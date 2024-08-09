/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.util.ReferenceCounted;

public interface InterfaceHttpData
extends Comparable<InterfaceHttpData>,
ReferenceCounted {
    public String getName();

    public HttpDataType getHttpDataType();

    @Override
    public InterfaceHttpData retain();

    @Override
    public InterfaceHttpData retain(int var1);

    @Override
    public InterfaceHttpData touch();

    @Override
    public InterfaceHttpData touch(Object var1);

    public static enum HttpDataType {
        Attribute,
        FileUpload,
        InternalAttribute;

    }
}

