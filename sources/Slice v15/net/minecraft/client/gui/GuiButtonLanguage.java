package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;

public class GuiButtonLanguage extends GuiButton
{
  private static final String __OBFID = "CL_00000672";
  
  public GuiButtonLanguage(int p_i1041_1_, int p_i1041_2_, int p_i1041_3_)
  {
    super(p_i1041_1_, p_i1041_2_, p_i1041_3_, 20, 20, "");
  }
  



  public void drawButton(Minecraft mc, int mouseX, int mouseY)
  {
    if (visible)
    {
      mc.getTextureManager().bindTexture(GuiButton.buttonTextures);
      net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      boolean var4 = (mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);
      int var5 = 106;
      
      if (var4)
      {
        var5 += height;
      }
      
      drawTexturedModalRect(xPosition, yPosition, 0, var5, width, height);
    }
  }
}
