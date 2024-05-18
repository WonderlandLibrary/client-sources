package HORIZON-6-0-SKIDPROTECTION;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class C17PacketCustomPayload implements Packet
{
    private String HorizonCode_Horizon_È;
    private PacketBuffer Â;
    private static final String Ý = "CL_00001356";
    
    public C17PacketCustomPayload() {
    }
    
    public C17PacketCustomPayload(final String p_i45945_1_, final PacketBuffer p_i45945_2_) {
        this.HorizonCode_Horizon_È = p_i45945_1_;
        this.Â = p_i45945_2_;
        if (p_i45945_2_.writerIndex() > 32767) {
            throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(20);
        final int var2 = data.readableBytes();
        if (var2 >= 0 && var2 <= 32767) {
            this.Â = new PacketBuffer(data.readBytes(var2));
            return;
        }
        throw new IOException("Payload may not be larger than 32767 bytes");
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeBytes(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public PacketBuffer Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
