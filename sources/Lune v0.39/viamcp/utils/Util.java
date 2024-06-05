package me.superskidder.lune.viamcp.utils;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import me.superskidder.lune.viafabric.handler.CommonTransformer;

public class Util {
    public static ChannelPipeline decodeEncodePlacement(ChannelPipeline instance, String base, String newHandler, ChannelHandler handler) {
        // Fixes the handler order
        switch (base) {
            case "decoder": {
                if (instance.get(CommonTransformer.HANDLER_DECODER_NAME) != null)
                    base = CommonTransformer.HANDLER_DECODER_NAME;
                break;
            }
            case "encoder": {
                if (instance.get(CommonTransformer.HANDLER_ENCODER_NAME) != null)
                    base = CommonTransformer.HANDLER_ENCODER_NAME;
                break;
            }
        }
        return instance.addBefore(base, newHandler, handler);
    }
}
