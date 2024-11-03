package net.augustus.font.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.augustus.font.testfontbase.FontUtil;

public class CFont {
   private String filePath;
   private int fontSize;
   private int width;
   private int height;
   private int lineHeight;
   private Map<Integer, CharInfo> characterMap;

   public CFont(String filepath, int fontSize) {
      this.filePath = filepath;
      this.fontSize = fontSize;
      this.characterMap = new HashMap<>();
      this.generateBitMap();
   }

   public void generateBitMap() {
      InputStream is = FontUtil.class.getClassLoader().getResourceAsStream(this.filePath);
      Font font = null;

      try {
         font = Font.createFont(0, is);
         font = font.deriveFont(0, 64.0F);
      } catch (FontFormatException var12) {
         var12.printStackTrace();
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      BufferedImage img = new BufferedImage(1, 1, 4);
      Graphics2D g2d = img.createGraphics();
      g2d.setFont(font);
      FontMetrics fontMetrics = g2d.getFontMetrics();
      int estimatedWith = (int)Math.sqrt((double)font.getNumGlyphs()) * font.getSize() + 1;
      this.width = 0;
      this.height = fontMetrics.getHeight();
      this.lineHeight = fontMetrics.getHeight();
      int x = 0;
      int y = (int)((float)fontMetrics.getHeight() * 1.4F);

      for(int i = 0; i < font.getNumGlyphs(); ++i) {
         if (font.canDisplay(i)) {
            CharInfo charInfo = new CharInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getHeight());
            this.characterMap.put(i, charInfo);
            this.width = Math.max(x + fontMetrics.charWidth(i), this.width);
            x += charInfo.width;
            if (x > estimatedWith) {
               x = 0;
               y = (int)((float)y + (float)fontMetrics.getHeight() * 1.4F);
               this.height = (int)((float)this.height + (float)fontMetrics.getHeight() * 1.4F);
            }
         }
      }

      this.height = (int)((float)this.height + (float)fontMetrics.getHeight() * 1.4F);
      g2d.dispose();
      img = new BufferedImage(this.width, this.height, 4);
      g2d = img.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setFont(font);
      g2d.setColor(Color.white);

      for(int i = 0; i < font.getNumGlyphs(); ++i) {
         if (font.canDisplay(i)) {
            CharInfo info = this.characterMap.get(i);
            info.calculateTextureCoordinates(this.width, this.height);
            g2d.drawString("" + (char)i, info.sourceX, info.sourceY);
         }
      }

      g2d.dispose();

      try {
         File file = new File("tmp.png");
         ImageIO.write(img, "png", file);
      } catch (IOException var11) {
         var11.printStackTrace();
      }
   }
}
