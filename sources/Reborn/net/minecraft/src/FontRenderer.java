package net.minecraft.src;

import javax.imageio.*;
import java.awt.image.*;
import org.lwjgl.opengl.*;
import java.text.*;
import java.io.*;
import java.util.*;

public class FontRenderer
{
    private float[] charWidth;
    public int FONT_HEIGHT;
    public Random fontRandom;
    private byte[] glyphWidth;
    private int[] colorCode;
    private final String fontTextureName;
    private final RenderEngine renderEngine;
    private float posX;
    private float posY;
    private boolean unicodeFlag;
    private boolean bidiFlag;
    private float red;
    private float blue;
    private float green;
    private float alpha;
    private int textColor;
    private boolean randomStyle;
    private boolean boldStyle;
    private boolean italicStyle;
    private boolean underlineStyle;
    private boolean strikethroughStyle;
    public GameSettings gameSettings;
    public boolean enabled;
    
    FontRenderer() {
        this.charWidth = new float[256];
        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
        this.enabled = true;
        this.renderEngine = null;
        this.fontTextureName = null;
    }
    
    public FontRenderer(final GameSettings par1GameSettings, final String par2Str, final RenderEngine par3RenderEngine, final boolean par4) {
        this.charWidth = new float[256];
        this.FONT_HEIGHT = 9;
        this.fontRandom = new Random();
        this.glyphWidth = new byte[65536];
        this.colorCode = new int[32];
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
        this.enabled = true;
        this.gameSettings = par1GameSettings;
        this.fontTextureName = par2Str;
        this.renderEngine = par3RenderEngine;
        this.unicodeFlag = par4;
        this.readFontData();
        par3RenderEngine.bindTexture(par2Str);
        for (int var5 = 0; var5 < 32; ++var5) {
            final int var6 = (var5 >> 3 & 0x1) * 85;
            int var7 = (var5 >> 2 & 0x1) * 170 + var6;
            int var8 = (var5 >> 1 & 0x1) * 170 + var6;
            int var9 = (var5 >> 0 & 0x1) * 170 + var6;
            if (var5 == 6) {
                var7 += 85;
            }
            if (par1GameSettings.anaglyph) {
                final int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                final int var11 = (var7 * 30 + var8 * 70) / 100;
                final int var12 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            if (var5 >= 16) {
                var7 /= 4;
                var8 /= 4;
                var9 /= 4;
            }
            this.colorCode[var5] = ((var7 & 0xFF) << 16 | (var8 & 0xFF) << 8 | (var9 & 0xFF));
        }
    }
    
    public void readFontData() {
        this.readGlyphSizes();
        this.readFontTexture(this.fontTextureName);
    }
    
    private void readFontTexture(String par1Str) {
        BufferedImage var2;
        try {
            par1Str = fixHdFontName(par1Str);
            var2 = ImageIO.read(getFontTexturePack().getResourceAsStream(par1Str));
        }
        catch (IOException var3) {
            throw new RuntimeException(var3);
        }
        final int var4 = var2.getWidth();
        final int var5 = var2.getHeight();
        final int var6 = var4 / 16;
        final int var7 = var5 / 16;
        final float var8 = var4 / 128.0f;
        final int[] var9 = new int[var4 * var5];
        var2.getRGB(0, 0, var4, var5, var9, 0, var4);
        for (int var10 = 0; var10 < 256; ++var10) {
            final int var11 = var10 % 16;
            final int var12 = var10 / 16;
            final boolean var13 = false;
            int var14;
            for (var14 = var6 - 1; var14 >= 0; --var14) {
                final int var15 = var11 * var6 + var14;
                boolean var16 = true;
                for (int var17 = 0; var17 < var7 && var16; ++var17) {
                    final int var18 = (var12 * var7 + var17) * var4;
                    final int var19 = var9[var15 + var18];
                    final int var20 = var19 >> 24 & 0xFF;
                    if (var20 > 16) {
                        var16 = false;
                    }
                }
                if (!var16) {
                    break;
                }
            }
            if (var10 == 32) {
                if (var6 <= 8) {
                    var14 = (int)(2.0f * var8);
                }
                else {
                    var14 = (int)(1.5f * var8);
                }
            }
            this.charWidth[var10] = (var14 + 1) / var8 + 1.0f;
        }
        this.readCustomCharWidths();
    }
    
    private void readGlyphSizes() {
        try {
            final InputStream var1 = getFontTexturePack().getResourceAsStream("/font/glyph_sizes.bin");
            var1.read(this.glyphWidth);
        }
        catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }
    
    private float renderCharAtPos(final int par1, final char par2, final boolean par3) {
        return (par2 == ' ') ? this.charWidth[par2] : ((par1 > 0 && !this.unicodeFlag) ? this.renderDefaultChar(par1 + 32, par3) : this.renderUnicodeChar(par2, par3));
    }
    
    private float renderDefaultChar(final int par1, final boolean par2) {
        final float var3 = par1 % 16 * 8;
        final float var4 = par1 / 16 * 8;
        final float var5 = par2 ? 1.0f : 0.0f;
        this.renderEngine.bindTexture(this.fontTextureName);
        final float var6 = this.charWidth[par1] - 0.01f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var3 / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.posX + var5, this.posY, 0.0f);
        GL11.glTexCoord2f(var3 / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX - var5, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((var3 + var6) / 128.0f, var4 / 128.0f);
        GL11.glVertex3f(this.posX + var6 + var5, this.posY, 0.0f);
        GL11.glTexCoord2f((var3 + var6) / 128.0f, (var4 + 7.99f) / 128.0f);
        GL11.glVertex3f(this.posX + var6 - var5, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return this.charWidth[par1];
    }
    
    private void loadGlyphTexture(final int par1) {
        final String var2 = String.format("/font/glyph_%02X.png", par1);
        this.renderEngine.bindTexture(var2);
    }
    
    private float renderUnicodeChar(final char par1, final boolean par2) {
        if (this.glyphWidth[par1] == 0) {
            return 0.0f;
        }
        final int var3 = par1 / '\u0100';
        this.loadGlyphTexture(var3);
        final int var4 = this.glyphWidth[par1] >>> 4;
        final int var5 = this.glyphWidth[par1] & 0xF;
        final float var6 = var4;
        final float var7 = var5 + 1;
        final float var8 = par1 % '\u0010' * '\u0010' + var6;
        final float var9 = (par1 & '\u00ff') / '\u0010' * '\u0010';
        final float var10 = var7 - var6 - 0.02f;
        final float var11 = par2 ? 1.0f : 0.0f;
        GL11.glBegin(5);
        GL11.glTexCoord2f(var8 / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.posX + var11, this.posY, 0.0f);
        GL11.glTexCoord2f(var8 / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX - var11, this.posY + 7.99f, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, var9 / 256.0f);
        GL11.glVertex3f(this.posX + var10 / 2.0f + var11, this.posY, 0.0f);
        GL11.glTexCoord2f((var8 + var10) / 256.0f, (var9 + 15.98f) / 256.0f);
        GL11.glVertex3f(this.posX + var10 / 2.0f - var11, this.posY + 7.99f, 0.0f);
        GL11.glEnd();
        return (var7 - var6) / 2.0f + 1.0f;
    }
    
    public int drawStringWithShadow(final String par1Str, final int par2, final int par3, final int par4) {
        return this.drawString(par1Str, par2, par3, par4, true);
    }
    
    public int drawString(final String par1Str, final int par2, final int par3, final int par4) {
        return this.enabled ? this.drawString(par1Str, par2, par3, par4, false) : 0;
    }
    
    public int drawString(String par1Str, final int par2, final int par3, final int par4, final boolean par5) {
        this.resetStyles();
        if (this.bidiFlag) {
            par1Str = this.bidiReorder(par1Str);
        }
        int var6;
        if (par5) {
            var6 = this.renderString(par1Str, par2 + 1, par3 + 1, par4, true);
            var6 = Math.max(var6, this.renderString(par1Str, par2, par3, par4, false));
        }
        else {
            var6 = this.renderString(par1Str, par2, par3, par4, false);
        }
        return var6;
    }
    
    private String bidiReorder(final String par1Str) {
        if (par1Str != null && Bidi.requiresBidi(par1Str.toCharArray(), 0, par1Str.length())) {
            final Bidi var2 = new Bidi(par1Str, -2);
            final byte[] var3 = new byte[var2.getRunCount()];
            final String[] var4 = new String[var3.length];
            for (int var5 = 0; var5 < var3.length; ++var5) {
                final int var6 = var2.getRunStart(var5);
                final int var7 = var2.getRunLimit(var5);
                final int var8 = var2.getRunLevel(var5);
                final String var9 = par1Str.substring(var6, var7);
                var3[var5] = (byte)var8;
                var4[var5] = var9;
            }
            final String[] var10 = var4.clone();
            Bidi.reorderVisually(var3, 0, var4, 0, var3.length);
            final StringBuilder var11 = new StringBuilder();
            for (int var7 = 0; var7 < var4.length; ++var7) {
                byte var12 = var3[var7];
                for (int var13 = 0; var13 < var10.length; ++var13) {
                    if (var10[var13].equals(var4[var7])) {
                        var12 = var3[var13];
                        break;
                    }
                }
                if ((var12 & 0x1) == 0x0) {
                    var11.append(var4[var7]);
                }
                else {
                    for (int var13 = var4[var7].length() - 1; var13 >= 0; --var13) {
                        char var14 = var4[var7].charAt(var13);
                        if (var14 == '(') {
                            var14 = ')';
                        }
                        else if (var14 == ')') {
                            var14 = '(';
                        }
                        var11.append(var14);
                    }
                }
            }
            return var11.toString();
        }
        return par1Str;
    }
    
    private void resetStyles() {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    
    private void renderStringAtPos(final String par1Str, final boolean par2) {
        for (int var3 = 0; var3 < par1Str.length(); ++var3) {
            final char var4 = par1Str.charAt(var3);
            if (var4 == '§' && var3 + 1 < par1Str.length()) {
                int var5 = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(var3 + 1));
                if (var5 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    if (var5 < 0 || var5 > 15) {
                        var5 = 15;
                    }
                    if (par2) {
                        var5 += 16;
                    }
                    final int var6 = this.colorCode[var5];
                    this.textColor = var6;
                    GL11.glColor4f((var6 >> 16) / 255.0f, (var6 >> 8 & 0xFF) / 255.0f, (var6 & 0xFF) / 255.0f, this.alpha);
                }
                else if (var5 == 16) {
                    this.randomStyle = true;
                }
                else if (var5 == 17) {
                    this.boldStyle = true;
                }
                else if (var5 == 18) {
                    this.strikethroughStyle = true;
                }
                else if (var5 == 19) {
                    this.underlineStyle = true;
                }
                else if (var5 == 20) {
                    this.italicStyle = true;
                }
                else if (var5 == 21) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
                }
                ++var3;
            }
            else {
                int var5 = ChatAllowedCharacters.allowedCharacters.indexOf(var4);
                if (this.randomStyle && var5 > 0) {
                    int var6;
                    do {
                        var6 = this.fontRandom.nextInt(ChatAllowedCharacters.allowedCharacters.length());
                    } while ((int)this.charWidth[var5 + 32] != (int)this.charWidth[var6 + 32]);
                    var5 = var6;
                }
                final float var7 = this.unicodeFlag ? 0.5f : 1.0f;
                final boolean var8 = (var5 <= 0 || this.unicodeFlag) && par2;
                if (var8) {
                    this.posX -= var7;
                    this.posY -= var7;
                }
                float var9 = this.renderCharAtPos(var5, var4, this.italicStyle);
                if (var8) {
                    this.posX += var7;
                    this.posY += var7;
                }
                if (this.boldStyle) {
                    this.posX += var7;
                    if (var8) {
                        this.posX -= var7;
                        this.posY -= var7;
                    }
                    this.renderCharAtPos(var5, var4, this.italicStyle);
                    this.posX -= var7;
                    if (var8) {
                        this.posX += var7;
                        this.posY += var7;
                    }
                    ++var9;
                }
                if (this.strikethroughStyle) {
                    final Tessellator var10 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var10.startDrawingQuads();
                    var10.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT / 2, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    var10.addVertex(this.posX, this.posY + this.FONT_HEIGHT / 2 - 1.0f, 0.0);
                    var10.draw();
                    GL11.glEnable(3553);
                }
                if (this.underlineStyle) {
                    final Tessellator var10 = Tessellator.instance;
                    GL11.glDisable(3553);
                    var10.startDrawingQuads();
                    final int var11 = this.underlineStyle ? -1 : 0;
                    var10.addVertex(this.posX + var11, this.posY + this.FONT_HEIGHT, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT, 0.0);
                    var10.addVertex(this.posX + var9, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    var10.addVertex(this.posX + var11, this.posY + this.FONT_HEIGHT - 1.0f, 0.0);
                    var10.draw();
                    GL11.glEnable(3553);
                }
                this.posX += var9;
            }
        }
    }
    
    private int renderStringAligned(String par1Str, int par2, final int par3, final int par4, final int par5, final boolean par6) {
        if (this.bidiFlag) {
            par1Str = this.bidiReorder(par1Str);
            final int var7 = this.getStringWidth(par1Str);
            par2 = par2 + par4 - var7;
        }
        return this.renderString(par1Str, par2, par3, par5, par6);
    }
    
    private int renderString(final String par1Str, final int par2, final int par3, int par4, final boolean par5) {
        if (par1Str == null) {
            return 0;
        }
        if ((par4 & 0xFC000000) == 0x0) {
            par4 |= 0xFF000000;
        }
        if (par5) {
            par4 = ((par4 & 0xFCFCFC) >> 2 | (par4 & 0xFF000000));
        }
        this.red = (par4 >> 16 & 0xFF) / 255.0f;
        this.blue = (par4 >> 8 & 0xFF) / 255.0f;
        this.green = (par4 & 0xFF) / 255.0f;
        this.alpha = (par4 >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(this.red, this.blue, this.green, this.alpha);
        this.posX = par2;
        this.posY = par3;
        this.renderStringAtPos(par1Str, par5);
        return (int)this.posX;
    }
    
    public int getStringWidth(final String par1Str) {
        if (par1Str == null) {
            return 0;
        }
        float var2 = 0.0f;
        boolean var3 = false;
        for (int var4 = 0; var4 < par1Str.length(); ++var4) {
            char var5 = par1Str.charAt(var4);
            float var6 = this.getCharWidthFloat(var5);
            if (var6 < 0.0f && var4 < par1Str.length() - 1) {
                ++var4;
                var5 = par1Str.charAt(var4);
                if (var5 != 'l' && var5 != 'L') {
                    if (var5 == 'r' || var5 == 'R') {
                        var3 = false;
                    }
                }
                else {
                    var3 = true;
                }
                var6 = 0.0f;
            }
            var2 += var6;
            if (var3) {
                ++var2;
            }
        }
        return (int)var2;
    }
    
    public int getCharWidth(final char par1) {
        return Math.round(this.getCharWidthFloat(par1));
    }
    
    private float getCharWidthFloat(final char var1) {
        if (var1 == '§') {
            return -1.0f;
        }
        if (var1 == ' ') {
            return this.charWidth[32];
        }
        final int var2 = ChatAllowedCharacters.allowedCharacters.indexOf(var1);
        if (var2 >= 0 && !this.unicodeFlag) {
            return this.charWidth[var2 + 32];
        }
        if (this.glyphWidth[var1] != 0) {
            int var3 = this.glyphWidth[var1] >>> 4;
            int var4 = this.glyphWidth[var1] & 0xF;
            if (var4 > 7) {
                var4 = 15;
                var3 = 0;
            }
            return (++var4 - var3) / 2 + 1;
        }
        return 0.0f;
    }
    
    public String trimStringToWidth(final String par1Str, final int par2) {
        return this.trimStringToWidth(par1Str, par2, false);
    }
    
    public String trimStringToWidth(final String par1Str, final int par2, final boolean par3) {
        final StringBuilder var4 = new StringBuilder();
        float var5 = 0.0f;
        final int var6 = par3 ? (par1Str.length() - 1) : 0;
        final int var7 = par3 ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
            final char var11 = par1Str.charAt(var10);
            final float var12 = this.getCharWidthFloat(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                }
                else {
                    var9 = true;
                }
            }
            else if (var12 < 0.0f) {
                var8 = true;
            }
            else {
                var5 += var12;
                if (var9) {
                    ++var5;
                }
            }
            if (var5 > par2) {
                break;
            }
            if (par3) {
                var4.insert(0, var11);
            }
            else {
                var4.append(var11);
            }
        }
        return var4.toString();
    }
    
    private String trimStringNewline(String par1Str) {
        while (par1Str != null && par1Str.endsWith("\n")) {
            par1Str = par1Str.substring(0, par1Str.length() - 1);
        }
        return par1Str;
    }
    
    public void drawSplitString(String par1Str, final int par2, final int par3, final int par4, final int par5) {
        this.resetStyles();
        this.textColor = par5;
        par1Str = this.trimStringNewline(par1Str);
        this.renderSplitString(par1Str, par2, par3, par4, false);
    }
    
    private void renderSplitString(final String par1Str, final int par2, int par3, final int par4, final boolean par5) {
        final List var6 = this.listFormattedStringToWidth(par1Str, par4);
        for (final String var8 : var6) {
            this.renderStringAligned(var8, par2, par3, par4, this.textColor, par5);
            par3 += this.FONT_HEIGHT;
        }
    }
    
    public int splitStringWidth(final String par1Str, final int par2) {
        return this.FONT_HEIGHT * this.listFormattedStringToWidth(par1Str, par2).size();
    }
    
    public void setUnicodeFlag(final boolean par1) {
        this.unicodeFlag = par1;
    }
    
    public boolean getUnicodeFlag() {
        return this.unicodeFlag;
    }
    
    public void setBidiFlag(final boolean par1) {
        this.bidiFlag = par1;
    }
    
    public List listFormattedStringToWidth(final String par1Str, final int par2) {
        return Arrays.asList(this.wrapFormattedStringToWidth(par1Str, par2).split("\n"));
    }
    
    String wrapFormattedStringToWidth(final String par1Str, final int par2) {
        final int var3 = this.sizeStringToWidth(par1Str, par2);
        if (par1Str.length() <= var3) {
            return par1Str;
        }
        final String var4 = par1Str.substring(0, var3);
        final char var5 = par1Str.charAt(var3);
        final boolean var6 = var5 == ' ' || var5 == '\n';
        final String var7 = String.valueOf(getFormatFromString(var4)) + par1Str.substring(var3 + (var6 ? 1 : 0));
        return String.valueOf(var4) + "\n" + this.wrapFormattedStringToWidth(var7, par2);
    }
    
    private int sizeStringToWidth(final String par1Str, final int par2) {
        final int var3 = par1Str.length();
        float var4 = 0.0f;
        int var5 = 0;
        int var6 = -1;
        boolean var7 = false;
        while (var5 < var3) {
            final char var8 = par1Str.charAt(var5);
            Label_0163: {
                switch (var8) {
                    case '\n': {
                        --var5;
                        break Label_0163;
                    }
                    case '§': {
                        if (var5 >= var3 - 1) {
                            break Label_0163;
                        }
                        ++var5;
                        final char var9 = par1Str.charAt(var5);
                        if (var9 == 'l' || var9 == 'L') {
                            var7 = true;
                            break Label_0163;
                        }
                        if (var9 == 'r' || var9 == 'R' || isFormatColor(var9)) {
                            var7 = false;
                        }
                        break Label_0163;
                    }
                    case ' ': {
                        var6 = var5;
                        break;
                    }
                }
                var4 += this.getCharWidthFloat(var8);
                if (var7) {
                    ++var4;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                break;
            }
            if (var4 > par2) {
                break;
            }
            ++var5;
        }
        return (var5 != var3 && var6 != -1 && var6 < var5) ? var6 : var5;
    }
    
    private static boolean isFormatColor(final char par0) {
        return (par0 >= '0' && par0 <= '9') || (par0 >= 'a' && par0 <= 'f') || (par0 >= 'A' && par0 <= 'F');
    }
    
    private static boolean isFormatSpecial(final char par0) {
        return (par0 >= 'k' && par0 <= 'o') || (par0 >= 'K' && par0 <= 'O') || par0 == 'r' || par0 == 'R';
    }
    
    private static String getFormatFromString(final String par0Str) {
        String var1 = "";
        int var2 = -1;
        final int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                final char var4 = par0Str.charAt(var2 + 1);
                if (isFormatColor(var4)) {
                    var1 = "§" + var4;
                }
                else {
                    if (!isFormatSpecial(var4)) {
                        continue;
                    }
                    var1 = String.valueOf(var1) + "§" + var4;
                }
            }
        }
        return var1;
    }
    
    public boolean getBidiFlag() {
        return this.bidiFlag;
    }
    
    private void readCustomCharWidths() {
        final String var1 = fixHdFontName(this.fontTextureName);
        final String var2 = ".png";
        if (var1.endsWith(var2)) {
            final String var3 = String.valueOf(var1.substring(0, var1.length() - var2.length())) + ".properties";
            try {
                final InputStream var4 = getFontTexturePack().getResourceAsStream(var3);
                if (var4 == null) {
                    return;
                }
                Config.log("Loading " + var3);
                final Properties var5 = new Properties();
                var5.load(var4);
                final Set var6 = var5.keySet();
                for (final String var8 : var6) {
                    final String var9 = "width.";
                    if (var8.startsWith(var9)) {
                        final String var10 = var8.substring(var9.length());
                        final int var11 = Config.parseInt(var10, -1);
                        if (var11 < 0 || var11 >= this.charWidth.length) {
                            continue;
                        }
                        final String var12 = var5.getProperty(var8);
                        final float var13 = Config.parseFloat(var12, -1.0f);
                        if (var13 < 0.0f) {
                            continue;
                        }
                        this.charWidth[var11] = var13;
                    }
                }
            }
            catch (FileNotFoundException ex) {}
            catch (IOException var14) {
                var14.printStackTrace();
            }
        }
    }
    
    private static String fixHdFontName(final String var0) {
        if (!Config.isCustomFonts()) {
            return var0;
        }
        if (var0 == null) {
            return var0;
        }
        final String var = ".png";
        if (!var0.endsWith(var)) {
            return var0;
        }
        final String var2 = var0.substring(0, var0.length() - var.length());
        final String var3 = String.valueOf(var2) + "_hd" + var;
        return getFontTexturePack().func_98138_b(var3, true) ? var3 : var0;
    }
    
    private static ITexturePack getFontTexturePack() {
        return Config.isCustomFonts() ? Config.getMinecraft().texturePackList.getSelectedTexturePack() : Config.getMinecraft().texturePackList.availableTexturePacks().get(0);
    }
}
