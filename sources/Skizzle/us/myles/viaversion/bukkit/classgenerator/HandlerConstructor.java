/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package us.myles.ViaVersion.bukkit.classgenerator;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import us.myles.ViaVersion.api.data.UserConnection;

public interface HandlerConstructor {
    public MessageToByteEncoder newEncodeHandler(UserConnection var1, MessageToByteEncoder var2);

    public ByteToMessageDecoder newDecodeHandler(UserConnection var1, ByteToMessageDecoder var2);
}

