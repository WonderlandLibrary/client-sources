/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jboss.marshalling.Marshaller
 */
package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Marshaller;

public interface MarshallerProvider {
    public Marshaller getMarshaller(ChannelHandlerContext var1) throws Exception;
}

