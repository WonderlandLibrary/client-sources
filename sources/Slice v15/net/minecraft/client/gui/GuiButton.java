package net.minecraft.client.gui;

import net.SliceClient.Gui.RenderHelper;
import net.SliceClient.Utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui
{
  protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
  protected int width;
  private final TimeHelper time;
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
    time = new TimeHelper();
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
    if (!enabled) {
      var2 = 0;
    } else if (mouseOver) {
      var2 = 2;
    }
    return var2;
  }
  
  public void drawButton(Minecraft mc, int mouseX, int mouseY)
  {
    if (visible)
    {
      FontRenderer var4 = Minecraft.fontRendererObj;
      mc.getTextureManager().bindTexture(buttonTextures);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      hovered = ((mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height));
      int var2 = getHoverState(hovered);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.blendFunc(770, 771);
      int color = -2141035934;
      if (time.hasReached(50L)) {
        color = -2141035934;
      }
      if (time.hasReached(100L)) {
        color = -2139917453;
      }
      if (time.hasReached(150L)) {
        color = -2138864765;
      }
      if (time.hasReached(200L)) {
        color = -2137746284;
      }
      if (time.hasReached(250L)) {
        color = -2136693596;
      }
      if (time.hasReached(300L)) {
        color = -2136101459;
      }
      if (time.hasReached(350L)) {
        color = -2135509322;
      }
      if (time.hasReached(400L)) {
        color = -2134917185;
      }
      if (time.hasReached(450L)) {
        color = -2134325048;
      }
      if (time.hasReached(500L)) {
        color = -2133667118;
      }
      if (!hovered) {
        time.reset();
      }
      RenderHelper.drawBorderedRect(xPosition, yPosition, xPosition + width, yPosition + height, 0.8F, -587202560, -1879048192);
      if (hovered) {
        RenderHelper.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, color);
      }
      mouseDragged(mc, mouseX, mouseY);
      int var3 = 14737632;
      if (!enabled) {
        var3 = 10526880;
      } else if (hovered) {
        var3 = 42752;
      }
      drawCenteredString(Minecraft.fontRendererObj, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, -1);
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
