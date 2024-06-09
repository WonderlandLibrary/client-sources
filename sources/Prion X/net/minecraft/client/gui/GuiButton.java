package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui
{
  protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
  

  protected int width;
  

  protected int height;
  

  public int xPosition;
  
  public int yPosition;
  
  public String displayString;
  
  public int id;
  
  public boolean enabled;
  
  public boolean visible;
  
  protected boolean hovered;
  
  private static final String __OBFID = "CL_00000668";
  

  public GuiButton(int buttonId, int x, int y, String buttonText)
  {
    this(buttonId, x, y, 200, 20, buttonText);
  }
  
  public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
  {
    width = 200;
    height = 20;
    enabled = true;
    visible = true;
    id = buttonId;
    xPosition = x;
    yPosition = y;
    width = widthIn;
    height = heightIn;
    displayString = buttonText;
  }
  




  protected int getHoverState(boolean mouseOver)
  {
    byte var2 = 1;
    
    if (!enabled)
    {
      var2 = 0;
    }
    else if (mouseOver)
    {
      var2 = 2;
    }
    
    return var2;
  }
  



  public void drawButton(Minecraft mc, int mouseX, int mouseY)
  {
    if (visible)
    {
      FontRenderer var4 = fontRendererObj;
      mc.getTextureManager().bindTexture(buttonTextures);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      hovered = ((mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height));
      int var5 = getHoverState(hovered);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.blendFunc(770, 771);
      drawTexturedModalRect(xPosition, yPosition, 0, 46 + var5 * 20, width / 2, height);
      drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + var5 * 20, width / 2, height);
      mouseDragged(mc, mouseX, mouseY);
      int var6 = 14737632;
      
      if (!enabled)
      {
        var6 = 10526880;
      }
      else if (hovered)
      {
        var6 = 16777120;
      }
      
      drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, var6);
    }
  }
  




  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
  



  public void mouseReleased(int mouseX, int mouseY) {}
  



  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    return (enabled) && (visible) && (mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);
  }
  



  public boolean isMouseOver()
  {
    return hovered;
  }
  
  public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
  
  public void playPressSound(SoundHandler soundHandlerIn)
  {
    soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
  }
  
  public int getButtonWidth()
  {
    return width;
  }
  
  public void func_175211_a(int p_175211_1_)
  {
    width = p_175211_1_;
  }
}
