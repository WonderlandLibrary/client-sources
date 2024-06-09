package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class S44PacketWorldBorder implements Packet
{
    private HorizonCode_Horizon_È HorizonCode_Horizon_È;
    private int Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private long à;
    private int Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00002292";
    
    public S44PacketWorldBorder() {
    }
    
    public S44PacketWorldBorder(final WorldBorder p_i45962_1_, final HorizonCode_Horizon_È p_i45962_2_) {
        this.HorizonCode_Horizon_È = p_i45962_2_;
        this.Ý = p_i45962_1_.HorizonCode_Horizon_È();
        this.Ø­áŒŠá = p_i45962_1_.Â();
        this.Ó = p_i45962_1_.Ø();
        this.Âµá€ = p_i45962_1_.áˆºÑ¢Õ();
        this.à = p_i45962_1_.áŒŠÆ();
        this.Â = p_i45962_1_.á();
        this.áŒŠÆ = p_i45962_1_.µà();
        this.Ø = p_i45962_1_.£à();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = (HorizonCode_Horizon_È)data.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class);
        switch (S44PacketWorldBorder.Â.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
            case 1: {
                this.Âµá€ = data.readDouble();
                break;
            }
            case 2: {
                this.Ó = data.readDouble();
                this.Âµá€ = data.readDouble();
                this.à = data.Âµá€();
                break;
            }
            case 3: {
                this.Ý = data.readDouble();
                this.Ø­áŒŠá = data.readDouble();
                break;
            }
            case 4: {
                this.áŒŠÆ = data.Ø­áŒŠá();
                break;
            }
            case 5: {
                this.Ø = data.Ø­áŒŠá();
                break;
            }
            case 6: {
                this.Ý = data.readDouble();
                this.Ø­áŒŠá = data.readDouble();
                this.Ó = data.readDouble();
                this.Âµá€ = data.readDouble();
                this.à = data.Âµá€();
                this.Â = data.Ø­áŒŠá();
                this.áŒŠÆ = data.Ø­áŒŠá();
                this.Ø = data.Ø­áŒŠá();
                break;
            }
        }
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
        switch (S44PacketWorldBorder.Â.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
            case 1: {
                data.writeDouble(this.Âµá€);
                break;
            }
            case 2: {
                data.writeDouble(this.Ó);
                data.writeDouble(this.Âµá€);
                data.HorizonCode_Horizon_È(this.à);
                break;
            }
            case 3: {
                data.writeDouble(this.Ý);
                data.writeDouble(this.Ø­áŒŠá);
                break;
            }
            case 4: {
                data.Â(this.áŒŠÆ);
                break;
            }
            case 5: {
                data.Â(this.Ø);
                break;
            }
            case 6: {
                data.writeDouble(this.Ý);
                data.writeDouble(this.Ø­áŒŠá);
                data.writeDouble(this.Ó);
                data.writeDouble(this.Âµá€);
                data.HorizonCode_Horizon_È(this.à);
                data.Â(this.Â);
                data.Â(this.áŒŠÆ);
                data.Â(this.Ø);
                break;
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_179787_1_) {
        p_179787_1_.HorizonCode_Horizon_È(this);
    }
    
    public void HorizonCode_Horizon_È(final WorldBorder p_179788_1_) {
        switch (S44PacketWorldBorder.Â.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
            case 1: {
                p_179788_1_.HorizonCode_Horizon_È(this.Âµá€);
                break;
            }
            case 2: {
                p_179788_1_.HorizonCode_Horizon_È(this.Ó, this.Âµá€, this.à);
                break;
            }
            case 3: {
                p_179788_1_.Â(this.Ý, this.Ø­áŒŠá);
                break;
            }
            case 4: {
                p_179788_1_.Ý(this.áŒŠÆ);
                break;
            }
            case 5: {
                p_179788_1_.Â(this.Ø);
                break;
            }
            case 6: {
                p_179788_1_.Â(this.Ý, this.Ø­áŒŠá);
                if (this.à > 0L) {
                    p_179788_1_.HorizonCode_Horizon_È(this.Ó, this.Âµá€, this.à);
                }
                else {
                    p_179788_1_.HorizonCode_Horizon_È(this.Âµá€);
                }
                p_179788_1_.HorizonCode_Horizon_È(this.Â);
                p_179788_1_.Ý(this.áŒŠÆ);
                p_179788_1_.Â(this.Ø);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("SET_SIZE", 0, "SET_SIZE", 0), 
        Â("LERP_SIZE", 1, "LERP_SIZE", 1), 
        Ý("SET_CENTER", 2, "SET_CENTER", 2), 
        Ø­áŒŠá("INITIALIZE", 3, "INITIALIZE", 3), 
        Âµá€("SET_WARNING_TIME", 4, "SET_WARNING_TIME", 4), 
        Ó("SET_WARNING_BLOCKS", 5, "SET_WARNING_BLOCKS", 5);
        
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002290";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45961_1_, final int p_i45961_2_) {
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002291";
        
        static {
            HorizonCode_Horizon_È = new int[S44PacketWorldBorder.HorizonCode_Horizon_È.values().length];
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                S44PacketWorldBorder.Â.HorizonCode_Horizon_È[S44PacketWorldBorder.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
