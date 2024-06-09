package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C0CPacketInput implements Packet
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private boolean Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001367";
    
    public C0CPacketInput() {
    }
    
    public C0CPacketInput(final float p_i45261_1_, final float p_i45261_2_, final boolean p_i45261_3_, final boolean p_i45261_4_) {
        this.HorizonCode_Horizon_È = p_i45261_1_;
        this.Â = p_i45261_2_;
        this.Ý = p_i45261_3_;
        this.Ø­áŒŠá = p_i45261_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readFloat();
        this.Â = data.readFloat();
        final byte var2 = data.readByte();
        this.Ý = ((var2 & 0x1) > 0);
        this.Ø­áŒŠá = ((var2 & 0x2) > 0);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeFloat(this.HorizonCode_Horizon_È);
        data.writeFloat(this.Â);
        byte var2 = 0;
        if (this.Ý) {
            var2 |= 0x1;
        }
        if (this.Ø­áŒŠá) {
            var2 |= 0x2;
        }
        data.writeByte(var2);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180766_1_) {
        p_180766_1_.HorizonCode_Horizon_È(this);
    }
    
    public float HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float Â() {
        return this.Â;
    }
    
    public boolean Ý() {
        return this.Ý;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
}
