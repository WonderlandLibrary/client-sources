package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float xSize_lo;
    private float ySize_lo;
    
    public GuiInventory(final EntityPlayer par1EntityPlayer) {
        super(par1EntityPlayer.inventoryContainer);
        this.allowUserInput = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
    }
    
    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(Minecraft.thePlayer));
        }
        else {
            super.initGui();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.crafting"), 86, 16, 4210752);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        this.xSize_lo = par1;
        this.ySize_lo = par2;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/inventory.png");
        final int var4 = this.guiLeft;
        final int var5 = this.guiTop;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        drawPlayerOnGui(this.mc, var4 + 51, var5 + 75, 30, var4 + 51 - this.xSize_lo, var5 + 75 - 50 - this.ySize_lo);
    }
    
    public static void drawPlayerOnGui(final Minecraft par0Minecraft, final int par1, final int par2, final int par3, final float par4, final float par5) {
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef(par1, par2, 50.0f);
        GL11.glScalef(-par3, par3, par3);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        final float var6 = Minecraft.thePlayer.renderYawOffset;
        final float var7 = Minecraft.thePlayer.rotationYaw;
        final float var8 = Minecraft.thePlayer.rotationPitch;
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-(float)Math.atan(par5 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        Minecraft.thePlayer.renderYawOffset = (float)Math.atan(par4 / 40.0f) * 20.0f;
        Minecraft.thePlayer.rotationYaw = (float)Math.atan(par4 / 40.0f) * 40.0f;
        Minecraft.thePlayer.rotationPitch = -(float)Math.atan(par5 / 40.0f) * 20.0f;
        Minecraft.thePlayer.rotationYawHead = Minecraft.thePlayer.rotationYaw;
        GL11.glTranslatef(0.0f, Minecraft.thePlayer.yOffset, 0.0f);
        RenderManager.instance.playerViewY = 180.0f;
        RenderManager.instance.renderEntityWithPosYaw(Minecraft.thePlayer, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        Minecraft.thePlayer.renderYawOffset = var6;
        Minecraft.thePlayer.rotationYaw = var7;
        Minecraft.thePlayer.rotationPitch = var8;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
        }
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
        }
    }
}
