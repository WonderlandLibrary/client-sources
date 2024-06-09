package parallaxadevs.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class FontUtils
{
  private final int boxSize = 512;
  private Font theFont;
  private Graphics2D theGraphics;
  private FontMetrics theMetrics;
  private int startChar;
  private int endChar;
  private float[] xPos;
  private float[] yPos;
  private BufferedImage bufferedImage;
  private BufferedImage bufferedImageBlurred;
  private float extraSpacing = 0.0F;
  private DynamicTexture dynamicTexture;
  private DynamicTexture dynamicTextureBlurred = null;
  private final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-ORU]");
  private final Pattern patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");
  
  public FontUtils(Font font, float spacing, boolean unicodeFont, boolean createBlur)
  {
    this(font, font.getSize(), spacing, unicodeFont, createBlur);
  }
  
  public FontUtils(Font font, boolean unicodeFont, boolean createBlur)
  {
    this(font, font.getSize(), 0.0F, unicodeFont, createBlur);
  }
  
  public FontUtils(Object font, float size, boolean createBlur)
  {
    this(font, size, false, createBlur);
  }
  
  public FontUtils(Object font, float size, boolean unicodeFont, boolean createBlur)
  {
    this(font, size, 0.0F, unicodeFont, createBlur);
  }
  
  public FontUtils(Object font, float size, float spacing, boolean unicodeFont, boolean createBlur)
  {
    try
    {
      this.startChar = 32;
      this.endChar = 255;
      this.extraSpacing = spacing;
      this.xPos = new float[this.endChar - this.startChar];
      this.yPos = new float[this.endChar - this.startChar];
      setupGraphics2D(unicodeFont);
      createFont(font, size, createBlur);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void setupGraphics2D(boolean unicodeFont)
  {
    this.bufferedImage = new BufferedImage(512, 512, 2);
    this.theGraphics = ((Graphics2D)this.bufferedImage.getGraphics());
    this.theGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    if (unicodeFont) {
      this.theGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }
  }
  
  private void createFont(Object font, float size, boolean createBlur)
  {
    try
    {
      if ((font instanceof Font)) {
        this.theFont = ((Font)font);
      } else if ((font instanceof File)) {
        this.theFont = Font.createFont(0, (File)font).deriveFont(size);
      } else if ((font instanceof InputStream)) {
        this.theFont = Font.createFont(0, (InputStream)font).deriveFont(size);
      } else if ((font instanceof String)) {
        this.theFont = new Font((String)font, 0, Math.round(size));
      } else {
        this.theFont = new Font("Verdana", 0, Math.round(size));
      }
      this.theGraphics.setFont(this.theFont);
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
      this.theFont = new Font("Verdana", 0, Math.round(size));
      this.theGraphics.setFont(this.theFont);
    }
    this.theGraphics.setColor(new Color(255, 255, 255, 0));
    this.theGraphics.fillRect(0, 0, 512, 512);
    this.theGraphics.setColor(Color.white);
    this.theMetrics = this.theGraphics.getFontMetrics();
    
    float x = 2.0F;
    float y = 2.0F;
    for (int i = this.startChar; i < this.endChar; i++)
    {
      this.theGraphics.drawString(Character.toString((char)i), x, y + this.theMetrics.getAscent());
      this.xPos[(i - this.startChar)] = x;
      this.yPos[(i - this.startChar)] = (y - this.theMetrics.getMaxDescent());
      x += this.theMetrics.stringWidth(Character.toString((char)i)) + 5;
      if (x >= '?' - 5 - this.theMetrics.getMaxAdvance())
      {
        x = 2.0F;
        y += this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent() + size / 2.0F + 2.0F;
      }
    }
    this.dynamicTexture = new DynamicTexture(this.bufferedImage);
    if (createBlur)
    {
      Kernel kernel = new Kernel(3, 3, new float[] { 0.1F, 0.1F, 0.1F, 
        0.1F, 0.1F, 0.1F, 
        0.1F, 0.1F, 0.1F });
      ConvolveOp convolveOp = new ConvolveOp(kernel, 1, null);
      this.bufferedImageBlurred = convolveOp.filter(this.bufferedImage, null);
      this.dynamicTextureBlurred = new DynamicTexture(this.bufferedImageBlurred);
    }
  }
  
  public void drawStringWithShadow(String text, double d, double e, int color)
  {
    GlStateManager.enableBlend();
    drawString(text, d, e, FontType.SHADOW_THIN, color, 0, true);
    GlStateManager.disableBlend();
  }
  
  public void drawCenteredStringWithShadow(String text, double x, double y, int color)
  {
    drawStringWithShadow(text, x - getStringWidth(text) / 2.0F, y - getStringHeight(text) / 2.0F, color);
  }
  
  public void drawString(String text, double x, double y, FontType fontType, int color, int color2, boolean shadow)
  {
    text = stripUnsupported(text);
    
    GlStateManager.enableBlend();
    GlStateManager.scale(0.5F, 0.5F, 0.5F);
    
    String text2 = stripControlCodes(text);
    boolean skip = false;
    switch (fontType)
    {
    case PLAIN: 
      drawer(text2, x + 0.5D, y, color2, false);
      drawer(text2, x - 0.5D, y, color2, false);
      drawer(text2, x, y + 0.5D, color2, false);
      drawer(text2, x, y - 0.5D, color2, false);
      break;
    case OVERLAY_BLUR: 
      drawer(text2, x + 0.4000000059604645D, y + 0.4000000059604645D, color2, false);
      break;
    case OUTLINE_THIN: 
      drawer(text2, x + 1.0D, y + 1.0D, color2, false);
      break;
    case EMBOSS_TOP: 
      if (this.dynamicTextureBlurred != null) {
        drawer(text2, x + 0.5D, y + 0.5D, color2, true);
      }
      break;
    case SHADOW_THIN: 
      if (this.dynamicTextureBlurred != null)
      {
        skip = true;
        drawer(text, x, y, color, false);
        drawer(text2, x, y, color2, true);
      }
      break;
    case SHADOW_THICK: 
      drawer(text2, x, y + 0.5D, color2, false);
      break;
    case SHADOW_BLURRED: 
      drawer(text2, x, y - 0.5D, color2, false);
      break;
    case EMBOSS_BOTTOM: 
      break;
    }
    if (shadow) {
      color2 = 0 >> 2 | color & 0xFF000000;
    }
    if (!skip) {
      drawer(text, x, y, color, false);
    }
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    GL11.glHint(3155, 4352);
  }
  
  private void drawer(String text, double x, double y, int color, boolean useBlur)
  {
    x *= 2.0D;
    y *= 2.0D;
    GlStateManager.func_179098_w();
    if (useBlur) {
      GlStateManager.func_179144_i(this.dynamicTextureBlurred.getGlTextureId());
    } else {
      GlStateManager.func_179144_i(this.dynamicTexture.getGlTextureId());
    }
    float alpha = (color >> 24 & 0xFF) / 255.0F;
    float red = (color >> 16 & 0xFF) / 255.0F;
    float green = (color >> 8 & 0xFF) / 255.0F;
    float blue = (color & 0xFF) / 255.0F;
    GlStateManager.color(red, green, blue, 255.0F);
    double startX = x;
    for (int i = 0; i < text.length(); i++) {
      if ((text.charAt(i) == '§') && (i + 1 < text.length()))
      {
        char oneMore = Character.toLowerCase(text.charAt(i + 1));
        if (oneMore == 'n')
        {
          y += this.theMetrics.getAscent() + 2;
          x = startX;
        }
        int colorCode = "0123456789abcdefklmnoru".indexOf(oneMore);
        if ((colorCode < 16) && (colorCode > -1))
        {
          int newColor = net.minecraft.client.Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
          GlStateManager.color((newColor >> 16) / 255.0F, 
            (newColor >> 8 & 0xFF) / 255.0F, 
            (newColor & 0xFF) / 255.0F, 
            255.0F);
        }
        else if (oneMore == 'f')
        {
          GlStateManager.color(1.0F, 1.0F, 1.0F, 255.0F);
        }
        else if (oneMore == 'r')
        {
          GlStateManager.color(red, green, blue, 255.0F);
        }
        else if (oneMore == 'u')
        {
          int newColor = -10859859;
          GlStateManager.color((newColor >> 16 & 0xFF) / 255.0F, 
            (newColor >> 8 & 0xFF) / 255.0F, 
            (newColor & 0xFF) / 255.0F, 
            255.0F);
        }
        i++;
      }
      else
      {
        try
        {
          char c = text.charAt(i);
          drawChar(c, x, y);
          x += getStringWidth(Character.toString(c)) * 2.0F;
        }
        catch (ArrayIndexOutOfBoundsException indexException)
        {
          indexException.printStackTrace();
        }
      }
    }
  }
  
  public float getStringWidth(String str)
  {
    return (float)(getBounds(StringUtils.stripControlCodes(str)).getWidth() + this.extraSpacing) / 2.0F;
  }
  
  public float getStringHeight(String text)
  {
    return (float)getBounds(text).getHeight() / 2.0F;
  }
  
  private Rectangle2D getBounds(String text)
  {
    return this.theMetrics.getStringBounds(text, this.theGraphics);
  }
  
  public final float getHeight()
  {
    return this.theMetrics.getHeight() + this.theMetrics.getDescent() + 3;
  }
  
  private void drawChar(char character, double x, double y)
  {
    if ((character >= this.startChar) && (character <= this.endChar))
    {
      Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
      drawTexturedModalRect(x, y - this.theMetrics.getAscent() / 2, this.xPos[(character - this.startChar)], this.yPos[(character - this.startChar)], bounds.getWidth(), getHeight());
    }
  }
  
  public List listFormattedStringToWidth(String s, int width)
  {
    return Arrays.asList(wrapFormattedStringToWidth(s, width).split("\n"));
  }
  
  String wrapFormattedStringToWidth(String s, float width)
  {
    int wrapWidth = sizeStringToWidth(s, width);
    if (s.length() <= wrapWidth) {
      return s;
    }
    String split = s.substring(0, wrapWidth);
    String split2 = getFormatFromString(split) + s.substring(wrapWidth + ((s.charAt(wrapWidth) == ' ') || (s.charAt(wrapWidth) == '\n') ? 1 : 0));
    return split + "\n" + wrapFormattedStringToWidth(split2, width);
  }
  
  private int sizeStringToWidth(String par1Str, float par2)
  {
    int var3 = par1Str.length();
    float var4 = 0.0F;
    int var5 = 0;
    int var6 = -1;
    for (boolean var7 = false; var5 < var3; var5++)
    {
      char var8 = par1Str.charAt(var5);
      switch (var8)
      {
      case '\n': 
        var5--;
        break;
      case '§': 
        if (var5 < var3 - 1)
        {
          var5++;
          char var9 = par1Str.charAt(var5);
          if ((var9 != 'l') && (var9 != 'L'))
          {
            if ((var9 == 'r') || (var9 == 'R') || (isFormatColor(var9))) {
              var7 = false;
            }
          }
          else {
            var7 = true;
          }
        }
        break;
      case ' ': 
        var6 = var5;
      case '-': 
        var6 = var5;
      case '_': 
        var6 = var5;
      case ':': 
        var6 = var5;
      default: 
        String text = String.valueOf(var8);
        var4 += getStringWidth(text);
        if (var7) {
          var4 += 1.0F;
        }
        break;
      }
      if (var8 == '\n')
      {
        var5++;
        var6 = var5;
      }
      else
      {
        if (var4 > par2) {
          break;
        }
      }
    }
    return (var5 != var3) && (var6 != -1) && (var6 < var5) ? var6 : var5;
  }
  
  private String getFormatFromString(String par0Str)
  {
    String var1 = "";
    int var2 = -1;
    int var3 = par0Str.length();
    while ((var2 = par0Str.indexOf('§', var2 + 1)) != -1) {
      if (var2 < var3 - 1)
      {
        char var4 = par0Str.charAt(var2 + 1);
        if (isFormatColor(var4)) {
          var1 = "§" + var4;
        } else if (isFormatSpecial(var4)) {
          var1 = var1 + "§" + var4;
        }
      }
    }
    return var1;
  }
  
  private boolean isFormatColor(char par0)
  {
    return ((par0 >= '0') && (par0 <= '9')) || ((par0 >= 'a') && (par0 <= 'f')) || ((par0 >= 'A') && (par0 <= 'F'));
  }
  
  private boolean isFormatSpecial(char par0)
  {
    return ((par0 >= 'k') && (par0 <= 'o')) || ((par0 >= 'K') && (par0 <= 'O')) || (par0 == 'r') || (par0 == 'R');
  }
  
  private void drawTexturedModalRect(double x, double y, double u, double v, double width, double height)
  {
    double scale = 1.0D / 512.0D;
    GL11.glBegin(7);
    GL11.glTexCoord2d(u * scale, v * scale);
    GL11.glVertex2d(x, y);
    GL11.glTexCoord2d(u * scale, (v + height) * scale);
    GL11.glVertex2d(x, y + height);
    GL11.glTexCoord2d((u + width) * scale, (v + height) * scale);
    GL11.glVertex2d(x + width, y + height);
    GL11.glTexCoord2d((u + width) * scale, v * scale);
    GL11.glVertex2d(x + width, y);
    GL11.glEnd();
  }
  
  public String stripControlCodes(String s)
  {
    return this.patternControlCode.matcher(s).replaceAll("");
  }
  
  public String stripUnsupported(String s)
  {
    return this.patternUnsupported.matcher(s).replaceAll("");
  }
  
  public Graphics2D getGraphics()
  {
    return this.theGraphics;
  }
  
  public Font getFont()
  {
    return this.theFont;
  }
  
  public static enum FontType
  {
    PLAIN,  SHADOW_BLURRED,  SHADOW_THICK,  SHADOW_THIN,  OUTLINE_THIN,  EMBOSS_TOP,  EMBOSS_BOTTOM,  OVERLAY_BLUR;
  }
}