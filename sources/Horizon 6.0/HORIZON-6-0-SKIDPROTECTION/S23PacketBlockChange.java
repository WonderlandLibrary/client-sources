package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S23PacketBlockChange implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private IBlockState Â;
    private static final String Ý = "CL_00001287";
    
    public S23PacketBlockChange() {
    }
    
    public S23PacketBlockChange(final World worldIn, final BlockPos p_i45988_2_) {
        this.HorizonCode_Horizon_È = p_i45988_2_;
        this.Â = worldIn.Â(p_i45988_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Â();
        this.Â = (IBlockState)Block.Â.HorizonCode_Horizon_È(data.Ø­áŒŠá());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.Â(Block.Â.HorizonCode_Horizon_È(this.Â));
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180727_1_) {
        p_180727_1_.HorizonCode_Horizon_È(this);
    }
    
    public IBlockState HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public BlockPos Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
}
