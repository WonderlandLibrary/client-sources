package org.darkstorm.minecraft.gui.font;

import java.awt.Font;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class UnicodeFontRenderer
  extends FontRenderer
{
  private final UnicodeFont font;
  
  public UnicodeFontRenderer(Font awtFont)
  {
    super(Minecraft.gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
    
    font = new UnicodeFont(awtFont);
    font.addAsciiGlyphs();
    font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
    try
    {
      font.loadGlyphs();
    }
    catch (SlickException exception)
    {
      throw new RuntimeException(exception);
    }
    String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
  }
  
  public int drawString(String string, int x, int y, int color)
  {
    if (string == null) {
      return 0;
    }
    GL11.glPushMatrix();
    GL11.glScaled(0.5D, 0.5D, 0.5D);
    
    boolean blend = GL11.glIsEnabled(3042);
    boolean lighting = GL11.glIsEnabled(2896);
    boolean texture = GL11.glIsEnabled(3553);
    if (!blend) {
      GL11.glEnable(3042);
    }
    if (lighting) {
      GL11.glDisable(2896);
    }
    if (texture) {
      GL11.glDisable(3553);
    }
    x *= 2;
    y *= 2;
    
    font.drawString(x, y, string, new org.newdawn.slick.Color(color));
    if (texture) {
      GL11.glEnable(3553);
    }
    if (lighting) {
      GL11.glEnable(2896);
    }
    if (!blend) {
      GL11.glDisable(3042);
    }
    GL11.glPopMatrix();
    return x;
  }
  
  public int drawStringWithShadow(String string, float x, float y, int color)
  {
    return drawString(string, (int)x, (int)y, color);
  }
  
  public int getCharWidth(char c)
  {
    return getStringWidth(Character.toString(c));
  }
  
  public int getStringWidth(String string)
  {
    return font.getWidth(string) / 2;
  }
  
  public int getStringHeight(String string)
  {
    return font.getHeight(string) / 2;
  }
}
