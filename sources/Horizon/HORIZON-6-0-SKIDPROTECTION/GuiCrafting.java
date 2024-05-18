package HORIZON-6-0-SKIDPROTECTION;

public class GuiCrafting extends GuiContainer
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Â = "CL_00000750";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/gui/container/crafting_table.png");
    }
    
    public GuiCrafting(final InventoryPlayer p_i45504_1_, final World worldIn) {
        this(p_i45504_1_, worldIn, BlockPos.HorizonCode_Horizon_È);
    }
    
    public GuiCrafting(final InventoryPlayer p_i45505_1_, final World worldIn, final BlockPos p_i45505_3_) {
        super(new ContainerWorkbench(p_i45505_1_, worldIn, p_i45505_3_));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("container.crafting", new Object[0]), 28, 6, 4210752);
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("container.inventory", new Object[0]), 8, this.ÂµÈ - 96 + 2, 4210752);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiCrafting.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiCrafting.HorizonCode_Horizon_È);
        final int var4 = (GuiCrafting.Çªà¢ - this.áˆºÑ¢Õ) / 2;
        final int var5 = (GuiCrafting.Ê - this.ÂµÈ) / 2;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
    }
}
