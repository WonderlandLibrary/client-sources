package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S0BPacketAnimation implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00001282";
    
    public S0BPacketAnimation() {
    }
    
    public S0BPacketAnimation(final Entity ent, final int animationType) {
        this.HorizonCode_Horizon_È = ent.ˆá();
        this.Â = animationType;
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
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180723_1_) {
        p_180723_1_.HorizonCode_Horizon_È(this);
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
