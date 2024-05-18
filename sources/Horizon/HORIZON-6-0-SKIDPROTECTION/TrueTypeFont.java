package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.awt.FontMetrics;
import java.util.Map;

public class TrueTypeFont implements Font
{
    private static final SGL HorizonCode_Horizon_È;
    private HorizonCode_Horizon_È[] Â;
    private Map Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private Texture à;
    private int Ø;
    private int áŒŠÆ;
    private java.awt.Font áˆºÑ¢Õ;
    private FontMetrics ÂµÈ;
    
    static {
        HorizonCode_Horizon_È = Renderer.HorizonCode_Horizon_È();
    }
    
    public TrueTypeFont(final java.awt.Font font, final boolean antiAlias, final char[] additionalChars) {
        this.Â = new HorizonCode_Horizon_È[256];
        this.Ý = new HashMap();
        this.Âµá€ = 0;
        this.Ó = 0;
        this.Ø = 512;
        this.áŒŠÆ = 512;
        GLUtils.HorizonCode_Horizon_È();
        this.áˆºÑ¢Õ = font;
        this.Âµá€ = font.getSize();
        this.Ø­áŒŠá = antiAlias;
        this.HorizonCode_Horizon_È(additionalChars);
    }
    
    public TrueTypeFont(final java.awt.Font font, final boolean antiAlias) {
        this(font, antiAlias, null);
    }
    
    private BufferedImage HorizonCode_Horizon_È(final char ch) {
        final BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
        final Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
        if (this.Ø­áŒŠá) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(this.áˆºÑ¢Õ);
        this.ÂµÈ = g.getFontMetrics();
        int charwidth = this.ÂµÈ.charWidth(ch);
        if (charwidth <= 0) {
            charwidth = 1;
        }
        int charheight = this.ÂµÈ.getHeight();
        if (charheight <= 0) {
            charheight = this.Âµá€;
        }
        final BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
        final Graphics2D gt = (Graphics2D)fontImage.getGraphics();
        if (this.Ø­áŒŠá) {
            gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        gt.setFont(this.áˆºÑ¢Õ);
        gt.setColor(Color.WHITE);
        final int charx = 0;
        final int chary = 0;
        gt.drawString(String.valueOf(ch), charx, chary + this.ÂµÈ.getAscent());
        return fontImage;
    }
    
    private void HorizonCode_Horizon_È(final char[] customCharsArray) {
        if (customCharsArray != null && customCharsArray.length > 0) {
            this.Ø *= 2;
        }
        try {
            final BufferedImage imgTemp = new BufferedImage(this.Ø, this.áŒŠÆ, 2);
            final Graphics2D g = (Graphics2D)imgTemp.getGraphics();
            g.setColor(new Color(255, 255, 255, 1));
            g.fillRect(0, 0, this.Ø, this.áŒŠÆ);
            int rowHeight = 0;
            int positionX = 0;
            int positionY = 0;
            for (int customCharsLength = (customCharsArray != null) ? customCharsArray.length : 0, i = 0; i < 256 + customCharsLength; ++i) {
                final char ch = (i < 256) ? ((char)i) : customCharsArray[i - 256];
                BufferedImage fontImage = this.HorizonCode_Horizon_È(ch);
                final HorizonCode_Horizon_È newIntObject = new HorizonCode_Horizon_È((HorizonCode_Horizon_È)null);
                newIntObject.HorizonCode_Horizon_È = fontImage.getWidth();
                newIntObject.Â = fontImage.getHeight();
                if (positionX + newIntObject.HorizonCode_Horizon_È >= this.Ø) {
                    positionX = 0;
                    positionY += rowHeight;
                    rowHeight = 0;
                }
                newIntObject.Ý = positionX;
                newIntObject.Ø­áŒŠá = positionY;
                if (newIntObject.Â > this.Ó) {
                    this.Ó = newIntObject.Â;
                }
                if (newIntObject.Â > rowHeight) {
                    rowHeight = newIntObject.Â;
                }
                g.drawImage(fontImage, positionX, positionY, null);
                positionX += newIntObject.HorizonCode_Horizon_È;
                if (i < 256) {
                    this.Â[i] = newIntObject;
                }
                else {
                    this.Ý.put(new Character(ch), newIntObject);
                }
                fontImage = null;
            }
            this.à = BufferedImageUtil.HorizonCode_Horizon_È(this.áˆºÑ¢Õ.toString(), imgTemp);
        }
        catch (IOException e) {
            System.err.println("Failed to create font.");
            e.printStackTrace();
        }
    }
    
    private void HorizonCode_Horizon_È(final float drawX, final float drawY, final float drawX2, final float drawY2, final float srcX, final float srcY, final float srcX2, final float srcY2) {
        final float DrawWidth = drawX2 - drawX;
        final float DrawHeight = drawY2 - drawY;
        final float TextureSrcX = srcX / this.Ø;
        final float TextureSrcY = srcY / this.áŒŠÆ;
        final float SrcWidth = srcX2 - srcX;
        final float SrcHeight = srcY2 - srcY;
        final float RenderWidth = SrcWidth / this.Ø;
        final float RenderHeight = SrcHeight / this.áŒŠÆ;
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureSrcX, TextureSrcY);
        TrueTypeFont.HorizonCode_Horizon_È.Â(drawX, drawY);
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureSrcX, TextureSrcY + RenderHeight);
        TrueTypeFont.HorizonCode_Horizon_È.Â(drawX, drawY + DrawHeight);
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureSrcX + RenderWidth, TextureSrcY + RenderHeight);
        TrueTypeFont.HorizonCode_Horizon_È.Â(drawX + DrawWidth, drawY + DrawHeight);
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(TextureSrcX + RenderWidth, TextureSrcY);
        TrueTypeFont.HorizonCode_Horizon_È.Â(drawX + DrawWidth, drawY);
    }
    
    @Override
    public int Ý(final String whatchars) {
        int totalwidth = 0;
        HorizonCode_Horizon_È intObject = null;
        int currentChar = 0;
        for (int i = 0; i < whatchars.length(); ++i) {
            currentChar = whatchars.charAt(i);
            if (currentChar < 256) {
                intObject = this.Â[currentChar];
            }
            else {
                intObject = this.Ý.get(new Character((char)currentChar));
            }
            if (intObject != null) {
                totalwidth += intObject.HorizonCode_Horizon_È;
            }
        }
        return totalwidth;
    }
    
    public int Â() {
        return this.Ó;
    }
    
    @Override
    public int Â(final String HeightString) {
        return this.Ó;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String whatchars, final HORIZON-6-0-SKIDPROTECTION.Color color) {
        this.HorizonCode_Horizon_È(x, y, whatchars, color, 0, whatchars.length() - 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String whatchars, final HORIZON-6-0-SKIDPROTECTION.Color color, final int startIndex, final int endIndex) {
        color.HorizonCode_Horizon_È();
        this.à.Ý();
        HorizonCode_Horizon_È intObject = null;
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È(7);
        int totalwidth = 0;
        for (int i = 0; i < whatchars.length(); ++i) {
            final int charCurrent = whatchars.charAt(i);
            if (charCurrent < 256) {
                intObject = this.Â[charCurrent];
            }
            else {
                intObject = this.Ý.get(new Character((char)charCurrent));
            }
            if (intObject != null) {
                if (i >= startIndex || i <= endIndex) {
                    this.HorizonCode_Horizon_È(x + totalwidth, y, x + totalwidth + intObject.HorizonCode_Horizon_È, y + intObject.Â, intObject.Ý, intObject.Ø­áŒŠá, intObject.Ý + intObject.HorizonCode_Horizon_È, intObject.Ø­áŒŠá + intObject.Â);
                }
                totalwidth += intObject.HorizonCode_Horizon_È;
            }
        }
        TrueTypeFont.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String whatchars) {
        this.HorizonCode_Horizon_È(x, y, whatchars, HORIZON-6-0-SKIDPROTECTION.Color.Ý);
    }
    
    private class HorizonCode_Horizon_È
    {
        public int HorizonCode_Horizon_È;
        public int Â;
        public int Ý;
        public int Ø­áŒŠá;
    }
}
