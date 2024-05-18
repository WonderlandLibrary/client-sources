package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S28PacketEffect implements Packet
{
    private int HorizonCode_Horizon_È;
    private BlockPos Â;
    private int Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001307";
    
    public S28PacketEffect() {
    }
    
    public S28PacketEffect(final int p_i45978_1_, final BlockPos p_i45978_2_, final int p_i45978_3_, final boolean p_i45978_4_) {
        this.HorizonCode_Horizon_È = p_i45978_1_;
        this.Â = p_i45978_2_;
        this.Ý = p_i45978_3_;
        this.Ø­áŒŠá = p_i45978_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readInt();
        this.Â = data.Â();
        this.Ý = data.readInt();
        this.Ø­áŒŠá = data.readBoolean();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.writeInt(this.Ý);
        data.writeBoolean(this.Ø­áŒŠá);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180739_1_) {
        p_180739_1_.HorizonCode_Horizon_È(this);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public BlockPos Ø­áŒŠá() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
