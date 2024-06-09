package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S19PacketEntityHeadLook implements Packet
{
    private int HorizonCode_Horizon_È;
    private byte Â;
    private static final String Ý = "CL_00001323";
    
    public S19PacketEntityHeadLook() {
    }
    
    public S19PacketEntityHeadLook(final Entity p_i45214_1_, final byte p_i45214_2_) {
        this.HorizonCode_Horizon_È = p_i45214_1_.ˆá();
        this.Â = p_i45214_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180745_1_) {
        p_180745_1_.HorizonCode_Horizon_È(this);
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        return worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public byte HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
