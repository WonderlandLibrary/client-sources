package xyz.cucumber.base.utils.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class FontUtils {
   private float imgSize = 512.0F;
   protected FontUtils.CharData[] charData = new FontUtils.CharData[256];
   protected FontUtils.CharData[] boldChars = new FontUtils.CharData[256];
   protected FontUtils.CharData[] italicChars = new FontUtils.CharData[256];
   protected FontUtils.CharData[] boldItalicChars = new FontUtils.CharData[256];
   protected Font font;
   protected boolean antiAlias;
   protected boolean fractionalMetrics;
   protected int fontHeight = -1;
   protected int charOffset = 0;
   protected DynamicTexture tex;
   protected DynamicTexture texBold;
   protected DynamicTexture texItalic;
   protected DynamicTexture texItalicBold;
   public static double fontScaleOffset = 2.0;
   private final int[] colorCode = new int[32];
   private final String colorcodeIdentifiers = "0123456789abcdefklmnor";
   char COLOR_INVOKER = 167;

   public FontUtils(Font font) {
      this(font, true, true);
   }

   public FontUtils(Font font, boolean antiAlias) {
      this(font, true, antiAlias);
   }

   public FontUtils(Font font, boolean antiAlias, boolean fractionalMetrics) {
      this.font = font;
      this.antiAlias = antiAlias;
      this.fractionalMetrics = fractionalMetrics;
      this.tex = this.setupTexture(font, antiAlias, fractionalMetrics, this.charData);
      this.texBold = this.setupTexture(font.deriveFont(1), antiAlias, fractionalMetrics, this.boldChars);
      this.texItalic = this.setupTexture(font.deriveFont(2), antiAlias, fractionalMetrics, this.italicChars);
      this.texItalicBold = this.setupTexture(font.deriveFont(3), antiAlias, fractionalMetrics, this.boldItalicChars);
      this.setupColorcodes();
   }

   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, FontUtils.CharData[] chars) {
      BufferedImage img = this.generateFontImage(font, antiAlias, fractionalMetrics, chars);

      try {
         return new DynamicTexture(img);
      } catch (Exception var7) {
         var7.printStackTrace();
         return null;
      }
   }

   private void setupColorcodes() {
      for (int index = 0; index < 32; index++) {
         int noClue = (index >> 3 & 1) * 85;
         int red = (index >> 2 & 1) * 170 + noClue;
         int green = (index >> 1 & 1) * 170 + noClue;
         int blue = (index >> 0 & 1) * 170 + noClue;
         if (index == 6) {
            red += 85;
         }

         if (index >= 16) {
            red /= 4;
            green /= 4;
            blue /= 4;
         }

         this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
      }
   }

   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, FontUtils.CharData[] chars) {
      int imgSize = (int)this.imgSize;
      BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
      Graphics2D g2 = (Graphics2D)bufferedImage.getGraphics();
      g2.setFont(font);
      g2.setColor(new Color(255, 255, 255, 0));
      g2.fillRect(0, 0, imgSize, imgSize);
      g2.setColor(Color.WHITE);
      g2.setRenderingHint(
         RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF
      );
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      FontMetrics fontMetrics = g2.getFontMetrics();
      int charHeight = 0;
      int positionX = 0;
      int positionY = 1;

      for (int i2 = 0; i2 < chars.length; i2++) {
         char ch2 = (char)i2;
         FontUtils.CharData charData = new FontUtils.CharData();
         Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch2), g2);
         charData.width = dimensions.getBounds().width + 8;
         charData.height = dimensions.getBounds().height;
         if (positionX + charData.width >= imgSize) {
            positionX = 0;
            positionY += charHeight;
            charHeight = 0;
         }

         if (charData.height > charHeight) {
            charHeight = charData.height;
         }

         charData.storedX = positionX;
         charData.storedY = positionY;
         if (charData.height > this.fontHeight) {
            this.fontHeight = charData.height;
         }

         chars[i2] = charData;
         g2.drawString(String.valueOf(ch2), positionX + 2, positionY + fontMetrics.getAscent());
         positionX += charData.width;
      }

      return bufferedImage;
   }

   public float drawString(String text, double x, double y, int color) {
      return this.drawString(text, x, y, color, false, 9.0F);
   }

   public void drawHighlitedString(String text, String contain, double x, double y, int color, int hightLightColor) {
      if (text.toLowerCase().equals(contain.toLowerCase())) {
         this.drawString(text, x, y, hightLightColor, false, 9.0F);
      } else if (text.toLowerCase().contains(contain.toLowerCase())) {
         String[] splitted = text.toLowerCase().split(contain.toLowerCase());
         double spacing = 0.0;

         for (int i = 0; i < splitted.length; i++) {
            this.drawString(splitted[i], x + spacing, y, color, false, 9.0F);
            spacing += this.getWidth(splitted[i]);
            if (splitted.length - 1 != i) {
               this.drawString(contain.toLowerCase(), x + spacing, y, hightLightColor, false, 9.0F);
               spacing += this.getWidth(contain.toLowerCase());
            }
         }

         if (text.toLowerCase().endsWith(contain.toLowerCase())) {
            spacing += (double)this.drawString(contain.toLowerCase(), x + spacing, y, hightLightColor, false, 9.0F);
         }
      } else {
         this.drawString(text, x, y, color, false, 9.0F);
      }
   }

   public void drawNewlineString(String text, double x, double y, int color, double offset) {
      String[] t = text.split("\n");
      double y1 = y;

      for (String sa : t) {
         this.drawString(sa, x, y1, color, false, 9.0F);
         y1 += offset;
      }
   }

   public float drawStringWithShadow(String text, double x, double y, int color, int shadowColor) {
      this.drawString(text, x + 0.5, y + 0.5, shadowColor, false, 9.0F);
      return this.drawString(text, x, y, color, false, 9.0F);
   }

   public float drawStringWithShadow(String text, double x, double y, double offset, int color, int shadowColor) {
      this.drawString(text, x + offset, y + offset, shadowColor, false, 9.0F);
      return this.drawString(text, x, y, color, false, 9.0F);
   }

   public float drawString(String text, double x2, double y2, int color, boolean shadow, float kerning) {
      x2--;
      if (text == null) {
         return 0.0F;
      } else {
         FontUtils.CharData[] currentData = this.charData;
         float alpha = (float)(color >> 24 & 0xFF) / 255.0F;
         boolean randomCase = false;
         boolean bold = false;
         boolean italic = false;
         boolean strikethrough = false;
         boolean underline = false;
         boolean render = true;
         x2 *= 2.0 * fontScaleOffset;
         y2 = (y2 - 3.0) * 2.0 * fontScaleOffset;
         GL11.glPushMatrix();
         GlStateManager.scale(0.5 / fontScaleOffset, 0.5 / fontScaleOffset, 0.5 / fontScaleOffset);
         GL11.glEnable(3042);
         GL14.glBlendEquation(32774);
         GlStateManager.color((float)(color >> 16 & 0xFF) / 255.0F, (float)(color >> 8 & 0xFF) / 255.0F, (float)(color & 0xFF) / 255.0F, alpha);
         int size = text.length();
         GlStateManager.enableTexture2D();
         GlStateManager.bindTexture(this.tex.getGlTextureId());
         GL11.glBindTexture(3553, this.tex.getGlTextureId());
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);

         for (int i2 = 0; i2 < size; i2++) {
            char character = text.charAt(i2);
            if (character == 167 && i2 < size) {
               int colorIndex = 21;

               try {
                  colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i2 + 1));
               } catch (Exception var22) {
                  var22.printStackTrace();
               }

               if (colorIndex >= 16) {
                  if (colorIndex == 16) {
                     randomCase = true;
                  } else if (colorIndex == 17) {
                     bold = true;
                     if (italic) {
                        GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.bindTexture(this.texBold.getGlTextureId());
                        currentData = this.boldChars;
                     }
                  } else if (colorIndex == 18) {
                     strikethrough = true;
                  } else if (colorIndex == 19) {
                     underline = true;
                  } else if (colorIndex == 20) {
                     italic = true;
                     if (bold) {
                        GlStateManager.bindTexture(this.texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                     } else {
                        GlStateManager.bindTexture(this.texItalic.getGlTextureId());
                        currentData = this.italicChars;
                     }
                  } else if (colorIndex == 21) {
                     bold = false;
                     italic = false;
                     randomCase = false;
                     underline = false;
                     strikethrough = false;
                     GlStateManager.color((float)(color >> 16 & 0xFF) / 255.0F, (float)(color >> 8 & 0xFF) / 255.0F, (float)(color & 0xFF) / 255.0F, alpha);
                     GlStateManager.bindTexture(this.tex.getGlTextureId());
                     currentData = this.charData;
                  }
               } else {
                  bold = false;
                  italic = false;
                  randomCase = false;
                  underline = false;
                  strikethrough = false;
                  GlStateManager.bindTexture(this.tex.getGlTextureId());
                  currentData = this.charData;
                  if (colorIndex < 0 || colorIndex > 15) {
                     colorIndex = 15;
                  }

                  if (shadow) {
                     colorIndex += 16;
                  }

                  int colorcode = this.colorCode[colorIndex];
                  GlStateManager.color(
                     (float)(colorcode >> 16 & 0xFF) / 255.0F, (float)(colorcode >> 8 & 0xFF) / 255.0F, (float)(colorcode & 0xFF) / 255.0F, alpha
                  );
               }

               i2++;
            } else if (character < currentData.length && character >= 0) {
               GL11.glBegin(4);
               this.drawChar(currentData, character, (float)x2, (float)y2);
               GL11.glEnd();
               x2 += (double)((float)currentData[character].width - 8.3F + (float)this.charOffset);
            }
         }

         GL11.glDisable(3042);
         GL11.glHint(3155, 4352);
         GL11.glPopMatrix();
         return (float)x2 / 2.0F;
      }
   }

   public void drawChar(FontUtils.CharData[] chars, char c2, float x2, float y2) throws ArrayIndexOutOfBoundsException {
      try {
         this.drawQuad(
            x2,
            y2,
            (float)chars[c2].width,
            (float)chars[c2].height,
            (float)chars[c2].storedX,
            (float)chars[c2].storedY,
            (float)chars[c2].width,
            (float)chars[c2].height
         );
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   protected void drawQuad(float x2, float y2, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
      float renderSRCX = srcX / this.imgSize;
      float renderSRCY = srcY / this.imgSize;
      float renderSRCWidth = srcWidth / this.imgSize;
      float renderSRCHeight = srcHeight / this.imgSize;
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x2 + width), (double)y2);
      GL11.glTexCoord2f(renderSRCX, renderSRCY);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x2, (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x2, (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)(x2 + width), (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x2 + width), (double)y2);
   }

   public float getHeight(String text) {
      return (float)((double)((this.fontHeight - (int)(8.3 * fontScaleOffset)) / (int)(2.0 * fontScaleOffset)) - 2.0 * fontScaleOffset);
   }

   public double getWidth(String text) {
      int width = 0;

      for (char c2 : text.toCharArray()) {
         if (c2 < this.charData.length && c2 != this.COLOR_INVOKER) {
            width = (int)((double)width + (double)(this.charData[c2].width - 8) + (double)this.charOffset * fontScaleOffset);
         }
      }

      return (double)width / (2.0 * fontScaleOffset);
   }

   public boolean isAntiAlias() {
      return this.antiAlias;
   }

   public void setAntiAlias(boolean antiAlias) {
      if (this.antiAlias != antiAlias) {
         this.antiAlias = antiAlias;
         this.tex = this.setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
      }
   }

   public boolean isFractionalMetrics() {
      return this.fractionalMetrics;
   }

   public void setFractionalMetrics(boolean fractionalMetrics) {
      if (this.fractionalMetrics != fractionalMetrics) {
         this.fractionalMetrics = fractionalMetrics;
         this.tex = this.setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
      }
   }

   public Font getFont() {
      return this.font;
   }

   public void setFont(Font font) {
      this.font = font;
      this.tex = this.setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
   }

   protected class CharData {
      public int width;
      public int height;
      public int storedX;
      public int storedY;
   }
}
