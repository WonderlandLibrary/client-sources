package HORIZON-6-0-SKIDPROTECTION;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsSimpleScrolledSelectionList HorizonCode_Horizon_È;
    private static final String Â = "CL_00001938";
    
    public GuiSimpleScrolledSelectionListProxy(final RealmsSimpleScrolledSelectionList p_i45525_1_, final int p_i45525_2_, final int p_i45525_3_, final int p_i45525_4_, final int p_i45525_5_, final int p_i45525_6_) {
        super(Minecraft.áŒŠà(), p_i45525_2_, p_i45525_3_, p_i45525_4_, p_i45525_5_, p_i45525_6_);
        this.HorizonCode_Horizon_È = p_i45525_1_;
    }
    
    @Override
    protected int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(slotIndex, isDoubleClick, mouseX, mouseY);
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int slotIndex) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(slotIndex);
    }
    
    @Override
    protected void Â() {
        this.HorizonCode_Horizon_È.Âµá€();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180791_1_, p_180791_2_, p_180791_3_, p_180791_4_, p_180791_5_, p_180791_6_);
    }
    
    public int Ø­áŒŠá() {
        return super.Ø­áŒŠá;
    }
    
    public int Âµá€() {
        return super.á;
    }
    
    public int áŒŠÆ() {
        return super.ÂµÈ;
    }
    
    @Override
    protected int Ó() {
        return this.HorizonCode_Horizon_È.Ó();
    }
    
    @Override
    protected int à() {
        return this.HorizonCode_Horizon_È.à();
    }
    
    @Override
    public void Ø() {
        super.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
        if (this.¥Æ) {
            this.ÂµÈ = p_148128_1_;
            this.á = p_148128_2_;
            this.Â();
            final int var4 = this.à();
            final int var5 = var4 + 6;
            this.á();
            GlStateManager.Ó();
            GlStateManager.£á();
            final Tessellator var6 = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer var7 = var6.Ý();
            final int var8 = this.áŒŠÆ + this.Ø­áŒŠá / 2 - this.o_() / 2 + 2;
            final int var9 = this.Ó + 4 - (int)this.£à;
            if (this.µÕ) {
                this.HorizonCode_Horizon_È(var8, var9, var6);
            }
            this.HorizonCode_Horizon_È(var8, var9, p_148128_1_, p_148128_2_);
            GlStateManager.áŒŠÆ();
            final boolean var10 = true;
            this.Ý(0, this.Ó, 255, 255);
            this.Ý(this.à, this.Âµá€, 255, 255);
            GlStateManager.á();
            GlStateManager.HorizonCode_Horizon_È(770, 771, 0, 1);
            GlStateManager.Ý();
            GlStateManager.áˆºÑ¢Õ(7425);
            GlStateManager.Æ();
            final int var11 = this.ˆÏ­();
            if (var11 > 0) {
                int var12 = (this.à - this.Ó) * (this.à - this.Ó) / this.Ó();
                var12 = MathHelper.HorizonCode_Horizon_È(var12, 32, this.à - this.Ó - 8);
                int var13 = (int)this.£à * (this.à - this.Ó - var12) / var11 + this.Ó;
                if (var13 < this.Ó) {
                    var13 = this.Ó;
                }
                var7.Â();
                var7.HorizonCode_Horizon_È(0, 255);
                var7.HorizonCode_Horizon_È(var4, this.à, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, this.à, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, this.Ó, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var4, this.Ó, 0.0, 0.0, 0.0);
                var6.Â();
                var7.Â();
                var7.HorizonCode_Horizon_È(8421504, 255);
                var7.HorizonCode_Horizon_È(var4, var13 + var12, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, var13 + var12, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var5, var13, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var4, var13, 0.0, 0.0, 0.0);
                var6.Â();
                var7.Â();
                var7.HorizonCode_Horizon_È(12632256, 255);
                var7.HorizonCode_Horizon_È(var4, var13 + var12 - 1, 0.0, 0.0, 1.0);
                var7.HorizonCode_Horizon_È(var5 - 1, var13 + var12 - 1, 0.0, 1.0, 1.0);
                var7.HorizonCode_Horizon_È(var5 - 1, var13, 0.0, 1.0, 0.0);
                var7.HorizonCode_Horizon_È(var4, var13, 0.0, 0.0, 0.0);
                var6.Â();
            }
            this.Â(p_148128_1_, p_148128_2_);
            GlStateManager.µÕ();
            GlStateManager.áˆºÑ¢Õ(7424);
            GlStateManager.Ø­áŒŠá();
            GlStateManager.ÂµÈ();
        }
    }
}
