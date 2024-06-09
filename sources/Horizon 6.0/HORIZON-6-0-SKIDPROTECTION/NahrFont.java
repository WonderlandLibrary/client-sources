package HORIZON-6-0-SKIDPROTECTION;

import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.List;
import org.lwjgl.opengl.GL11;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Color;
import java.io.InputStream;
import java.io.File;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.util.regex.Pattern;
import java.awt.image.BufferedImage;

public class NahrFont
{
    private BufferedImage HorizonCode_Horizon_È;
    private DynamicTexture Â;
    private final int Ý;
    private float Ø­áŒŠá;
    private final float Âµá€;
    private final Pattern Ó;
    private final Pattern à;
    private ResourceLocation_1975012498 Ø;
    private final int áŒŠÆ;
    private Font áˆºÑ¢Õ;
    private Graphics2D ÂµÈ;
    private FontMetrics á;
    private final float[] ˆÏ­;
    private final float[] £á;
    
    public NahrFont(final Object font, final float size) {
        this(font, size, 0.0f);
    }
    
    public NahrFont(final Object font, final float size, final float spacing) {
        this.Ø­áŒŠá = 0.0f;
        this.Ó = Pattern.compile("(?i)\\u00A7[0-9A-FK-OG]");
        this.à = Pattern.compile("(?i)\\u00A7[K-O]");
        this.Âµá€ = size;
        this.áŒŠÆ = 32;
        this.Ý = 255;
        this.Ø­áŒŠá = spacing;
        this.ˆÏ­ = new float[this.Ý - this.áŒŠÆ];
        this.£á = new float[this.Ý - this.áŒŠÆ];
        this.Ý();
        this.HorizonCode_Horizon_È(font, size);
    }
    
    private void HorizonCode_Horizon_È(final Object font, final float size) {
        try {
            if (font instanceof Font) {
                this.áˆºÑ¢Õ = (Font)font;
            }
            else if (font instanceof File) {
                this.áˆºÑ¢Õ = Font.createFont(0, (File)font).deriveFont(size);
            }
            else if (font instanceof InputStream) {
                this.áˆºÑ¢Õ = Font.createFont(0, (InputStream)font).deriveFont(size);
            }
            else if (font instanceof String) {
                this.áˆºÑ¢Õ = new Font((String)font, 0, Math.round(size));
            }
            else {
                this.áˆºÑ¢Õ = new Font("Verdana", 0, Math.round(size));
            }
            this.ÂµÈ.setFont(this.áˆºÑ¢Õ);
        }
        catch (Exception var6) {
            var6.printStackTrace();
            this.áˆºÑ¢Õ = new Font("Verdana", 0, Math.round(size));
            this.ÂµÈ.setFont(this.áˆºÑ¢Õ);
        }
        this.ÂµÈ.setColor(new Color(255, 255, 255, 0));
        this.ÂµÈ.fillRect(0, 0, 256, 256);
        this.ÂµÈ.setColor(Color.white);
        this.á = this.ÂµÈ.getFontMetrics();
        float x = 5.0f;
        float y = 5.0f;
        for (int i = this.áŒŠÆ; i < this.Ý; ++i) {
            this.ÂµÈ.drawString(Character.toString((char)i), x, y + this.á.getAscent());
            this.ˆÏ­[i - this.áŒŠÆ] = x;
            this.£á[i - this.áŒŠÆ] = y - this.á.getMaxDescent();
            x += this.á.stringWidth(Character.toString((char)i)) + 2.0f;
            if (x >= 250 - this.á.getMaxAdvance()) {
                x = 5.0f;
                y += this.á.getMaxAscent() + this.á.getMaxDescent() + this.Âµá€ / 2.0f;
            }
        }
        final TextureManager ¥à = Minecraft.áŒŠà().¥à();
        final String string = "font" + font.toString() + size;
        final DynamicTexture dynamicTexture = new DynamicTexture(this.HorizonCode_Horizon_È);
        this.Â = dynamicTexture;
        this.Ø = ¥à.HorizonCode_Horizon_È(string, dynamicTexture);
    }
    
    private void HorizonCode_Horizon_È(final char character, final float x, final float y) throws ArrayIndexOutOfBoundsException {
        final Rectangle2D bounds = this.á.getStringBounds(Character.toString(character), this.ÂµÈ);
        this.HorizonCode_Horizon_È(x, y, this.ˆÏ­[character - this.áŒŠÆ], this.£á[character - this.áŒŠÆ], (float)bounds.getWidth(), (float)bounds.getHeight() + this.á.getMaxDescent() + 1.0f);
    }
    
    private void HorizonCode_Horizon_È(final String text, float x, float y, final int color) {
        x *= 2.0f;
        y *= 2.0f;
        GL11.glEnable(3553);
        Minecraft.áŒŠà().¥à().HorizonCode_Horizon_È(this.Ø);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        final float startX = x;
        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) == '§' && i + 1 < text.length()) {
                final char indexException = Character.toLowerCase(text.charAt(i + 1));
                if (indexException == 'n') {
                    y += this.á.getAscent() + 2;
                    x = startX;
                }
                final int colorCode = "0123456789abcdefklmnorg".indexOf(indexException);
                if (colorCode < 16) {
                    try {
                        final int exception = Minecraft.áŒŠà().µà.Ý[colorCode];
                        GL11.glColor4f((exception >> 16) / 255.0f, (exception >> 8 & 0xFF) / 255.0f, (exception & 0xFF) / 255.0f, alpha);
                    }
                    catch (Exception var14) {
                        var14.printStackTrace();
                    }
                }
                else if (indexException == 'f') {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                }
                else if (indexException == 'r') {
                    GL11.glColor4f(red, green, blue, alpha);
                }
                else if (indexException == 'g') {
                    GL11.glColor4f(0.3f, 0.7f, 1.0f, alpha);
                }
                ++i;
            }
            else {
                try {
                    final char indexException = text.charAt(i);
                    this.HorizonCode_Horizon_È(indexException, x, y);
                    x += this.Â(Character.toString(indexException)) * 2.0f;
                }
                catch (ArrayIndexOutOfBoundsException var15) {
                    text.charAt(i);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(String text, final float x, final float y, final HorizonCode_Horizon_È fontType, final int color, final int color2) {
        text = this.Ø­áŒŠá(text);
        GL11.glEnable(3042);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        final String text2 = this.Ý(text);
        switch (fontType.ordinal()) {
            case 1: {
                this.HorizonCode_Horizon_È(text2, x, y - 0.5f, color2);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(text2, x, y + 0.5f, color2);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(text2, x + 0.5f, y, color2);
                this.HorizonCode_Horizon_È(text2, x - 0.5f, y, color2);
                this.HorizonCode_Horizon_È(text2, x, y + 0.5f, color2);
                this.HorizonCode_Horizon_È(text2, x, y - 0.5f, color2);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(text2, x + 1.0f, y + 1.0f, color2);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(text2, x + 0.5f, y + 0.5f, color2);
                break;
            }
        }
        this.HorizonCode_Horizon_È(text, x, y, color);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    private void HorizonCode_Horizon_È(final float x, final float y, final float u, final float v, final float width, final float height) {
        final float scale = 0.0039063f;
        final WorldRenderer worldRenderer = Tessellator.HorizonCode_Horizon_È().Ý();
        final Tessellator tesselator = Tessellator.HorizonCode_Horizon_È();
        worldRenderer.Â();
        worldRenderer.HorizonCode_Horizon_È(x + 0.0f, y + height, 0.0, (u + 0.0f) * 0.0039063f, (v + height) * 0.0039063f);
        worldRenderer.HorizonCode_Horizon_È(x + width, y + height, 0.0, (u + width) * 0.0039063f, (v + height) * 0.0039063f);
        worldRenderer.HorizonCode_Horizon_È(x + width, y + 0.0f, 0.0, (u + width) * 0.0039063f, (v + 0.0f) * 0.0039063f);
        worldRenderer.HorizonCode_Horizon_È(x + 0.0f, y + 0.0f, 0.0, (u + 0.0f) * 0.0039063f, (v + 0.0f) * 0.0039063f);
        tesselator.Â();
    }
    
    private Rectangle2D Âµá€(final String text) {
        return this.á.getStringBounds(text, this.ÂµÈ);
    }
    
    public Font HorizonCode_Horizon_È() {
        return this.áˆºÑ¢Õ;
    }
    
    private String Ó(final String par0Str) {
        String var1 = "";
        int var2 = -1;
        final int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = par0Str.charAt(var2 + 1);
                if (this.HorizonCode_Horizon_È(var4)) {
                    var1 = "§" + var4;
                }
                else {
                    if (!this.Â(var4)) {
                        continue;
                    }
                    var1 = String.valueOf(var1) + "§" + var4;
                }
            }
        }
        return var1;
    }
    
    public Graphics2D Â() {
        return this.ÂµÈ;
    }
    
    public float HorizonCode_Horizon_È(final String text) {
        return (float)this.Âµá€(text).getHeight() / 2.0f;
    }
    
    public float Â(final String text) {
        return (float)(this.Âµá€(text).getWidth() + this.Ø­áŒŠá) / 2.0f;
    }
    
    private boolean HorizonCode_Horizon_È(final char par0) {
        return (par0 >= '0' && par0 <= '9') || (par0 >= 'a' && par0 <= 'f') || (par0 >= 'A' && par0 <= 'F');
    }
    
    private boolean Â(final char par0) {
        return (par0 >= 'k' && par0 <= 'o') || (par0 >= 'K' && par0 <= 'O') || par0 == 'r' || par0 == 'R';
    }
    
    public List HorizonCode_Horizon_È(final String s, final int width) {
        return Arrays.asList(this.HorizonCode_Horizon_È(s, (float)width).split("\n"));
    }
    
    private void Ý() {
        this.HorizonCode_Horizon_È = new BufferedImage(256, 256, 2);
        (this.ÂµÈ = (Graphics2D)this.HorizonCode_Horizon_È.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    
    private int Â(final String par1Str, final float par2) {
        final int var3 = par1Str.length();
        float var4 = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            final char var8 = par1Str.charAt(var5);
            Label_0195: {
                switch (var8) {
                    case '\n': {
                        --var5;
                        break Label_0195;
                    }
                    case ' ':
                    case '-':
                    case ':':
                    case '_': {
                        var6 = var5;
                        break;
                    }
                    case '§': {
                        if (var5 >= var3 - 1) {
                            break Label_0195;
                        }
                        ++var5;
                        final char text = par1Str.charAt(var5);
                        if (text == 'l' || text == 'L') {
                            var7 = true;
                            break Label_0195;
                        }
                        if (text == 'r' || text == 'R' || this.HorizonCode_Horizon_È(text)) {
                            var7 = false;
                        }
                        break Label_0195;
                    }
                }
                final String var9 = String.valueOf(var8);
                var4 += this.Â(var9);
                if (var7) {
                    ++var4;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
            }
            else if (var4 > par2) {
                break;
            }
            ++var5;
        }
        return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    public String Ý(final String s) {
        return this.Ó.matcher(s).replaceAll("");
    }
    
    public String Ø­áŒŠá(final String s) {
        return this.à.matcher(s).replaceAll("");
    }
    
    public String HorizonCode_Horizon_È(final String s, final float width) {
        final int wrapWidth = this.Â(s, width);
        if (s.length() <= wrapWidth) {
            return s;
        }
        final String split = s.substring(0, wrapWidth);
        final String split2 = String.valueOf(this.Ó(split)) + s.substring(wrapWidth + ((s.charAt(wrapWidth) == ' ' || s.charAt(wrapWidth) == '\n') ? 1 : 0));
        try {
            return String.valueOf(split) + "\n" + this.HorizonCode_Horizon_È(split2, width);
        }
        catch (Exception var7) {
            System.out.println("Cannot wrap string to width.");
            return "";
        }
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("EMBOSS_BOTTOM", 0), 
        Â("EMBOSS_TOP", 1), 
        Ý("NORMAL", 2), 
        Ø­áŒŠá("OUTLINE_THIN", 3), 
        Âµá€("SHADOW_THICK", 4), 
        Ó("SHADOW_THIN", 5);
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n) {
        }
    }
}
