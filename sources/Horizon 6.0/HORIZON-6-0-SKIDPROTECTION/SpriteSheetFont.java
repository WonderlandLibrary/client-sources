package HORIZON-6-0-SKIDPROTECTION;

import java.io.UnsupportedEncodingException;

public class SpriteSheetFont implements Font
{
    private SpriteSheet HorizonCode_Horizon_È;
    private char Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    
    public SpriteSheetFont(final SpriteSheet font, final char startingCharacter) {
        this.HorizonCode_Horizon_È = font;
        this.Â = startingCharacter;
        this.Âµá€ = font.HorizonCode_Horizon_È();
        final int verticalCount = font.á();
        this.Ý = font.ŒÏ() / this.Âµá€;
        this.Ø­áŒŠá = font.Çªà¢() / verticalCount;
        this.Ó = this.Âµá€ * verticalCount;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text) {
        this.HorizonCode_Horizon_È(x, y, text, Color.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color col) {
        this.HorizonCode_Horizon_È(x, y, text, col, 0, text.length() - 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final String text, final Color col, final int startIndex, final int endIndex) {
        try {
            final byte[] data = text.getBytes("US-ASCII");
            for (int i = 0; i < data.length; ++i) {
                final int index = data[i] - this.Â;
                if (index < this.Ó) {
                    final int xPos = index % this.Âµá€;
                    final int yPos = index / this.Âµá€;
                    if (i >= startIndex || i <= endIndex) {
                        this.HorizonCode_Horizon_È.Ø­áŒŠá(xPos, yPos).HorizonCode_Horizon_È(x + i * this.Ý, y, col);
                    }
                }
            }
        }
        catch (UnsupportedEncodingException e) {
            Log.HorizonCode_Horizon_È(e);
        }
    }
    
    @Override
    public int Â(final String text) {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Ý(final String text) {
        return this.Ý * text.length();
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
}
