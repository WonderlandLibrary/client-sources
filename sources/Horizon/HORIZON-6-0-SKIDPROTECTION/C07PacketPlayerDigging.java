package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C07PacketPlayerDigging implements Packet
{
    private BlockPos HorizonCode_Horizon_È;
    private EnumFacing Â;
    private HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00001365";
    
    public C07PacketPlayerDigging() {
    }
    
    public C07PacketPlayerDigging(final HorizonCode_Horizon_È p_i45940_1_, final BlockPos p_i45940_2_, final EnumFacing p_i45940_3_) {
        this.Ý = p_i45940_1_;
        this.HorizonCode_Horizon_È = p_i45940_2_;
        this.Â = p_i45940_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.Ý = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        this.HorizonCode_Horizon_È = data.Â();
        this.Â = EnumFacing.HorizonCode_Horizon_È(data.readUnsignedByte());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.Ý);
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        data.writeByte(this.Â.Â());
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180763_1_) {
        p_180763_1_.HorizonCode_Horizon_È(this);
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public EnumFacing Â() {
        return this.Â;
    }
    
    public HorizonCode_Horizon_È Ý() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("START_DESTROY_BLOCK", 0, "START_DESTROY_BLOCK", 0), 
        Â("ABORT_DESTROY_BLOCK", 1, "ABORT_DESTROY_BLOCK", 1), 
        Ý("STOP_DESTROY_BLOCK", 2, "STOP_DESTROY_BLOCK", 2), 
        Ø­áŒŠá("DROP_ALL_ITEMS", 3, "DROP_ALL_ITEMS", 3), 
        Âµá€("DROP_ITEM", 4, "DROP_ITEM", 4), 
        Ó("RELEASE_USE_ITEM", 5, "RELEASE_USE_ITEM", 5);
        
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002284";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45939_1_, final int p_i45939_2_) {
        }
    }
}
