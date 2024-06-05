/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelPipeline
 */
package viamcp.utils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

public class NettyUtil {
    public static ChannelPipeline decodeEncodePlacement(ChannelPipeline instance, String base, String newHandler, ChannelHandler handler) {
        switch (base) {
            case "decoder": {
                if (instance.get("via-decoder") == null) return instance.addBefore(base, newHandler, handler);
                base = "via-decoder";
                return instance.addBefore(base, newHandler, handler);
            }
            case "encoder": {
                if (instance.get("via-encoder") == null) return instance.addBefore(base, newHandler, handler);
                base = "via-encoder";
                return instance.addBefore(base, newHandler, handler);
            }
        }
        return instance.addBefore(base, newHandler, handler);
    }
}

