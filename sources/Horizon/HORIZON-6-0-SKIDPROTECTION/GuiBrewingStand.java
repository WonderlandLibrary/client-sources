package HORIZON-6-0-SKIDPROTECTION;

public class GuiBrewingStand extends GuiContainer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private final InventoryPlayer Â;
    private IInventory Ý;
    private static final String Ø­áŒŠá = "CL_00000746";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/brewing_stand.png");
    }
    
    public GuiBrewingStand(final InventoryPlayer p_i45506_1_, final IInventory p_i45506_2_) {
        super(new ContainerBrewingStand(p_i45506_1_, p_i45506_2_));
        this.Â = p_i45506_1_;
        this.Ý = p_i45506_2_;
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
        GuiBrewingStand.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiBrewingStand.HorizonCode_Horizon_È);
        final int var4 = (GuiBrewingStand.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiBrewingStand.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        final int var6 = this.Ý.HorizonCode_Horizon_È(0);
        if (var6 > 0) {
            int var7 = (int)(28.0f * (1.0f - var6 / 400.0f));
            if (var7 > 0) {
                this.Â(var4 + 97, var5 + 16, 176, 0, 9, var7);
            }
            final int var8 = var6 / 2 % 7;
            switch (var8) {
                case 0: {
                    var7 = 29;
                    break;
                }
                case 1: {
                    var7 = 24;
                    break;
                }
                case 2: {
                    var7 = 20;
                    break;
                }
                case 3: {
                    var7 = 16;
                    break;
                }
                case 4: {
                    var7 = 11;
                    break;
                }
                case 5: {
                    var7 = 6;
                    break;
                }
                case 6: {
                    var7 = 0;
                    break;
                }
            }
            if (var7 > 0) {
                this.Â(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
            }
        }
    }
}
