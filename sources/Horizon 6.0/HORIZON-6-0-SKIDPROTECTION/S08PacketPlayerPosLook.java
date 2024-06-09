package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.EnumSet;
import java.io.IOException;
import java.util.Set;

public class S08PacketPlayerPosLook implements Packet
{
    public double HorizonCode_Horizon_È;
    public double Â;
    public double Ý;
    public float Ø­áŒŠá;
    public float Âµá€;
    public Set Ó;
    private static final String à = "CL_00001273";
    
    public S08PacketPlayerPosLook() {
    }
    
    public S08PacketPlayerPosLook(final double p_i45993_1_, final double p_i45993_3_, final double p_i45993_5_, final float p_i45993_7_, final float p_i45993_8_, final Set p_i45993_9_) {
        this.HorizonCode_Horizon_È = p_i45993_1_;
        this.Â = p_i45993_3_;
        this.Ý = p_i45993_5_;
        this.Ø­áŒŠá = p_i45993_7_;
        this.Âµá€ = p_i45993_8_;
        this.Ó = p_i45993_9_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readDouble();
        this.Â = data.readDouble();
        this.Ý = data.readDouble();
        this.Ø­áŒŠá = data.readFloat();
        this.Âµá€ = data.readFloat();
        this.Ó = S08PacketPlayerPosLook.HorizonCode_Horizon_È.HorizonCode_Horizon_È(data.readUnsignedByte());
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeDouble(this.HorizonCode_Horizon_È);
        data.writeDouble(this.Â);
        data.writeDouble(this.Ý);
        data.writeFloat(this.Ø­áŒŠá);
        data.writeFloat(this.Âµá€);
        data.writeByte(S08PacketPlayerPosLook.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ó));
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient p_180718_1_) {
        p_180718_1_.HorizonCode_Horizon_È(this);
    }
    
    public double HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public double Â() {
        return this.Â;
    }
    
    public double Ý() {
        return this.Ý;
    }
    
    public float Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public float Âµá€() {
        return this.Âµá€;
    }
    
    public Set Ó() {
        return this.Ó;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("X", 0, "X", 0, 0), 
        Â("Y", 1, "Y", 1, 1), 
        Ý("Z", 2, "Z", 2, 2), 
        Ø­áŒŠá("Y_ROT", 3, "Y_ROT", 3, 3), 
        Âµá€("X_ROT", 4, "X_ROT", 4, 4);
        
        private int Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002304";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45992_1_, final int p_i45992_2_, final int p_i45992_3_) {
            this.Ó = p_i45992_3_;
        }
        
        private int HorizonCode_Horizon_È() {
            return 1 << this.Ó;
        }
        
        private boolean Â(final int p_180054_1_) {
            return (p_180054_1_ & this.HorizonCode_Horizon_È()) == this.HorizonCode_Horizon_È();
        }
        
        public static Set HorizonCode_Horizon_È(final int p_180053_0_) {
            final EnumSet var1 = EnumSet.noneOf(HorizonCode_Horizon_È.class);
            for (final HorizonCode_Horizon_È var5 : values()) {
                if (var5.Â(p_180053_0_)) {
                    var1.add(var5);
                }
            }
            return var1;
        }
        
        public static int HorizonCode_Horizon_È(final Set p_180056_0_) {
            int var1 = 0;
            for (final HorizonCode_Horizon_È var3 : p_180056_0_) {
                var1 |= var3.HorizonCode_Horizon_È();
            }
            return var1;
        }
    }
}
