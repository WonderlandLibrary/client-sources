package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S3DPacketDisplayScoreboard implements Packet
{
    private int HorizonCode_Horizon_È;
    private String Â;
    private static final String Ý = "CL_00001325";
    
    public S3DPacketDisplayScoreboard() {
    }
    
    public S3DPacketDisplayScoreboard(final int p_i45216_1_, final ScoreObjective p_i45216_2_) {
        this.HorizonCode_Horizon_È = p_i45216_1_;
        if (p_i45216_2_ == null) {
            this.Â = "";
        }
        else {
            this.Â = p_i45216_2_.Â();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readByte();
        this.Â = data.Ý(16);
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180747_1_) {
        p_180747_1_.HorizonCode_Horizon_È(this);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
