package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S33PacketUpdateSign implements Packet
{
    private World HorizonCode_Horizon_È;
    private BlockPos Â;
    private IChatComponent[] Ý;
    private static final String Ø­áŒŠá = "CL_00001338";
    
    public S33PacketUpdateSign() {
    }
    
    public S33PacketUpdateSign(final World worldIn, final BlockPos p_i45951_2_, final IChatComponent[] p_i45951_3_) {
        this.HorizonCode_Horizon_È = worldIn;
        this.Â = p_i45951_2_;
        this.Ý = new IChatComponent[] { p_i45951_3_[0], p_i45951_3_[1], p_i45951_3_[2], p_i45951_3_[3] };
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Â = data.Â();
        this.Ý = new IChatComponent[4];
        for (int var2 = 0; var2 < 4; ++var2) {
            this.Ý[var2] = data.Ý();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.Â);
        for (int var2 = 0; var2 < 4; ++var2) {
            data.HorizonCode_Horizon_È(this.Ý[var2]);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public IChatComponent[] Â() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
