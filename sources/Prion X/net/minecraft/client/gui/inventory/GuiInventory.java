package net.minecraft.client.gui.inventory;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends InventoryEffectRenderer
{
  private float oldMouseX;
  private float oldMouseY;
  private static final String __OBFID = "CL_00000761";
  
  public GuiInventory(EntityPlayer p_i1094_1_)
  {
    super(inventoryContainer);
    allowUserInput = true;
  }
  



  public void updateScreen()
  {
    if (mc.playerController.isInCreativeMode())
    {
      mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
    }
    
    func_175378_g();
  }
  



  public void initGui()
  {
    buttonList.clear();
    
    if (mc.playerController.isInCreativeMode())
    {
      mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
    }
    else
    {
      super.initGui();
    }
  }
  



  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
  {
    fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    super.drawScreen(mouseX, mouseY, partialTicks);
    oldMouseX = mouseX;
    oldMouseY = mouseY;
  }
  



  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(inventoryBackground);
    int var4 = guiLeft;
    int var5 = guiTop;
    drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
    drawEntityOnScreen(var4 + 51, var5 + 75, 30, var4 + 51 - oldMouseX, var5 + 75 - 50 - oldMouseY, mc.thePlayer);
  }
  



  public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
  {
    GlStateManager.enableColorMaterial();
    GlStateManager.pushMatrix();
    GlStateManager.translate(p_147046_0_, p_147046_1_, 50.0F);
    GlStateManager.scale(-p_147046_2_, p_147046_2_, p_147046_2_);
    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    float var6 = renderYawOffset;
    float var7 = rotationYaw;
    float var8 = rotationPitch;
    float var9 = prevRotationYawHead;
    float var10 = rotationYawHead;
    GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
    RenderHelper.enableStandardItemLighting();
    GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(-(float)Math.atan(p_147046_4_ / 40.0F) * 20.0F, 1.0F, 0.0F, 0.0F);
    renderYawOffset = ((float)Math.atan(p_147046_3_ / 40.0F) * 20.0F);
    rotationYaw = ((float)Math.atan(p_147046_3_ / 40.0F) * 40.0F);
    rotationPitch = (-(float)Math.atan(p_147046_4_ / 40.0F) * 20.0F);
    rotationYawHead = rotationYaw;
    prevRotationYawHead = rotationYaw;
    GlStateManager.translate(0.0F, 0.0F, 0.0F);
    RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
    var11.func_178631_a(180.0F);
    var11.func_178633_a(false);
    var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
    var11.func_178633_a(true);
    renderYawOffset = var6;
    rotationYaw = var7;
    rotationPitch = var8;
    prevRotationYawHead = var9;
    rotationYawHead = var10;
    GlStateManager.popMatrix();
    RenderHelper.disableStandardItemLighting();
    GlStateManager.disableRescaleNormal();
    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    GlStateManager.func_179090_x();
    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 0)
    {
      mc.displayGuiScreen(new GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
    }
    
    if (id == 1)
    {
      mc.displayGuiScreen(new GuiStats(this, mc.thePlayer.getStatFileWriter()));
    }
  }
}
