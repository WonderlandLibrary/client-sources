package HORIZON-6-0-SKIDPROTECTION;

public class GuiLockIconButton extends GuiButton
{
    private boolean HorizonCode_Horizon_È;
    private static final String Â = "CL_00001952";
    
    public GuiLockIconButton(final int p_i45538_1_, final int p_i45538_2_, final int p_i45538_3_) {
        super(p_i45538_1_, p_i45538_2_, p_i45538_3_, 20, 20, "");
        this.HorizonCode_Horizon_È = false;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void Â(final boolean p_175229_1_) {
        this.HorizonCode_Horizon_È = p_175229_1_;
    }
    
    @Override
    public void Ý(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.ˆà) {
            mc.¥à().HorizonCode_Horizon_È(GuiButton.áˆºÑ¢Õ);
            GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
            final boolean var4 = mouseX >= this.ˆÏ­ && mouseY >= this.£á && mouseX < this.ˆÏ­ + this.ÂµÈ && mouseY < this.£á + this.á;
            HorizonCode_Horizon_È var5;
            if (this.HorizonCode_Horizon_È) {
                if (!this.µà) {
                    var5 = GuiLockIconButton.HorizonCode_Horizon_È.Ý;
                }
                else if (var4) {
                    var5 = GuiLockIconButton.HorizonCode_Horizon_È.Â;
                }
                else {
                    var5 = GuiLockIconButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                }
            }
            else if (!this.µà) {
                var5 = GuiLockIconButton.HorizonCode_Horizon_È.Ó;
            }
            else if (var4) {
                var5 = GuiLockIconButton.HorizonCode_Horizon_È.Âµá€;
            }
            else {
                var5 = GuiLockIconButton.HorizonCode_Horizon_È.Ø­áŒŠá;
            }
            this.Â(this.ˆÏ­, this.£á, var5.HorizonCode_Horizon_È(), var5.Â(), this.ÂµÈ, this.á);
        }
    }
    
    enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("LOCKED", 0, "LOCKED", 0, 0, 146), 
        Â("LOCKED_HOVER", 1, "LOCKED_HOVER", 1, 0, 166), 
        Ý("LOCKED_DISABLED", 2, "LOCKED_DISABLED", 2, 0, 186), 
        Ø­áŒŠá("UNLOCKED", 3, "UNLOCKED", 3, 20, 146), 
        Âµá€("UNLOCKED_HOVER", 4, "UNLOCKED_HOVER", 4, 20, 166), 
        Ó("UNLOCKED_DISABLED", 5, "UNLOCKED_DISABLED", 5, 20, 186);
        
        private final int à;
        private final int Ø;
        private static final HorizonCode_Horizon_È[] áŒŠÆ;
        private static final String áˆºÑ¢Õ = "CL_00001951";
        
        static {
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45537_1_, final int p_i45537_2_, final int p_i45537_3_, final int p_i45537_4_) {
            this.à = p_i45537_3_;
            this.Ø = p_i45537_4_;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.à;
        }
        
        public int Â() {
            return this.Ø;
        }
    }
}
