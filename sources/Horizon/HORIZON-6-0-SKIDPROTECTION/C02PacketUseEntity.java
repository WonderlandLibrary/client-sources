package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class C02PacketUseEntity implements Packet
{
    private int HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È Â;
    private Vec3 Ý;
    private static final String Ø­áŒŠá = "CL_00001357";
    
    public C02PacketUseEntity() {
    }
    
    public C02PacketUseEntity(final Entity p_i45251_1_, final HorizonCode_Horizon_È p_i45251_2_) {
        this.HorizonCode_Horizon_È = p_i45251_1_.ˆá();
        this.Â = p_i45251_2_;
    }
    
    public C02PacketUseEntity(final Entity p_i45944_1_, final Vec3 p_i45944_2_) {
        this(p_i45944_1_, C02PacketUseEntity.HorizonCode_Horizon_È.Ý);
        this.Ý = p_i45944_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.Ø­áŒŠá();
        this.Â = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        if (this.Â == C02PacketUseEntity.HorizonCode_Horizon_È.Ý) {
            this.Ý = new Vec3(data.readFloat(), data.readFloat(), data.readFloat());
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.Â(this.HorizonCode_Horizon_È);
        data.HorizonCode_Horizon_È(this.Â);
        if (this.Â == C02PacketUseEntity.HorizonCode_Horizon_È.Ý) {
            data.writeFloat((float)this.Ý.HorizonCode_Horizon_È);
            data.writeFloat((float)this.Ý.Â);
            data.writeFloat((float)this.Ý.Ý);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayServer handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public Entity HorizonCode_Horizon_È(final World worldIn) {
        return worldIn.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public Vec3 Â() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayServer)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("INTERACT", 0, "INTERACT", 0), 
        Â("ATTACK", 1, "ATTACK", 1), 
        Ý("INTERACT_AT", 2, "INTERACT_AT", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001358";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45943_1_, final int p_i45943_2_) {
        }
    }
}
