package org.newdawn.slick.font.effects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;

public interface Effect {
  void draw(BufferedImage paramBufferedImage, Graphics2D paramGraphics2D, UnicodeFont paramUnicodeFont, Glyph paramGlyph);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\font\effects\Effect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */