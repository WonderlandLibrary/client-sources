package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float oldMouseX;
    private float oldMouseY;
    
    public GuiInventory(final EntityPlayer p_i1094_1_) {
        super(p_i1094_1_.inventoryContainer);
        this.allowUserInput = true;
    }
    
    public static void drawEntityOnScreen(final int p_147046_0_, final int p_147046_1_, final int p_147046_2_, final float p_147046_3_, final float p_147046_4_, final EntityLivingBase p_147046_5_) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 50.0f);
        GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float var6 = p_147046_5_.renderYawOffset;
        final float var7 = p_147046_5_.rotationYaw;
        final float var8 = p_147046_5_.rotationPitch;
        final float var9 = p_147046_5_.prevRotationYawHead;
        final float var10 = p_147046_5_.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        p_147046_5_.renderYawOffset = (float)Math.atan(p_147046_3_ / 40.0f) * 20.0f;
        p_147046_5_.rotationYaw = (float)Math.atan(p_147046_3_ / 40.0f) * 40.0f;
        p_147046_5_.rotationPitch = -(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.func_178631_a(180.0f);
        var11.func_178633_a(false);
        var11.renderEntityWithPosYaw(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        var11.func_178633_a(true);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.func_179090_x();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    public void updateScreen() {
        if (GuiInventory.mc.playerController.isInCreativeMode()) {
            GuiInventory.mc.displayGuiScreen(new GuiContainerCreative(GuiInventory.mc.thePlayer));
        }
        this.func_175378_g();
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (GuiInventory.mc.playerController.isInCreativeMode()) {
            GuiInventory.mc.displayGuiScreen(new GuiContainerCreative(GuiInventory.mc.thePlayer));
        }
        else {
            super.initGui();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiInventory.mc.getTextureManager().bindTexture(GuiInventory.inventoryBackground);
        final int var4 = this.guiLeft;
        final int var5 = this.guiTop;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(var4 + 51, var5 + 75, 30, var4 + 51 - this.oldMouseX, var5 + 75 - 50 - this.oldMouseY, GuiInventory.mc.thePlayer);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            GuiInventory.mc.displayGuiScreen(new GuiAchievements(this, GuiInventory.mc.thePlayer.getStatFileWriter()));
        }
        if (button.id == 1) {
            GuiInventory.mc.displayGuiScreen(new GuiStats(this, GuiInventory.mc.thePlayer.getStatFileWriter()));
        }
    }
}
