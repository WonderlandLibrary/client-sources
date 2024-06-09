package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S1FPacketSetExperience implements Packet
{
    private float HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001331";
    
    public S1FPacketSetExperience() {
    }
    
    public S1FPacketSetExperience(final float p_i45222_1_, final int p_i45222_2_, final int p_i45222_3_) {
        this.HorizonCode_Horizon_È = p_i45222_1_;
        this.Â = p_i45222_2_;
        this.Ý = p_i45222_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readFloat();
        this.Ý = data.Ø­áŒŠá();
        this.Â = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeFloat(this.HorizonCode_Horizon_È);
        data.Â(this.Ý);
        data.Â(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180749_1_) {
        p_180749_1_.HorizonCode_Horizon_È(this);
    }
    
    public float HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
