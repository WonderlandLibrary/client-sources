package net.SliceClient.TTF;

import java.awt.Font;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class TTFRenderer
{
  private Minecraft mc = Minecraft.getMinecraft();
  private final UnicodeFont unicodeFont;
  private final int[] colorCodes = new int[32];
  private int fontType;
  private int size;
  private String fontName;
  private float kerning;
  
  public TTFRenderer(String fontName, int fontType, int size)
  {
    this(fontName, fontType, size, 0.0F);
  }
  
  public TTFRenderer(String fontName, int fontType, int size, float kerning)
  {
    this.fontName = fontName;
    this.fontType = fontType;
    this.size = size;
    
    unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
    this.kerning = kerning;
    
    unicodeFont.addAsciiGlyphs();
    unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
    try
    {
      unicodeFont.loadGlyphs();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    for (int i = 0; i < 32; i++)
    {
      int shadow = (i >> 3 & 0x1) * 85;
      int red = (i >> 2 & 0x1) * 170 + shadow;
      int green = (i >> 1 & 0x1) * 170 + shadow;
      int blue = (i >> 0 & 0x1) * 170 + shadow;
      if (i == 6) {
        red += 85;
      }
      if (i >= 16)
      {
        red /= 4;
        green /= 4;
        blue /= 4;
      }
      colorCodes[i] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
    }
  }
  
  public int drawString(String text, float x, float y, int color)
  {
    x *= 2.0F;
    y *= 2.0F;
    float originalX = x;
    
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
    int currentColor = color;
    char[] characters = text.toCharArray();
    
    int index = 0;
    char[] arrayOfChar1;
    int j = (arrayOfChar1 = characters).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar1[i];
      if (c == '\r') {
        x = originalX;
      }
      if (c == '\n') {
        y += getHeight(Character.toString(c)) * 2.0F;
      }
      if ((c != '§') && ((index == 0) || (index == characters.length - 1) || (characters[(index - 1)] != '§')))
      {
        unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
        x += getWidth(Character.toString(c)) * 2.0F;
      }
      else if (c == ' ')
      {
        x += unicodeFont.getSpaceWidth();
      }
      else if ((c == '§') && (index != characters.length - 1))
      {
        int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
        if (codeIndex < 0) {
          continue;
        }
        int col = colorCodes[codeIndex];
        currentColor = col;
      }
      index++;
    }
    GL11.glScaled(2.0D, 2.0D, 2.0D);
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
    return (int)x;
  }
  
  public int drawStringWithShadow(String text, float x, float y, int color)
  {
    drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0);
    return drawString(text, x, y, color);
  }
  
  public void drawCenteredString(String text, float x, float y, int color)
  {
    drawString(text, x - (int)getWidth(text) / 2, y, color);
  }
  
  public void drawCenteredStringWithShadow(String text, float x, float y, int color)
  {
    drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, color);
    drawCenteredString(text, x, y, color);
  }
  
  public float getWidth(String s)
  {
    float width = 0.0F;
    
    String str = StringUtils.stripControlCodes(s);
    char[] arrayOfChar;
    int j = (arrayOfChar = str.toCharArray()).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar[i];
      width += unicodeFont.getWidth(Character.toString(c)) + kerning;
    }
    return width / 2.0F;
  }
  
  public float getCharWidth(char c)
  {
    return unicodeFont.getWidth(String.valueOf(c));
  }
  
  public float getHeight(String s)
  {
    return unicodeFont.getHeight(s) / 2.0F;
  }
  
  public UnicodeFont getFont()
  {
    return unicodeFont;
  }
  
  public String trimStringToWidth(String par1Str, int par2)
  {
    StringBuilder var4 = new StringBuilder();
    float var5 = 0.0F;
    int var6 = 0;
    int var7 = 1;
    boolean var8 = false;
    boolean var9 = false;
    for (int var10 = var6; (var10 >= 0) && (var10 < par1Str.length()) && (var5 < par2); var10 += var7)
    {
      char var11 = par1Str.charAt(var10);
      float var12 = getCharWidth(var11);
      if (var8)
      {
        var8 = false;
        if ((var11 != 'l') && (var11 != 'L'))
        {
          if ((var11 == 'r') || (var11 == 'R')) {
            var9 = false;
          }
        }
        else {
          var9 = true;
        }
      }
      else if (var12 < 0.0F)
      {
        var8 = true;
      }
      else
      {
        var5 += var12;
        if (var9) {
          var5 += 1.0F;
        }
      }
      if (var5 > par2) {
        break;
      }
      var4.append(var11);
    }
    return var4.toString();
  }
}
