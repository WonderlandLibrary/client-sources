package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Logger;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageSerializer extends MessageToByteEncoder
{
    private static final Logger HorizonCode_Horizon_È;
    private static final Marker Â;
    private final EnumPacketDirection Ý;
    private static final String Ø­áŒŠá = "CL_00001253";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = MarkerManager.getMarker("PACKET_SENT", NetworkManager.Â);
    }
    
    public MessageSerializer(final EnumPacketDirection direction) {
        this.Ý = direction;
    }
    
    protected void HorizonCode_Horizon_È(final ChannelHandlerContext p_encode_1_, Packet p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        final Integer var4 = ((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.Ý).get()).HorizonCode_Horizon_È(this.Ý, p_encode_2_);
        if (MessageSerializer.HorizonCode_Horizon_È.isDebugEnabled()) {
            MessageSerializer.HorizonCode_Horizon_È.debug(MessageSerializer.Â, "OUT: [{}:{}] {}", new Object[] { p_encode_1_.channel().attr(NetworkManager.Ý).get(), var4, p_encode_2_.getClass().getName() });
        }
        if (var4 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketBuffer var5 = new PacketBuffer(p_encode_3_);
        var5.Â(var4);
        try {
            if (p_encode_2_ instanceof S0CPacketSpawnPlayer) {
                p_encode_2_ = p_encode_2_;
            }
            p_encode_2_.Â(var5);
        }
        catch (Throwable var6) {
            MessageSerializer.HorizonCode_Horizon_È.error((Object)var6);
        }
    }
    
    protected void encode(final ChannelHandlerContext p_encode_1_, final Object p_encode_2_, final ByteBuf p_encode_3_) throws IOException {
        this.HorizonCode_Horizon_È(p_encode_1_, (Packet)p_encode_2_, p_encode_3_);
    }
}
