package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S45PacketTitle implements Packet
{
    private HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private IChatComponent Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00002287";
    
    public S45PacketTitle() {
    }
    
    public S45PacketTitle(final HorizonCode_Horizon_È p_i45953_1_, final IChatComponent p_i45953_2_) {
        this(p_i45953_1_, p_i45953_2_, -1, -1, -1);
    }
    
    public S45PacketTitle(final int p_i45954_1_, final int p_i45954_2_, final int p_i45954_3_) {
        this(S45PacketTitle.HorizonCode_Horizon_È.Ý, null, p_i45954_1_, p_i45954_2_, p_i45954_3_);
    }
    
    public S45PacketTitle(final HorizonCode_Horizon_È p_i45955_1_, final IChatComponent p_i45955_2_, final int p_i45955_3_, final int p_i45955_4_, final int p_i45955_5_) {
        this.HorizonCode_Horizon_È = p_i45955_1_;
        this.Â = p_i45955_2_;
        this.Ý = p_i45955_3_;
        this.Ø­áŒŠá = p_i45955_4_;
        this.Âµá€ = p_i45955_5_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        if (this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.HorizonCode_Horizon_È || this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.Â) {
            this.Â = data.Ý();
        }
        if (this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.Ý) {
            this.Ý = data.readInt();
            this.Ø­áŒŠá = data.readInt();
            this.Âµá€ = data.readInt();
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        if (this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.HorizonCode_Horizon_È || this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.Â) {
            data.HorizonCode_Horizon_È(this.Â);
        }
        if (this.HorizonCode_Horizon_È == S45PacketTitle.HorizonCode_Horizon_È.Ý) {
            data.writeInt(this.Ý);
            data.writeInt(this.Ø­áŒŠá);
            data.writeInt(this.Âµá€);
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179802_1_) {
        p_179802_1_.HorizonCode_Horizon_È(this);
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public IChatComponent Â() {
        return this.Â;
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return this.Âµá€;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("TITLE", 0, "TITLE", 0), 
        Â("SUBTITLE", 1, "SUBTITLE", 1), 
        Ý("TIMES", 2, "TIMES", 2), 
        Ø­áŒŠá("CLEAR", 3, "CLEAR", 3), 
        Âµá€("RESET", 4, "RESET", 4);
        
        private static final HorizonCode_Horizon_È[] Ó;
        private static final String à = "CL_00002286";
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45952_1_, final int p_i45952_2_) {
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_179969_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (var4.name().equalsIgnoreCase(p_179969_0_)) {
                    return var4;
                }
            }
            return HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        
        public static String[] HorizonCode_Horizon_È() {
            final String[] var0 = new String[values().length];
            int var2 = 0;
            for (final HorizonCode_Horizon_È var6 : values()) {
                var0[var2++] = var6.name().toLowerCase();
            }
            return var0;
        }
    }
}
