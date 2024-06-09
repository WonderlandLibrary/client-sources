package HORIZON-6-0-SKIDPROTECTION;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private IInventory Â;
    private IInventory Ý;
    private EntityHorse Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    private static final String à = "CL_00000760";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/horse.png");
    }
    
    public GuiScreenHorseInventory(final IInventory p_i1093_1_, final IInventory p_i1093_2_, final EntityHorse p_i1093_3_) {
        super(new ContainerHorseInventory(p_i1093_1_, p_i1093_2_, p_i1093_3_, Minecraft.áŒŠà().á));
        this.Â = p_i1093_1_;
        this.Ý = p_i1093_2_;
        this.Ø­áŒŠá = p_i1093_3_;
        this.ÇŽÕ = false;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(this.Ý.Ý().Ø(), 8, 6, 4210752);
        this.É.HorizonCode_Horizon_È(this.Â.Ý().Ø(), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiScreenHorseInventory.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiScreenHorseInventory.HorizonCode_Horizon_È);
        final int var4 = (GuiScreenHorseInventory.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiScreenHorseInventory.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        if (this.Ø­áŒŠá.Ñ¢Õ()) {
            this.Â(var4 + 79, var5 + 17, 0, this.ÂµÈ, 90, 54);
        }
        if (this.Ø­áŒŠá.ÇŽáˆºÈ()) {
            this.Â(var4 + 7, var5 + 35, 0, this.ÂµÈ + 54, 18, 18);
        }
        GuiInventory.HorizonCode_Horizon_È(var4 + 51, var5 + 60, 17, var4 + 51 - this.Âµá€, var5 + 75 - 50 - this.Ó, this.Ø­áŒŠá);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.Âµá€ = mouseX;
        this.Ó = mouseY;
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
