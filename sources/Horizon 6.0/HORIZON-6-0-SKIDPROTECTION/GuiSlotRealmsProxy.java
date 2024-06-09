package HORIZON-6-0-SKIDPROTECTION;

public class GuiSlotRealmsProxy extends GuiSlot
{
    private final RealmsScrolledSelectionList HorizonCode_Horizon_È;
    private static final String Â = "CL_00001846";
    
    public GuiSlotRealmsProxy(final RealmsScrolledSelectionList selectionListIn, final int p_i1085_2_, final int p_i1085_3_, final int p_i1085_4_, final int p_i1085_5_, final int p_i1085_6_) {
        super(Minecraft.áŒŠà(), p_i1085_2_, p_i1085_3_, p_i1085_4_, p_i1085_5_, p_i1085_6_);
        this.HorizonCode_Horizon_È = selectionListIn;
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
}
