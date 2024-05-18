package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C0FPacketConfirmTransaction implements Packet
{
    private int HorizonCode_Horizon_È;
    private short Â;
    private boolean Ý;
    private static final String Ø­áŒŠá = "CL_00001351";
    
    public C0FPacketConfirmTransaction() {
    }
    
    public C0FPacketConfirmTransaction(final int p_i45244_1_, final short p_i45244_2_, final boolean p_i45244_3_) {
        this.HorizonCode_Horizon_È = p_i45244_1_;
        this.Â = p_i45244_2_;
        this.Ý = p_i45244_3_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
        this.Â = data.readShort();
        this.Ý = (data.readByte() != 0);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.writeByte(this.Ý ? 1 : 0);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public short Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
