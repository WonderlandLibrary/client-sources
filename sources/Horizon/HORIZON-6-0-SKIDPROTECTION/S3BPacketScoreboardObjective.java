package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S3BPacketScoreboardObjective implements Packet
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private IScoreObjectiveCriteria.HorizonCode_Horizon_È Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001333";
    
    public S3BPacketScoreboardObjective() {
    }
    
    public S3BPacketScoreboardObjective(final ScoreObjective p_i45224_1_, final int p_i45224_2_) {
        this.HorizonCode_Horizon_È = p_i45224_1_.Â();
        this.Â = p_i45224_1_.Ø­áŒŠá();
        this.Ý = p_i45224_1_.Ý().Ý();
        this.Ø­áŒŠá = p_i45224_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ý(16);
        this.Ø­áŒŠá = data.readByte();
        if (this.Ø­áŒŠá == 0 || this.Ø­áŒŠá == 2) {
            this.Â = data.Ý(32);
            this.Ý = IScoreObjectiveCriteria.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.Ý(16));
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Ø­áŒŠá);
        if (this.Ø­áŒŠá == 0 || this.Ø­áŒŠá == 2) {
            data.HorizonCode_Horizon_È(this.Â);
            data.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È());
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ø­áŒŠá;
    }
    
    public IScoreObjectiveCriteria.HorizonCode_Horizon_È Ø­áŒŠá() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
