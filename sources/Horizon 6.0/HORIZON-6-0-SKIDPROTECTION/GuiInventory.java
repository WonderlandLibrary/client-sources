package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiInventory extends InventoryEffectRenderer
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private static final String Ý = "CL_00000761";
    
    public GuiInventory(final EntityPlayer p_i1094_1_) {
        super(p_i1094_1_.ŒÂ);
        this.ÇŽÕ = true;
    }
    
    @Override
    public void Ý() {
        if (GuiInventory.Ñ¢á.Âµá€.Ø()) {
            GuiInventory.Ñ¢á.HorizonCode_Horizon_È(new GuiContainerCreative(GuiInventory.Ñ¢á.á));
        }
        this.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        if (GuiInventory.Ñ¢á.Âµá€.Ø()) {
            GuiInventory.Ñ¢á.HorizonCode_Horizon_È(new GuiContainerCreative(GuiInventory.Ñ¢á.á));
        }
        else {
            super.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY) {
        this.É.HorizonCode_Horizon_È(I18n.HorizonCode_Horizon_È("container.crafting", new Object[0]), 86, 16, 4210752);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È = mouseX;
        this.Â = mouseY;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.Ý(1.0f, 1.0f, 1.0f, 1.0f);
        GuiInventory.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiInventory.áŒŠÆ);
        final int var4 = this.ˆÏ­;
        final int var5 = this.£á;
        this.Â(var4, var5, 0, 0, this.áˆºÑ¢Õ, this.ÂµÈ);
        HorizonCode_Horizon_È(var4 + 51, var5 + 75, 30, var4 + 51 - this.HorizonCode_Horizon_È, var5 + 75 - 50 - this.Â, GuiInventory.Ñ¢á.á);
    }
    
    public static void HorizonCode_Horizon_È(final int p_147046_0_, final int p_147046_1_, final int p_147046_2_, final float p_147046_3_, final float p_147046_4_, final EntityLivingBase p_147046_5_) {
        GlStateManager.à();
        GlStateManager.Çªà¢();
        GlStateManager.Â(p_147046_0_, p_147046_1_, 50.0f);
        GlStateManager.HorizonCode_Horizon_È(-p_147046_2_, p_147046_2_, p_147046_2_);
        GlStateManager.Â(180.0f, 0.0f, 0.0f, 1.0f);
        final float var6 = p_147046_5_.¥É;
        final float var7 = p_147046_5_.É;
        final float var8 = p_147046_5_.áƒ;
        final float var9 = p_147046_5_.Š;
        final float var10 = p_147046_5_.ÂµÕ;
        GlStateManager.Â(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.Â();
        GlStateManager.Â(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.Â(-(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        p_147046_5_.¥É = (float)Math.atan(p_147046_3_ / 40.0f) * 20.0f;
        p_147046_5_.É = (float)Math.atan(p_147046_3_ / 40.0f) * 40.0f;
        p_147046_5_.áƒ = -(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f;
        p_147046_5_.ÂµÕ = p_147046_5_.É;
        p_147046_5_.Š = p_147046_5_.É;
        GlStateManager.Â(0.0f, 0.0f, 0.0f);
        final RenderManager var11 = Minecraft.áŒŠà().ÇªÓ();
        var11.HorizonCode_Horizon_È(180.0f);
        var11.HorizonCode_Horizon_È(false);
        var11.HorizonCode_Horizon_È(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        var11.HorizonCode_Horizon_È(true);
        p_147046_5_.¥É = var6;
        p_147046_5_.É = var7;
        p_147046_5_.áƒ = var8;
        p_147046_5_.Š = var9;
        p_147046_5_.ÂµÕ = var10;
        GlStateManager.Ê();
        RenderHelper.HorizonCode_Horizon_È();
        GlStateManager.Ñ¢á();
        GlStateManager.à(OpenGlHelper.µà);
        GlStateManager.Æ();
        GlStateManager.à(OpenGlHelper.£à);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0) {
            GuiInventory.Ñ¢á.HorizonCode_Horizon_È(new GuiAchievements(this, GuiInventory.Ñ¢á.á.c_()));
        }
        if (button.£à == 1) {
            GuiInventory.Ñ¢á.HorizonCode_Horizon_È(new GuiStats(this, GuiInventory.Ñ¢á.á.c_()));
        }
    }
}
