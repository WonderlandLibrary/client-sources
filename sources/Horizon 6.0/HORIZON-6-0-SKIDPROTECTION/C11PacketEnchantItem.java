package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C11PacketEnchantItem implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00001352";
    
    public C11PacketEnchantItem() {
    }
    
    public C11PacketEnchantItem(final int p_i45245_1_, final int p_i45245_2_) {
        this.HorizonCode_Horizon_È = p_i45245_1_;
        this.Â = p_i45245_2_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
        this.Â = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
