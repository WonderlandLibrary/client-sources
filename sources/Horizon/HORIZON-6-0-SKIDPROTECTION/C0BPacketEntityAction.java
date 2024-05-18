package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C0BPacketEntityAction implements Packet
{
    private int HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È Â;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001366";
    
    public C0BPacketEntityAction() {
    }
    
    public C0BPacketEntityAction(final Entity p_i45937_1_, final HorizonCode_Horizon_È p_i45937_2_) {
        this(p_i45937_1_, p_i45937_2_, 0);
    }
    
    public C0BPacketEntityAction(final Entity p_i45938_1_, final HorizonCode_Horizon_È p_i45938_2_, final int p_i45938_3_) {
        this.HorizonCode_Horizon_È = p_i45938_1_.ˆá();
        this.Â = p_i45938_2_;
        this.Ý = p_i45938_3_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        this.Ý = data.Ø­áŒŠá();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        data.Â(this.Ý);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer p_180765_1_) {
        p_180765_1_.HorizonCode_Horizon_È(this);
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public int Â() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("START_SNEAKING", 0, "START_SNEAKING", 0), 
        Â("STOP_SNEAKING", 1, "STOP_SNEAKING", 1), 
        Ý("STOP_SLEEPING", 2, "STOP_SLEEPING", 2), 
        Ø­áŒŠá("START_SPRINTING", 3, "START_SPRINTING", 3), 
        Âµá€("STOP_SPRINTING", 4, "STOP_SPRINTING", 4), 
        Ó("RIDING_JUMP", 5, "RIDING_JUMP", 5), 
        à("OPEN_INVENTORY", 6, "OPEN_INVENTORY", 6);
        
        private static final HorizonCode_Horizon_È[] Ø;
        private static final String áŒŠÆ = "CL_00002283";
        
        static {
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45936_1_, final int p_i45936_2_) {
        }
    }
}
