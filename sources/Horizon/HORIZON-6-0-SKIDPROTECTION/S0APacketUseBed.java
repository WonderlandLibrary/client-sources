package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S0APacketUseBed implements Packet
{
    private int HorizonCode_Horizon_È;
    private BlockPos Â;
    private static final String Ý = "CL_00001319";
    
    public S0APacketUseBed() {
    }
    
    public S0APacketUseBed(final EntityPlayer p_i45964_1_, final BlockPos p_i45964_2_) {
        this.HorizonCode_Horizon_È = p_i45964_1_.ˆá();
        this.Â = p_i45964_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = data.Â();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180744_1_) {
        p_180744_1_.HorizonCode_Horizon_È(this);
    }
    
    public EntityPlayer HorizonCode_Horizon_È(final World worldIn) {
        return (EntityPlayer)worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
