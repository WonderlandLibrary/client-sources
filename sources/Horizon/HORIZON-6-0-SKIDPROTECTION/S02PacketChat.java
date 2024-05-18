package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S02PacketChat implements Packet
{
    private IChatComponent HorizonCode_Horizon_È;
    private byte Â;
    private static final String Ý = "CL_00001289";
    
    public S02PacketChat() {
    }
    
    public S02PacketChat(final IChatComponent component) {
        this(component, (byte)1);
    }
    
    public S02PacketChat(final IChatComponent p_i45986_1_, final byte p_i45986_2_) {
        this.HorizonCode_Horizon_È = p_i45986_1_;
        this.Â = p_i45986_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý();
        this.Â = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public IChatComponent HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.Â == 1 || this.Â == 2;
    }
    
    public byte Ý() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
