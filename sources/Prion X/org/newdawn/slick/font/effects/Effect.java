package org.newdawn.slick.font.effects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;

public abstract interface Effect
{
  public abstract void draw(BufferedImage paramBufferedImage, Graphics2D paramGraphics2D, UnicodeFont paramUnicodeFont, Glyph paramGlyph);
}
