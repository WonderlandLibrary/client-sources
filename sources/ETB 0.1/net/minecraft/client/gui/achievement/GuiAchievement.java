package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GuiAchievement extends net.minecraft.client.gui.Gui
{
  private static final ResourceLocation achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
  private Minecraft mc;
  private int width;
  private int height;
  private String achievementTitle;
  private String achievementDescription;
  private Achievement theAchievement;
  private long notificationTime;
  private RenderItem renderItem;
  private boolean permanentNotification;
  private static final String __OBFID = "CL_00000721";
  
  public GuiAchievement(Minecraft mc)
  {
    this.mc = mc;
    renderItem = mc.getRenderItem();
  }
  
  public void displayAchievement(Achievement p_146256_1_)
  {
    achievementTitle = net.minecraft.client.resources.I18n.format("achievement.get", new Object[0]);
    achievementDescription = p_146256_1_.getStatName().getUnformattedText();
    notificationTime = Minecraft.getSystemTime();
    theAchievement = p_146256_1_;
    permanentNotification = false;
  }
  
  public void displayUnformattedAchievement(Achievement p_146255_1_)
  {
    achievementTitle = p_146255_1_.getStatName().getUnformattedText();
    achievementDescription = p_146255_1_.getDescription();
    notificationTime = (Minecraft.getSystemTime() + 2500L);
    theAchievement = p_146255_1_;
    permanentNotification = true;
  }
  
  private void updateAchievementWindowScale()
  {
    GlStateManager.viewport(0, 0, mc.displayWidth, mc.displayHeight);
    GlStateManager.matrixMode(5889);
    GlStateManager.loadIdentity();
    GlStateManager.matrixMode(5888);
    GlStateManager.loadIdentity();
    width = mc.displayWidth;
    height = mc.displayHeight;
    ScaledResolution var1 = new ScaledResolution(mc);
    width = var1.getScaledWidth();
    height = var1.getScaledHeight();
    GlStateManager.clear(256);
    GlStateManager.matrixMode(5889);
    GlStateManager.loadIdentity();
    GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
    GlStateManager.matrixMode(5888);
    GlStateManager.loadIdentity();
    GlStateManager.translate(0.0F, 0.0F, -2000.0F);
  }
  
  public void updateAchievementWindow()
  {
    if ((theAchievement != null) && (notificationTime != 0L) && (getMinecraftthePlayer != null))
    {
      double var1 = (Minecraft.getSystemTime() - notificationTime) / 3000.0D;
      
      if (!permanentNotification)
      {
        if ((var1 < 0.0D) || (var1 > 1.0D))
        {
          notificationTime = 0L;
        }
        
      }
      else if (var1 > 0.5D)
      {
        var1 = 0.5D;
      }
      
      updateAchievementWindowScale();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      double var3 = var1 * 2.0D;
      
      if (var3 > 1.0D)
      {
        var3 = 2.0D - var3;
      }
      
      var3 *= 4.0D;
      var3 = 1.0D - var3;
      
      if (var3 < 0.0D)
      {
        var3 = 0.0D;
      }
      
      var3 *= var3;
      var3 *= var3;
      int var5 = width - 160;
      int var6 = 0 - (int)(var3 * 36.0D);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179098_w();
      mc.getTextureManager().bindTexture(achievementBg);
      GlStateManager.disableLighting();
      drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
      
      if (permanentNotification)
      {
        mc.fontRendererObj.drawSplitString(achievementDescription, var5 + 30, var6 + 7, 120, -1);
      }
      else
      {
        mc.fontRendererObj.drawString(achievementTitle, var5 + 30, var6 + 7, 65280);
        mc.fontRendererObj.drawString(achievementDescription, var5 + 30, var6 + 18, -1);
      }
      
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();
      GlStateManager.enableLighting();
      renderItem.func_180450_b(theAchievement.theItemStack, var5 + 8, var6 + 8);
      GlStateManager.disableLighting();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
    }
  }
  
  public void clearAchievements()
  {
    theAchievement = null;
    notificationTime = 0L;
  }
}
