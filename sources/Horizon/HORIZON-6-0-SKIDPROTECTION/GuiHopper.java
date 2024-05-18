package HORIZON-6-0-SKIDPROTECTION;

public class GuiHopper extends GuiContainer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private IInventory Â;
    private IInventory Ý;
    private static final String Ø­áŒŠá = "CL_00000759";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/hopper.png");
    }
    
    public GuiHopper(final InventoryPlayer p_i1092_1_, final IInventory p_i1092_2_) {
        super(new ContainerHopper(p_i1092_1_, p_i1092_2_, Minecraft.áŒŠà().á));
        this.Â = p_i1092_1_;
        this.Ý = p_i1092_2_;
        this.ÇŽÕ = false;
        this.ÂµÈ = 133;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(this.Ý.Ý().Ø(), 8, 6, 4210752);
        this.É.HorizonCode_Horizon_È(this.Â.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiHopper.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiHopper.HorizonCode_Horizon_È);
        final int var4 = (GuiHopper.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiHopper.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
    }
}
