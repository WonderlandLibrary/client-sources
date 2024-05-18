package HORIZON-6-0-SKIDPROTECTION;

public class GuiDispenser extends GuiContainer
{
    private static final ResourceLocation_1975012498 Â;
    private final InventoryPlayer Ý;
    public IInventory HorizonCode_Horizon_È;
    private static final String Ø­áŒŠá = "CL_00000765";
    
    static {
        Â = new ResourceLocation_1975012498("textures/gui/container/dispenser.png");
    }
    
    public GuiDispenser(final InventoryPlayer p_i45503_1_, final IInventory p_i45503_2_) {
        super(new ContainerDispenser(p_i45503_1_, p_i45503_2_));
        this.Ý = p_i45503_1_;
        this.HorizonCode_Horizon_È = p_i45503_2_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        final String var3 = this.HorizonCode_Horizon_È.Ý().Ø();
        this.É.HorizonCode_Horizon_È(var3, this.áˆºÑ¢Õ / 2 - this.É.HorizonCode_Horizon_È(var3) / 2, 6, 4210752);
        this.É.HorizonCode_Horizon_È(this.Ý.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiDispenser.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiDispenser.Â);
        final int var4 = (GuiDispenser.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiDispenser.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
    }
}
