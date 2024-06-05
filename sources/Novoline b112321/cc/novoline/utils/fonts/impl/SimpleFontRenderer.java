package cc.novoline.utils.fonts.impl;

import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.SimpleFontManager;
import cc.novoline.utils.fonts.impl.SimpleFontRenderer$CharData;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import net.acE;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

final class SimpleFontRenderer implements FontRenderer {
   private static final int[] m = setupMinecraftColorCodes();
   private static final String a = "0123456789abcdefklmnor";
   private static final char COLOR_PREFIX = '§';
   private static final short CHARS = 256;
   private static final float IMG_SIZE = 512.0F;
   private static final float CHAR_OFFSET = 0.0F;
   private final SimpleFontRenderer$CharData[] charData = new SimpleFontRenderer$CharData[256];
   private final SimpleFontRenderer$CharData[] h = new SimpleFontRenderer$CharData[256];
   private final SimpleFontRenderer$CharData[] f = new SimpleFontRenderer$CharData[256];
   private final SimpleFontRenderer$CharData[] k = new SimpleFontRenderer$CharData[256];
   private final Font awtFont;
   private final boolean antiAlias;
   private final boolean fractionalMetrics;
   private DynamicTexture l;
   private DynamicTexture e;
   private DynamicTexture d;
   private DynamicTexture c;
   private int fontHeight = -1;

   private SimpleFontRenderer(Font var1, boolean var2, boolean var3) {
      this.awtFont = var1;
      this.antiAlias = var2;
      this.fractionalMetrics = var3;
      this.setupBoldItalicFonts();
   }

   static FontRenderer create(Font var0, boolean var1, boolean var2) {
      return new SimpleFontRenderer(var0, var1, var2);
   }

   public static FontRenderer create(Font var0) {
      return create(var0, true, true);
   }

   private DynamicTexture setupTexture(Font var1, boolean var2, boolean var3, SimpleFontRenderer$CharData[] var4) {
      return new DynamicTexture(this.generateFontImage(var1, var2, var3, var4));
   }

   private BufferedImage generateFontImage(Font var1, boolean var2, boolean var3, SimpleFontRenderer$CharData[] var4) {
      boolean var6 = true;
      SimpleFontManager.b();
      BufferedImage var7 = new BufferedImage(512, 512, 2);
      Graphics2D var8 = (Graphics2D)var7.getGraphics();
      var8.setFont(var1);
      var8.setColor(new Color(255, 255, 255, 0));
      var8.fillRect(0, 0, 512, 512);
      var8.setColor(Color.WHITE);
      var8.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      var8.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      var8.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      if(this.fractionalMetrics) {
         var8.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
      }

      var8.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
      FontMetrics var9 = var8.getFontMetrics();
      int var10 = 0;
      byte var11 = 0;
      int var12 = 1;
      int var13 = 0;
      if(var13 < var4.length) {
         char var14 = (char)var13;
         SimpleFontRenderer$CharData var15 = new SimpleFontRenderer$CharData();
         Rectangle2D var16 = var9.getStringBounds(String.valueOf(var14), var8);
         SimpleFontRenderer$CharData.access$102(var15, var16.getBounds().width + 8);
         SimpleFontRenderer$CharData.access$202(var15, var16.getBounds().height);
         if(var11 + SimpleFontRenderer$CharData.access$100(var15) >= 512) {
            var11 = 0;
            var12 += var10;
            var10 = 0;
         }

         if(SimpleFontRenderer$CharData.access$200(var15) > var10) {
            var10 = SimpleFontRenderer$CharData.access$200(var15);
         }

         SimpleFontRenderer$CharData.access$302(var15, var11);
         SimpleFontRenderer$CharData.access$402(var15, var12);
         if(SimpleFontRenderer$CharData.access$200(var15) > this.fontHeight) {
            this.fontHeight = SimpleFontRenderer$CharData.access$200(var15);
         }

         var4[var13] = var15;
         var8.drawString(String.valueOf(var14), var11 + 2, var12 + var9.getAscent());
         int var10000 = var11 + SimpleFontRenderer$CharData.access$100(var15);
         ++var13;
      }

      return var7;
   }

   private void setupBoldItalicFonts() {
      this.l = this.setupTexture(this.awtFont, this.antiAlias, this.fractionalMetrics, this.charData);
      this.e = this.setupTexture(this.awtFont.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.h);
      this.d = this.setupTexture(this.awtFont.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.f);
      this.c = this.setupTexture(this.awtFont.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.k);
   }

   public float drawString(CharSequence var1, double var2, double var4, int var6, boolean var7) {
      float var8 = this.b(var1, var2 + 0.5D, var4 + 0.5D, var6, true);
      return Math.max(var8, this.b(var1, var2, var4, var6, false));
   }

   private float b(CharSequence var1, double var2, double var4, int var6, boolean var7) {
      SimpleFontManager.b();
      --var2;
      return 0.0F;
   }

   public String trimStringToWidth(CharSequence var1, int var2, boolean var3) {
      StringBuilder var5 = new StringBuilder();
      SimpleFontManager.b();
      float var6 = 0.0F;
      int var7 = var3?var1.length() - 1:0;
      int var8 = var3?-1:1;
      boolean var9 = false;
      boolean var10 = false;
      if(var7 < var1.length() && var6 < (float)var2) {
         char var12;
         label90: {
            var12 = var1.charAt(var7);
            float var13 = (float)this.stringWidth(String.valueOf(var12));
            if(var9) {
               var9 = false;
               if(var12 != 108 && var12 != 76) {
                  if(var12 != 114 && var12 != 82) {
                     break label90;
                  }

                  var10 = false;
               }

               var10 = true;
            }

            if(var13 < 0.0F) {
               var9 = true;
            }

            var6 += var13;
            if(var10) {
               ++var6;
            }
         }

         if(var6 <= (float)var2) {
            if(var3) {
               var5.insert(0, var12);
            }

            var5.append(var12);
            int var11 = var7 + var8;
         }
      }

      return var5.toString();
   }

   public int stringWidth(CharSequence var1) {
      int[] var2 = SimpleFontManager.b();
      return 0;
   }

   public float charWidth(char var1) {
      int[] var2 = SimpleFontManager.b();
      float var10000 = (float)((SimpleFontRenderer$CharData.access$100(this.charData[var1]) - 8) / 2);
      if(acE.b() == null) {
         SimpleFontManager.b(new int[5]);
      }

      return var10000;
   }

   public SimpleFontRenderer$CharData[] getCharData() {
      return this.charData;
   }

   private static int[] setupMinecraftColorCodes() {
      int[] var0 = new int[32];

      for(int var1 = 0; var1 < 32; ++var1) {
         int var2 = (var1 >> 3 & 1) * 85;
         int var3 = (var1 >> 2 & 1) * 170 + var2;
         int var4 = (var1 >> 1 & 1) * 170 + var2;
         int var5 = (var1 & 1) * 170 + var2;
         if(var1 == 6) {
            var3 += 85;
         }

         if(var1 >= 16) {
            var3 >>= 2;
            var4 >>= 2;
            var5 >>= 2;
         }

         var0[var1] = (var3 & 255) << 16 | (var4 & 255) << 8 | var5 & 255;
      }

      return var0;
   }

   private static void drawChar(SimpleFontRenderer$CharData[] var0, char var1, float var2, float var3) {
      drawQuad(var2, var3, (float)SimpleFontRenderer$CharData.access$100(var0[var1]), (float)SimpleFontRenderer$CharData.access$200(var0[var1]), (float)SimpleFontRenderer$CharData.access$300(var0[var1]), (float)SimpleFontRenderer$CharData.access$400(var0[var1]), (float)SimpleFontRenderer$CharData.access$100(var0[var1]), (float)SimpleFontRenderer$CharData.access$200(var0[var1]));
   }

   private static void drawQuad(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var4 / 512.0F;
      float var9 = var5 / 512.0F;
      float var10 = var6 / 512.0F;
      float var11 = var7 / 512.0F;
      GL11.glTexCoord2f(var8 + var10, var9);
      GL11.glVertex2d((double)(var0 + var2), (double)var1);
      GL11.glTexCoord2f(var8, var9);
      GL11.glVertex2d((double)var0, (double)var1);
      GL11.glTexCoord2f(var8, var9 + var11);
      GL11.glVertex2d((double)var0, (double)(var1 + var3));
      GL11.glTexCoord2f(var8, var9 + var11);
      GL11.glVertex2d((double)var0, (double)(var1 + var3));
      GL11.glTexCoord2f(var8 + var10, var9 + var11);
      GL11.glVertex2d((double)(var0 + var2), (double)(var1 + var3));
      GL11.glTexCoord2f(var8 + var10, var9);
      GL11.glVertex2d((double)(var0 + var2), (double)var1);
   }

   private static void drawLine(double var0, double var2, double var4, double var6, float var8) {
      GL11.glDisable(3553);
      GL11.glLineWidth(var8);
      GL11.glBegin(1);
      GL11.glVertex2d(var0, var2);
      GL11.glVertex2d(var4, var6);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public String getName() {
      return this.awtFont.getFamily();
   }

   public int getHeight() {
      return (this.fontHeight - 8) / 2;
   }

   public boolean isAntiAlias() {
      return this.antiAlias;
   }

   public boolean isFractionalMetrics() {
      return this.fractionalMetrics;
   }
}
