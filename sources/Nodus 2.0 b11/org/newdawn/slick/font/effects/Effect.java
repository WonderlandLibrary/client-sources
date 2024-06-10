package org.newdawn.slick.font.effects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;

public abstract interface Effect
{
  public abstract void draw(BufferedImage paramBufferedImage, Graphics2D paramGraphics2D, UnicodeFont paramUnicodeFont, Glyph paramGlyph);
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.font.effects.Effect
 * JD-Core Version:    0.7.0.1
 */