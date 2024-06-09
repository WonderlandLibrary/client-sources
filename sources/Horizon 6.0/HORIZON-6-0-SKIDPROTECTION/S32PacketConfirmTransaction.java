package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S32PacketConfirmTransaction implements Packet
{
    private int HorizonCode_Horizon_È;
    private short Â;
    private boolean Ý;
    private static final String Ø­áŒŠá = "CL_00001291";
    
    public S32PacketConfirmTransaction() {
    }
    
    public S32PacketConfirmTransaction(final int p_i45182_1_, final short p_i45182_2_, final boolean p_i45182_3_) {
        this.HorizonCode_Horizon_È = p_i45182_1_;
        this.Â = p_i45182_2_;
        this.Ý = p_i45182_3_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180730_1_) {
        p_180730_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readUnsignedByte();
        this.Â = data.readShort();
        this.Ý = data.readBoolean();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.writeShort(this.Â);
        data.writeBoolean(this.Ý);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public short Â() {
        return this.Â;
    }
    
    public boolean Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
