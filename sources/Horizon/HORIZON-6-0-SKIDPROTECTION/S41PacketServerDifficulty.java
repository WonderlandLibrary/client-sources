package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S41PacketServerDifficulty implements Packet
{
    private EnumDifficulty HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00002303";
    
    public S41PacketServerDifficulty() {
    }
    
    public S41PacketServerDifficulty(final EnumDifficulty p_i45987_1_, final boolean p_i45987_2_) {
        this.HorizonCode_Horizon_È = p_i45987_1_;
        this.Â = p_i45987_2_;
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179829_1_) {
        p_179829_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = EnumDifficulty.HorizonCode_Horizon_È(data.readUnsignedByte());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeByte(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È());
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public EnumDifficulty Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
