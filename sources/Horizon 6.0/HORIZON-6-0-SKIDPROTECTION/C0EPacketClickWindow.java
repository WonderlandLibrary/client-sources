package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C0EPacketClickWindow implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private short Ø­áŒŠá;
    private ItemStack Âµá€;
    private int Ó;
    private static final String à = "CL_00001353";
    
    public C0EPacketClickWindow() {
    }
    
    public C0EPacketClickWindow(final int p_i45246_1_, final int p_i45246_2_, final int p_i45246_3_, final int p_i45246_4_, final ItemStack p_i45246_5_, final short p_i45246_6_) {
        this.HorizonCode_Horizon_È = p_i45246_1_;
        this.Â = p_i45246_2_;
        this.Ý = p_i45246_3_;
        this.Âµá€ = ((p_i45246_5_ != null) ? p_i45246_5_.áˆºÑ¢Õ() : null);
        this.Ø­áŒŠá = p_i45246_6_;
        this.Ó = p_i45246_4_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
        this.Â = data.readShort();
        this.Ý = data.readByte();
        this.Ø­áŒŠá = data.readShort();
        this.Ó = data.readByte();
        this.Âµá€ = data.Ø();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.writeByte(this.Ý);
        data.writeShort(this.Ø­áŒŠá);
        data.writeByte(this.Ó);
        data.HorizonCode_Horizon_È(this.Âµá€);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public short Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public ItemStack Âµá€() {
        return this.Âµá€;
    }
    
    public int Ó() {
        return this.Ó;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
