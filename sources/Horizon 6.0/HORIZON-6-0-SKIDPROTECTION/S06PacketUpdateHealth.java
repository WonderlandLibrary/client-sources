package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S06PacketUpdateHealth implements Packet
{
    private float HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    private static final String Ø­áŒŠá = "CL_00001332";
    
    public S06PacketUpdateHealth() {
    }
    
    public S06PacketUpdateHealth(final float healthIn, final int foodLevelIn, final float saturationIn) {
        this.HorizonCode_Horizon_È = healthIn;
        this.Â = foodLevelIn;
        this.Ý = saturationIn;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readFloat();
        this.Â = data.Ø­áŒŠá();
        this.Ý = data.readFloat();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeFloat(this.HorizonCode_Horizon_È);
        data.Â(this.Â);
        data.writeFloat(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180750_1_) {
        p_180750_1_.HorizonCode_Horizon_È(this);
    }
    
    public float HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Â() {
        return this.Â;
    }
    
    public float Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
