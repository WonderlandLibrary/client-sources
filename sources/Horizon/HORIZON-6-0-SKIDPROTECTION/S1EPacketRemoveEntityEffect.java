package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S1EPacketRemoveEntityEffect implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00001321";
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    public S1EPacketRemoveEntityEffect(final int p_i45212_1_, final PotionEffect p_i45212_2_) {
        this.HorizonCode_Horizon_È = p_i45212_1_;
        this.Â = p_i45212_2_.HorizonCode_Horizon_È();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.readUnsignedByte();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
