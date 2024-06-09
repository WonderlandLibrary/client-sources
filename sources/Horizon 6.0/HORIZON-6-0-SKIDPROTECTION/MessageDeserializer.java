package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.List;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.Logger;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDeserializer extends ByteToMessageDecoder
{
    private static final Logger HorizonCode_Horizon_È;
    private static final Marker Â;
    private final EnumPacketDirection Ý;
    private static final String Ø­áŒŠá = "CL_00001252";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.Â);
    }
    
    public MessageDeserializer(final EnumPacketDirection direction) {
        this.Ý = direction;
    }
    
    protected void decode(final ChannelHandlerContext p_decode_1_, final ByteBuf p_decode_2_, final List p_decode_3_) throws IOException, InstantiationException, IllegalAccessException {
        if (p_decode_2_.readableBytes() != 0) {
            final PacketBuffer var4 = new PacketBuffer(p_decode_2_);
            final int var5 = var4.Ø­áŒŠá();
            final Packet var6 = ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.Ý).get()).HorizonCode_Horizon_È(this.Ý, var5);
            if (var6 == null) {
                throw new IOException("Bad packet id " + var5);
            }
            var6.HorizonCode_Horizon_È(var4);
            if (var4.readableBytes() > 0) {
                throw new IOException("Packet " + ((EnumConnectionState)p_decode_1_.channel().attr(NetworkManager.Ý).get()).HorizonCode_Horizon_È() + "/" + var5 + " (" + var6.getClass().getSimpleName() + ") was larger than I expected, found " + var4.readableBytes() + " bytes extra whilst reading packet " + var5);
            }
            p_decode_3_.add(var6);
            if (MessageDeserializer.HorizonCode_Horizon_È.isDebugEnabled()) {
                MessageDeserializer.HorizonCode_Horizon_È.debug(MessageDeserializer.Â, " IN: [{}:{}] {}", new Object[] { p_decode_1_.channel().attr(NetworkManager.Ý).get(), var5, var6.getClass().getName() });
            }
        }
    }
}
