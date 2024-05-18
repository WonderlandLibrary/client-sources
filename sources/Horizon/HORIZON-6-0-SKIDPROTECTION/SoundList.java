package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class SoundList
{
    private final List HorizonCode_Horizon_È;
    private boolean Â;
    private SoundCategory Ý;
    private static final String Ø­áŒŠá = "CL_00001121";
    
    public SoundList() {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
    }
    
    public List HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Â() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_148572_1_) {
        this.Â = p_148572_1_;
    }
    
    public SoundCategory Ý() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final SoundCategory p_148571_1_) {
        this.Ý = p_148571_1_;
    }
    
    public static class HorizonCode_Horizon_È
    {
        private String HorizonCode_Horizon_È;
        private float Â;
        private float Ý;
        private int Ø­áŒŠá;
        private SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È Âµá€;
        private boolean Ó;
        private static final String à = "CL_00001122";
        
        public HorizonCode_Horizon_È() {
            this.Â = 1.0f;
            this.Ý = 1.0f;
            this.Ø­áŒŠá = 1;
            this.Âµá€ = SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            this.Ó = false;
        }
        
        public String HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
        
        public void HorizonCode_Horizon_È(final String p_148561_1_) {
            this.HorizonCode_Horizon_È = p_148561_1_;
        }
        
        public float Â() {
            return this.Â;
        }
        
        public void HorizonCode_Horizon_È(final float p_148553_1_) {
            this.Â = p_148553_1_;
        }
        
        public float Ý() {
            return this.Ý;
        }
        
        public void Â(final float p_148559_1_) {
            this.Ý = p_148559_1_;
        }
        
        public int Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
        
        public void HorizonCode_Horizon_È(final int p_148554_1_) {
            this.Ø­áŒŠá = p_148554_1_;
        }
        
        public SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È Âµá€() {
            return this.Âµá€;
        }
        
        public void HorizonCode_Horizon_È(final SoundList.HorizonCode_Horizon_È.HorizonCode_Horizon_È p_148562_1_) {
            this.Âµá€ = p_148562_1_;
        }
        
        public boolean Ó() {
            return this.Ó;
        }
        
        public void HorizonCode_Horizon_È(final boolean p_148557_1_) {
            this.Ó = p_148557_1_;
        }
        
        public enum HorizonCode_Horizon_È
        {
            HorizonCode_Horizon_È("FILE", 0, "FILE", 0, "file"), 
            Â("SOUND_EVENT", 1, "SOUND_EVENT", 1, "event");
            
            private final String Ý;
            private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
            private static final String Âµá€ = "CL_00001123";
            
            static {
                Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
                Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            }
            
            private HorizonCode_Horizon_È(final String s, final int n, final String p_i45109_1_, final int p_i45109_2_, final String p_i45109_3_) {
                this.Ý = p_i45109_3_;
            }
            
            public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_148580_0_) {
                for (final HorizonCode_Horizon_È var4 : values()) {
                    if (var4.Ý.equals(p_148580_0_)) {
                        return var4;
                    }
                }
                return null;
            }
        }
    }
}
