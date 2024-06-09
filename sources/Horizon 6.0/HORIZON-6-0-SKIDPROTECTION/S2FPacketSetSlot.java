package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S2FPacketSetSlot implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private ItemStack Ý;
    private static final String Ø­áŒŠá = "CL_00001296";
    
    public S2FPacketSetSlot() {
    }
    
    public S2FPacketSetSlot(final int p_i45188_1_, final int p_i45188_2_, final ItemStack p_i45188_3_) {
        this.HorizonCode_Horizon_È = p_i45188_1_;
        this.Â = p_i45188_2_;
        this.Ý = ((p_i45188_3_ == null) ? null : p_i45188_3_.áˆºÑ¢Õ());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
        this.Â = data.readShort();
        this.Ý = data.Ø();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.HorizonCode_Horizon_È(this.Ý);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public ItemStack Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
