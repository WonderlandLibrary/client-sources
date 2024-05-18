package HORIZON-6-0-SKIDPROTECTION;

public abstract class GuiListExtended extends GuiSlot
{
    private static final String HorizonCode_Horizon_È = "CL_00000674";
    
    public GuiListExtended(final Minecraft mcIn, final int p_i45010_2_, final int p_i45010_3_, final int p_i45010_4_, final int p_i45010_5_, final int p_i45010_6_) {
        super(mcIn, p_i45010_2_, p_i45010_3_, p_i45010_4_, p_i45010_5_, p_i45010_6_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int slotIndex) {
        return false;
    }
    
    @Override
    protected void Â() {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        this.Â(p_180791_1_).HorizonCode_Horizon_È(p_180791_1_, p_180791_2_, p_180791_3_, this.o_(), p_180791_4_, p_180791_5_, p_180791_6_, this.Ý(p_180791_5_, p_180791_6_) == p_180791_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int p_178040_1_, final int p_178040_2_, final int p_178040_3_) {
        this.Â(p_178040_1_).HorizonCode_Horizon_È(p_178040_1_, p_178040_2_, p_178040_3_);
    }
    
    public boolean Â(final int p_148179_1_, final int p_148179_2_, final int p_148179_3_) {
        if (this.Âµá€(p_148179_2_)) {
            final int var4 = this.Ý(p_148179_1_, p_148179_2_);
            if (var4 >= 0) {
                final int var5 = this.áŒŠÆ + this.Ø­áŒŠá / 2 - this.o_() / 2 + 2;
                final int var6 = this.Ó + 4 - this.£á() + var4 * this.áˆºÑ¢Õ + this.Æ;
                final int var7 = p_148179_1_ - var5;
                final int var8 = p_148179_2_ - var6;
                if (this.Â(var4).HorizonCode_Horizon_È(var4, p_148179_1_, p_148179_2_, p_148179_3_, var7, var8)) {
                    this.Â(false);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean Ý(final int p_148181_1_, final int p_148181_2_, final int p_148181_3_) {
        for (int var4 = 0; var4 < this.HorizonCode_Horizon_È(); ++var4) {
            final int var5 = this.áŒŠÆ + this.Ø­áŒŠá / 2 - this.o_() / 2 + 2;
            final int var6 = this.Ó + 4 - this.£á() + var4 * this.áˆºÑ¢Õ + this.Æ;
            final int var7 = p_148181_1_ - var5;
            final int var8 = p_148181_2_ - var6;
            this.Â(var4).Â(var4, p_148181_1_, p_148181_2_, p_148181_3_, var7, var8);
        }
        this.Â(true);
        return false;
    }
    
    public abstract HorizonCode_Horizon_È Â(final int p0);
    
    public interface HorizonCode_Horizon_È
    {
        void HorizonCode_Horizon_È(final int p0, final int p1, final int p2);
        
        void HorizonCode_Horizon_È(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final boolean p7);
        
        boolean HorizonCode_Horizon_È(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
        
        void Â(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    }
}
