package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class S3FPacketCustomPayload implements Packet
{
    private String HorizonCode_Horizon_È;
    private PacketBuffer Â;
    private static final String Ý = "CL_00001297";
    
    public S3FPacketCustomPayload() {
    }
    
    public S3FPacketCustomPayload(final String channelName, final PacketBuffer dataIn) {
        this.HorizonCode_Horizon_È = channelName;
        this.Â = dataIn;
        if (dataIn.writerIndex() > 1048576) {
            throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(20);
        final int var2 = data.readableBytes();
        if (var2 >= 0 && var2 <= 1048576) {
            this.Â = new PacketBuffer(data.readBytes(var2));
            return;
        }
        throw new IOException("Payload may not be larger than 1048576 bytes");
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeBytes(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180734_1_) {
        p_180734_1_.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public PacketBuffer Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
