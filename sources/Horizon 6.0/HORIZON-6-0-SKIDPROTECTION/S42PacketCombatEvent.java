package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S42PacketCombatEvent implements Packet
{
    public HorizonCode_Horizon_È HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    public int Ø­áŒŠá;
    public String Âµá€;
    private static final String Ó = "CL_00002299";
    
    public S42PacketCombatEvent() {
    }
    
    public S42PacketCombatEvent(final CombatTracker p_i45970_1_, final HorizonCode_Horizon_È p_i45970_2_) {
        this.HorizonCode_Horizon_È = p_i45970_2_;
        final EntityLivingBase var3 = p_i45970_1_.Ý();
        switch (S42PacketCombatEvent.Â.HorizonCode_Horizon_È[p_i45970_2_.ordinal()]) {
            case 1: {
                this.Ø­áŒŠá = p_i45970_1_.Ø­áŒŠá();
                this.Ý = ((var3 == null) ? -1 : var3.ˆá());
                break;
            }
            case 2: {
                this.Â = p_i45970_1_.Ó().ˆá();
                this.Ý = ((var3 == null) ? -1 : var3.ˆá());
                this.Âµá€ = p_i45970_1_.Â().Ø();
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        if (this.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Â) {
            this.Ø­áŒŠá = data.Ø­áŒŠá();
            this.Ý = data.readInt();
        }
        else if (this.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Ý) {
            this.Â = data.Ø­áŒŠá();
            this.Ý = data.readInt();
            this.Âµá€ = data.Ý(32767);
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        if (this.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Â) {
            data.Â(this.Ø­áŒŠá);
            data.writeInt(this.Ý);
        }
        else if (this.HorizonCode_Horizon_È == S42PacketCombatEvent.HorizonCode_Horizon_È.Ý) {
            data.Â(this.Â);
            data.writeInt(this.Ý);
            data.HorizonCode_Horizon_È(this.Âµá€);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179771_1_) {
        p_179771_1_.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("ENTER_COMBAT", 0, "ENTER_COMBAT", 0), 
        Â("END_COMBAT", 1, "END_COMBAT", 1), 
        Ý("ENTITY_DIED", 2, "ENTITY_DIED", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002297";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45969_1_, final int p_i45969_2_) {
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002298";
        
        static {
            HorizonCode_Horizon_È = new int[S42PacketCombatEvent.HorizonCode_Horizon_È.values().length];
            try {
                S42PacketCombatEvent.Â.HorizonCode_Horizon_È[S42PacketCombatEvent.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                S42PacketCombatEvent.Â.HorizonCode_Horizon_È[S42PacketCombatEvent.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
