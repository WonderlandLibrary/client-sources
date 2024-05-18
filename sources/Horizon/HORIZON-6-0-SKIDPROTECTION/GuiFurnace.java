package HORIZON-6-0-SKIDPROTECTION;

public class GuiFurnace extends GuiContainer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final InventoryPlayer Â;
    private IInventory Ý;
    private static final String Ø­áŒŠá = "CL_00000758";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/furnace.png");
    }
    
    public GuiFurnace(final InventoryPlayer p_i45501_1_, final IInventory p_i45501_2_) {
        super(new ContainerFurnace(p_i45501_1_, p_i45501_2_));
        this.Â = p_i45501_1_;
        this.Ý = p_i45501_2_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        final String var3 = this.Ý.Ý().Ø();
        this.É.HorizonCode_Horizon_È(var3, this.áˆºÑ¢Õ / 2 - this.É.HorizonCode_Horizon_È(var3) / 2, 6, 4210752);
        this.É.HorizonCode_Horizon_È(this.Â.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiFurnace.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiFurnace.HorizonCode_Horizon_È);
        final int var4 = (GuiFurnace.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiFurnace.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        if (TileEntityFurnace.HorizonCode_Horizon_È(this.Ý)) {
            final int var6 = this.áŒŠÆ(13);
            this.Â(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 1);
        }
        final int var6 = this.Ø(24);
        this.Â(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
    }
    
    private int Ø(final int p_175381_1_) {
        final int var2 = this.Ý.HorizonCode_Horizon_È(2);
        final int var3 = this.Ý.HorizonCode_Horizon_È(3);
        return (var3 != 0 && var2 != 0) ? (var2 * p_175381_1_ / var3) : 0;
    }
    
    private int áŒŠÆ(final int p_175382_1_) {
        int var2 = this.Ý.HorizonCode_Horizon_È(1);
        if (var2 == 0) {
            var2 = 200;
        }
        return this.Ý.HorizonCode_Horizon_È(0) * p_175382_1_ / var2;
    }
}
