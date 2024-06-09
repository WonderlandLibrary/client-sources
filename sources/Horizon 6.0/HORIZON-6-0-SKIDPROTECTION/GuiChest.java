package HORIZON-6-0-SKIDPROTECTION;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation_1975012498 Â;
    private IInventory Ý;
    public static IInventory HorizonCode_Horizon_È;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000749";
    
    static {
        Â = new ResourceLocation_1975012498("textures/gui/container/generic_54.png");
    }
    
    public GuiChest(final IInventory p_i46315_1_, final IInventory p_i46315_2_) {
        super(new ContainerChest(p_i46315_1_, p_i46315_2_, Minecraft.áŒŠà().á));
        this.Ý = p_i46315_1_;
        GuiChest.HorizonCode_Horizon_È = p_i46315_2_;
        this.ÇŽÕ = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        this.Ø­áŒŠá = p_i46315_2_.áŒŠÆ() / 9;
        this.ÂµÈ = var4 + this.Ø­áŒŠá * 18;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(GuiChest.HorizonCode_Horizon_È.Ý().Ø(), 8, 6, 4210752);
        this.É.HorizonCode_Horizon_È(this.Ý.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiChest.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiChest.Â);
        final int var4 = (GuiChest.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiChest.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.Ø­áŒŠá * 18 + 17);
        this.Â(var4, var5 + this.Ø­áŒŠá * 18 + 17, 0, 126, this.áˆºÑ¢Õ, 96);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        final int posY = (GuiChest.Ê - this.ÂµÈ) / 2 + 2;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton par1) {
        if (par1.£à == 2) {
            try {
                for (int x = 0; x < GuiChest.á.Ý.size(); ++x) {
                    GuiChest.Ñ¢á.Âµá€.HorizonCode_Horizon_È(GuiChest.á.Ø­áŒŠá, x, 0, 1, GuiChest.Ñ¢á.á);
                }
            }
            catch (Exception ex) {}
        }
        if (par1.£à == 1) {
            try {
                for (int x = 0; x < GuiChest.HorizonCode_Horizon_È.áŒŠÆ(); ++x) {
                    GuiChest.Ñ¢á.Âµá€.HorizonCode_Horizon_È(GuiChest.á.Ø­áŒŠá, x, 0, 1, GuiChest.Ñ¢á.á);
                }
            }
            catch (Exception ex2) {}
        }
    }
}
