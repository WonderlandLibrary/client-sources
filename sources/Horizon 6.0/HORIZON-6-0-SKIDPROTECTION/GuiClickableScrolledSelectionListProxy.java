package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot
{
    private final RealmsClickableScrolledSelectionList HorizonCode_Horizon_È;
    private static final String Â = "CL_00001939";
    
    public GuiClickableScrolledSelectionListProxy(final RealmsClickableScrolledSelectionList p_i45526_1_, final int p_i45526_2_, final int p_i45526_3_, final int p_i45526_4_, final int p_i45526_5_, final int p_i45526_6_) {
        super(Minecraft.áŒŠà(), p_i45526_2_, p_i45526_3_, p_i45526_4_, p_i45526_5_, p_i45526_6_);
        this.HorizonCode_Horizon_È = p_i45526_1_;
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
    
    public int Ý() {
        return super.Ø­áŒŠá;
    }
    
    public int Ø­áŒŠá() {
        return super.á;
    }
    
    public int Âµá€() {
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
        if (this.Å > 0.0f && Mouse.getEventButtonState()) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ó, this.à, this.Æ, this.£à, this.áˆºÑ¢Õ);
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_178043_1_, final int p_178043_2_, final int p_178043_3_, final Tezzelator p_178043_4_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_178043_1_, p_178043_2_, p_178043_3_, p_178043_4_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_, final int p_148120_4_) {
        for (int var5 = this.HorizonCode_Horizon_È(), var6 = 0; var6 < var5; ++var6) {
            final int var7 = p_148120_2_ + var6 * this.áˆºÑ¢Õ + this.Æ;
            final int var8 = this.áˆºÑ¢Õ - 4;
            if (var7 > this.à || var7 + var8 < this.Ó) {
                this.HorizonCode_Horizon_È(var6, p_148120_1_, var7);
            }
            if (this.Ø­à && this.HorizonCode_Horizon_È(var6)) {
                this.HorizonCode_Horizon_È(this.Ø­áŒŠá, var7, var8, Tezzelator.Â);
            }
            this.HorizonCode_Horizon_È(var6, p_148120_1_, var7, var8, p_148120_3_, p_148120_4_);
        }
    }
}
