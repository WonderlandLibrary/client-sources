package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.util.MathHelper;

public class GuiOptionSlider extends GuiButton
{
  private float sliderValue;
  public boolean dragging;
  private GameSettings.Options options;
  private final float field_146132_r;
  private final float field_146131_s;
  private static final String __OBFID = "CL_00000680";
  
  public GuiOptionSlider(int p_i45016_1_, int p_i45016_2_, int p_i45016_3_, GameSettings.Options p_i45016_4_)
  {
    this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0F, 1.0F);
  }
  
  public GuiOptionSlider(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, GameSettings.Options p_i45017_4_, float p_i45017_5_, float p_i45017_6_)
  {
    super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
    sliderValue = 1.0F;
    options = p_i45017_4_;
    field_146132_r = p_i45017_5_;
    field_146131_s = p_i45017_6_;
    Minecraft var7 = Minecraft.getMinecraft();
    sliderValue = p_i45017_4_.normalizeValue(gameSettings.getOptionFloatValue(p_i45017_4_));
    displayString = gameSettings.getKeyBinding(p_i45017_4_);
  }
  




  protected int getHoverState(boolean mouseOver)
  {
    return 0;
  }
  



  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
  {
    if (visible)
    {
      if (dragging)
      {
        sliderValue = ((mouseX - (xPosition + 4)) / (width - 8));
        sliderValue = MathHelper.clamp_float(sliderValue, 0.0F, 1.0F);
        float var4 = options.denormalizeValue(sliderValue);
        gameSettings.setOptionFloatValue(options, var4);
        sliderValue = options.normalizeValue(var4);
        displayString = gameSettings.getKeyBinding(options);
      }
      
      mc.getTextureManager().bindTexture(buttonTextures);
      net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect(xPosition + (int)(sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
      drawTexturedModalRect(xPosition + (int)(sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
    }
  }
  




  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    if (super.mousePressed(mc, mouseX, mouseY))
    {
      sliderValue = ((mouseX - (xPosition + 4)) / (width - 8));
      sliderValue = MathHelper.clamp_float(sliderValue, 0.0F, 1.0F);
      gameSettings.setOptionFloatValue(options, options.denormalizeValue(sliderValue));
      displayString = gameSettings.getKeyBinding(options);
      dragging = true;
      return true;
    }
    

    return false;
  }
  




  public void mouseReleased(int mouseX, int mouseY)
  {
    dragging = false;
  }
}
