package net.minecraft.client.gui;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import net.qT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.FontUtils;
import org.lwjgl.opengl.GL11;

public class FontRenderer implements IResourceManagerReloadListener {
   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
   private float[] charWidth = new float[256];
   public int FONT_HEIGHT = 9;
   public Random fontRandom = new Random();
   private byte[] glyphWidth = new byte[65536];
   public int[] colorCode = new int[32];
   private ResourceLocation locationFontTexture;
   private final TextureManager renderEngine;
   private float posX;
   private float posY;
   private boolean unicodeFlag;
   private boolean bidiFlag;
   private float y;
   private float o;
   private float e;
   private float k;
   private int textColor;
   private boolean randomStyle;
   private boolean boldStyle;
   private boolean italicStyle;
   private boolean underlineStyle;
   private boolean strikethroughStyle;
   private static final String x = "CL_00000660";
   public GameSettings gameSettings;
   public ResourceLocation locationFontTextureBase;
   public boolean enabled = true;
   public float offsetBold = 1.0F;
   private ResourceLocation lastUsedLocation = null;

   public FontRenderer(GameSettings var1, ResourceLocation var2, TextureManager var3, boolean var4) {
      this.gameSettings = var1;
      this.locationFontTextureBase = var2;
      this.locationFontTexture = var2;
      this.renderEngine = var3;
      this.unicodeFlag = var4;
      this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
      this.bindTexture(this.locationFontTexture);

      for(int var5 = 0; var5 < 32; ++var5) {
         int var6 = (var5 >> 3 & 1) * 85;
         int var7 = (var5 >> 2 & 1) * 170 + var6;
         int var8 = (var5 >> 1 & 1) * 170 + var6;
         int var9 = (var5 >> 0 & 1) * 170 + var6;
         if(var5 == 6) {
            var7 += 85;
         }

         if(var1.anaglyph) {
            int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
            int var11 = (var7 * 30 + var8 * 70) / 100;
            int var12 = (var7 * 30 + var9 * 70) / 100;
            var7 = var10;
            var8 = var11;
            var9 = var12;
         }

         if(var5 >= 16) {
            var7 /= 4;
            var8 /= 4;
            var9 /= 4;
         }

         this.colorCode[var5] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
      }

      this.readGlyphSizes();
   }

   public void onResourceManagerReload(IResourceManager var1) {
      this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);

      for(int var2 = 0; var2 < unicodePageLocations.length; ++var2) {
         unicodePageLocations[var2] = null;
      }

      this.readFontTexture();
      this.readGlyphSizes();
   }

   private void readFontTexture() {
      BufferedImage var1;
      try {
         var1 = TextureUtil.a(this.getResourceInputStream(this.locationFontTexture));
      } catch (IOException var21) {
         throw new RuntimeException(var21);
      }

      Properties var2 = FontUtils.readFontProperties(this.locationFontTexture);
      int var3 = var1.getWidth();
      int var4 = var1.getHeight();
      int var5 = var3 / 16;
      int var6 = var4 / 16;
      float var7 = (float)var3 / 128.0F;
      float var8 = Config.limit(var7, 1.0F, 2.0F);
      this.offsetBold = 1.0F / var8;
      float var9 = FontUtils.readFloat(var2, "offsetBold", -1.0F);
      if(var9 >= 0.0F) {
         this.offsetBold = var9;
      }

      int[] var10 = new int[var3 * var4];
      qT.b(var1, 0, 0, var3, var4, var10, 0, var3);

      for(int var11 = 0; var11 < 256; ++var11) {
         int var12 = var11 % 16;
         int var13 = var11 / 16;
         int var14 = 0;
         var14 = var5 - 1;
         int var15 = var12 * var5 + var14;
         boolean var16 = true;

         for(int var17 = 0; var17 < var6; ++var17) {
            int var18 = (var13 * var6 + var17) * var3;
            int var19 = var10[var15 + var18];
            int var20 = var19 >> 24 & 255;
            if(var20 > 16) {
               var16 = false;
            }
         }

         if(var11 == 65) {
            var11 = var11;
         }

         if(var11 == 32) {
            if(var5 <= 8) {
               var14 = (int)(2.0F * var7);
            } else {
               var14 = (int)(1.5F * var7);
            }
         }

         this.charWidth[var11] = (float)(var14 + 1) / var7 + 1.0F;
      }

      FontUtils.readCustomCharWidths(var2, this.charWidth);
   }

   private void readGlyphSizes() {
      // $FF: Couldn't be decompiled
   }

   private float func_181559_a(char var1, boolean var2) {
      if(var1 == 32) {
         return !this.unicodeFlag?this.charWidth[var1]:4.0F;
      } else {
         int var3 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(var1);
         return var3 != -1 && !this.unicodeFlag?this.renderDefaultChar(var3, var2):this.renderUnicodeChar(var1, var2);
      }
   }

   private float renderDefaultChar(int var1, boolean var2) {
      int var3 = var1 % 16 * 8;
      int var4 = var1 / 16 * 8;
      byte var5 = 1;
      if(this.lastUsedLocation != this.locationFontTexture) {
         this.bindTexture(this.locationFontTexture);
         this.lastUsedLocation = this.locationFontTexture;
      }

      float var6 = this.charWidth[var1];
      float var7 = 7.99F;
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)var3 / 128.0F, (float)var4 / 128.0F);
      GL11.glVertex3f(this.posX + (float)var5, this.posY, 0.0F);
      GL11.glTexCoord2f((float)var3 / 128.0F, ((float)var4 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.posX - (float)var5, this.posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)var3 + var7 - 1.0F) / 128.0F, (float)var4 / 128.0F);
      GL11.glVertex3f(this.posX + var7 - 1.0F + (float)var5, this.posY, 0.0F);
      GL11.glTexCoord2f(((float)var3 + var7 - 1.0F) / 128.0F, ((float)var4 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.posX + var7 - 1.0F - (float)var5, this.posY + 7.99F, 0.0F);
      GL11.glEnd();
      return var6;
   }

   private ResourceLocation getUnicodePageLocation(int var1) {
      if(unicodePageLocations[var1] == null) {
         unicodePageLocations[var1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[]{Integer.valueOf(var1)}));
         unicodePageLocations[var1] = FontUtils.getHdFontLocation(unicodePageLocations[var1]);
      }

      return unicodePageLocations[var1];
   }

   private void loadGlyphTexture(int var1) {
      ResourceLocation var2 = this.getUnicodePageLocation(var1);
      if(this.lastUsedLocation != var2) {
         this.bindTexture(var2);
         this.lastUsedLocation = var2;
      }

   }

   private float renderUnicodeChar(char var1, boolean var2) {
      if(this.glyphWidth[var1] == 0) {
         return 0.0F;
      } else {
         int var3 = var1 / 256;
         this.loadGlyphTexture(var3);
         int var4 = this.glyphWidth[var1] >>> 4;
         int var5 = this.glyphWidth[var1] & 15;
         var4 = var4 & 15;
         float var6 = (float)var4;
         float var7 = (float)(var5 + 1);
         float var8 = (float)(var1 % 16 * 16) + var6;
         float var9 = (float)((var1 & 255) / 16 * 16);
         float var10 = var7 - var6 - 0.02F;
         float var11 = 1.0F;
         GL11.glBegin(5);
         GL11.glTexCoord2f(var8 / 256.0F, var9 / 256.0F);
         GL11.glVertex3f(this.posX + var11, this.posY, 0.0F);
         GL11.glTexCoord2f(var8 / 256.0F, (var9 + 15.98F) / 256.0F);
         GL11.glVertex3f(this.posX - var11, this.posY + 7.99F, 0.0F);
         GL11.glTexCoord2f((var8 + var10) / 256.0F, var9 / 256.0F);
         GL11.glVertex3f(this.posX + var10 / 2.0F + var11, this.posY, 0.0F);
         GL11.glTexCoord2f((var8 + var10) / 256.0F, (var9 + 15.98F) / 256.0F);
         GL11.glVertex3f(this.posX + var10 / 2.0F - var11, this.posY + 7.99F, 0.0F);
         GL11.glEnd();
         return (var7 - var6) / 2.0F + 1.0F;
      }
   }

   public int drawStringWithShadow(String var1, float var2, float var3, int var4) {
      return this.drawString(var1, var2, var3, var4, true);
   }

   public int drawString(String var1, float var2, float var3, int var4) {
      return !this.enabled?0:this.drawString(var1, var2, var3, var4, false);
   }

   public int drawString(String var1, float var2, float var3, int var4, boolean var5) {
      this.enableAlpha();
      this.resetStyles();
      int var6 = this.b(var1, var2 + 1.0F, var3 + 1.0F, var4, true);
      var6 = Math.max(var6, this.b(var1, var2, var3, var4, false));
      return var6;
   }

   private String bidiReorder(String var1) {
      try {
         Bidi var2 = new Bidi((new ArabicShaping(8)).shape(var1), 127);
         var2.setReorderingMode(0);
         return var2.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return var1;
      }
   }

   private void resetStyles() {
      this.randomStyle = false;
      this.boldStyle = false;
      this.italicStyle = false;
      this.underlineStyle = false;
      this.strikethroughStyle = false;
   }

   private void renderStringAtPos(String var1, boolean var2) {
      for(int var3 = 0; var3 < var1.length(); ++var3) {
         char var4 = var1.charAt(var3);
         if(var4 == 167 && var3 + 1 < var1.length()) {
            int var12 = "0123456789abcdefklmnor".indexOf(var1.toLowerCase().charAt(var3 + 1));
            if(var12 < 16) {
               this.randomStyle = false;
               this.boldStyle = false;
               this.strikethroughStyle = false;
               this.underlineStyle = false;
               this.italicStyle = false;
               if(var12 > 15) {
                  var12 = 15;
               }

               var12 = var12 + 16;
               int var15 = this.colorCode[var12];
               this.textColor = var15;
               this.setColor((float)(var15 >> 16) / 255.0F, (float)(var15 >> 8 & 255) / 255.0F, (float)(var15 & 255) / 255.0F, this.k);
            } else if(var12 == 16) {
               this.randomStyle = true;
            } else if(var12 == 17) {
               this.boldStyle = true;
            } else if(var12 == 18) {
               this.strikethroughStyle = true;
            } else if(var12 == 19) {
               this.underlineStyle = true;
            } else if(var12 == 20) {
               this.italicStyle = true;
            } else if(var12 == 21) {
               this.randomStyle = false;
               this.boldStyle = false;
               this.strikethroughStyle = false;
               this.underlineStyle = false;
               this.italicStyle = false;
               this.setColor(this.y, this.o, this.e, this.k);
            }

            ++var3;
         } else {
            int var5 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(var4);
            if(this.randomStyle && var5 != -1) {
               int var6 = this.getCharWidth(var4);

               char var7;
               while(true) {
                  var5 = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".length());
                  var7 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".charAt(var5);
                  if(var6 == this.getCharWidth(var7)) {
                     break;
                  }
               }

               var4 = var7;
            }

            float var14 = var5 != -1 && !this.unicodeFlag?this.offsetBold:0.5F;
            boolean var16 = var5 == -1 || this.unicodeFlag;
            this.posX -= var14;
            this.posY -= var14;
            float var8 = this.func_181559_a(var4, this.italicStyle);
            this.posX += var14;
            this.posY += var14;
            if(this.boldStyle) {
               this.posX += var14;
               this.posX -= var14;
               this.posY -= var14;
               this.func_181559_a(var4, this.italicStyle);
               this.posX -= var14;
               this.posX += var14;
               this.posY += var14;
               var8 += var14;
            }

            if(this.strikethroughStyle) {
               Tessellator var9 = Tessellator.getInstance();
               WorldRenderer var10 = var9.getWorldRenderer();
               GlStateManager.disableTexture2D();
               var10.begin(7, DefaultVertexFormats.POSITION);
               var10.pos((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D).endVertex();
               var10.pos((double)(this.posX + var8), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D).endVertex();
               var10.pos((double)(this.posX + var8), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
               var10.pos((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
               var9.draw();
               GlStateManager.enableTexture2D();
            }

            if(this.underlineStyle) {
               Tessellator var17 = Tessellator.getInstance();
               WorldRenderer var18 = var17.getWorldRenderer();
               GlStateManager.disableTexture2D();
               var18.begin(7, DefaultVertexFormats.POSITION);
               int var11 = this.underlineStyle?-1:0;
               var18.pos((double)(this.posX + (float)var11), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D).endVertex();
               var18.pos((double)(this.posX + var8), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D).endVertex();
               var18.pos((double)(this.posX + var8), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
               var18.pos((double)(this.posX + (float)var11), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
               var17.draw();
               GlStateManager.enableTexture2D();
            }

            this.posX += var8;
         }
      }

      this.lastUsedLocation = null;
   }

   private int renderStringAligned(String var1, int var2, int var3, int var4, int var5, boolean var6) {
      if(this.bidiFlag) {
         int var7 = this.d(this.bidiReorder(var1));
         var2 = var2 + var4 - var7;
      }

      return this.b(var1, (float)var2, (float)var3, var5, var6);
   }

   private int b(String var1, float var2, float var3, int var4, boolean var5) {
      return 0;
   }

   public int d(String var1) {
      return 0;
   }

   public int getCharWidth(char var1) {
      return Math.round(this.getCharWidthFloat(var1));
   }

   private float getCharWidthFloat(char var1) {
      if(var1 == 167) {
         return -1.0F;
      } else if(var1 == 32) {
         return this.charWidth[32];
      } else {
         int var2 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(var1);
         if(var2 != -1 && !this.unicodeFlag) {
            return this.charWidth[var2];
         } else if(this.glyphWidth[var1] != 0) {
            int var3 = this.glyphWidth[var1] >>> 4;
            int var4 = this.glyphWidth[var1] & 15;
            var3 = var3 & 15;
            ++var4;
            return (float)((var4 - var3) / 2 + 1);
         } else {
            return 0.0F;
         }
      }
   }

   public String trimStringToWidth(String var1, int var2) {
      return this.trimStringToWidth(var1, var2, false);
   }

   public String trimStringToWidth(String var1, int var2, boolean var3) {
      StringBuilder var4 = new StringBuilder();
      float var5 = 0.0F;
      int var6 = var1.length() - 1;
      byte var7 = -1;
      boolean var8 = false;
      boolean var9 = false;

      for(int var10 = var6; var10 < var1.length() && var5 < (float)var2; var10 += var7) {
         char var11 = var1.charAt(var10);
         this.getCharWidthFloat(var11);
         var8 = false;
         if(var11 != 108 && var11 != 76) {
            if(var11 == 114 || var11 == 82) {
               var9 = false;
            }
         } else {
            var9 = true;
         }

         if(var5 > (float)var2) {
            break;
         }

         var4.insert(0, var11);
      }

      return var4.toString();
   }

   private String trimStringNewline(String var1) {
      while(var1.endsWith("\n")) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      return var1;
   }

   public void drawSplitString(String var1, int var2, int var3, int var4, int var5) {
      this.resetStyles();
      this.textColor = var5;
      var1 = this.trimStringNewline(var1);
      this.renderSplitString(var1, var2, var3, var4, false);
   }

   private void renderSplitString(String var1, int var2, int var3, int var4, boolean var5) {
      for(Object var7 : this.listFormattedStringToWidth(var1, var4)) {
         this.renderStringAligned((String)var7, var2, var3, var4, this.textColor, var5);
         var3 += this.FONT_HEIGHT;
      }

   }

   public int splitStringWidth(String var1, int var2) {
      return this.FONT_HEIGHT * this.listFormattedStringToWidth(var1, var2).size();
   }

   public void setUnicodeFlag(boolean var1) {
      this.unicodeFlag = var1;
   }

   public boolean getUnicodeFlag() {
      return this.unicodeFlag;
   }

   public void setBidiFlag(boolean var1) {
      this.bidiFlag = var1;
   }

   public List listFormattedStringToWidth(String var1, int var2) {
      return Arrays.asList(this.wrapFormattedStringToWidth(var1, var2).split("\n"));
   }

   String wrapFormattedStringToWidth(String var1, int var2) {
      int var3 = this.sizeStringToWidth(var1, var2);
      if(var1.length() <= var3) {
         return var1;
      } else {
         String var4 = var1.substring(0, var3);
         char var5 = var1.charAt(var3);
         boolean var6 = var5 == 32 || var5 == 10;
         String var7 = getFormatFromString(var4) + var1.substring(var3 + 1);
         return var4 + "\n" + this.wrapFormattedStringToWidth(var7, var2);
      }
   }

   private int sizeStringToWidth(String var1, int var2) {
      int var3 = var1.length();
      float var10 = 0.0F;
      int var5 = 0;
      int var6 = -1;

      for(boolean var7 = false; var5 < var3; ++var5) {
         char var8 = var1.charAt(var5);
         switch(var8) {
         case '\n':
            --var5;
            break;
         case ' ':
            var6 = var5;
         default:
            var10 += this.getCharWidthFloat(var8);
            ++var10;
            break;
         case '§':
            if(var5 < var3 - 1) {
               ++var5;
               char var9 = var1.charAt(var5);
               if(var9 != 108 && var9 != 76) {
                  if(var9 == 114 || var9 == 82 || isFormatColor(var9)) {
                     var7 = false;
                  }
               } else {
                  var7 = true;
               }
            }
         }

         if(var8 == 10) {
            ++var5;
            var6 = var5;
            break;
         }

         if(var10 > (float)var2) {
            break;
         }
      }

      return var5 != var3 && var6 != -1 && var6 < var5?var6:var5;
   }

   private static boolean isFormatColor(char var0) {
      return var0 >= 48 && var0 <= 57 || var0 >= 97 && var0 <= 102 || var0 >= 65 && var0 <= 70;
   }

   private static boolean isFormatSpecial(char var0) {
      return var0 >= 107 && var0 <= 111 || var0 >= 75 && var0 <= 79 || var0 == 114 || var0 == 82;
   }

   public static String getFormatFromString(String var0) {
      String var1 = "";
      int var2 = -1;
      int var3 = var0.length();

      while((var2 = var0.indexOf(167, var2 + 1)) != -1) {
         if(var2 < var3 - 1) {
            char var4 = var0.charAt(var2 + 1);
            if(isFormatColor(var4)) {
               var1 = "§" + var4;
            } else if(isFormatSpecial(var4)) {
               var1 = var1 + "§" + var4;
            }
         }
      }

      return var1;
   }

   public boolean getBidiFlag() {
      return this.bidiFlag;
   }

   public int getColorCode(char var1) {
      int var2 = "0123456789abcdef".indexOf(var1);
      if(var2 < this.colorCode.length) {
         int var3 = this.colorCode[var2];
         if(Config.isCustomColors()) {
            var3 = CustomColors.a(var2, var3);
         }

         return var3;
      } else {
         return 16777215;
      }
   }

   public int getHeight() {
      return this.FONT_HEIGHT;
   }

   protected void setColor(float var1, float var2, float var3, float var4) {
      GlStateManager.color(var1, var2, var3, var4);
   }

   protected void enableAlpha() {
      GlStateManager.enableAlpha();
   }

   protected void bindTexture(ResourceLocation var1) {
      this.renderEngine.bindTexture(var1);
   }

   protected InputStream getResourceInputStream(ResourceLocation var1) throws IOException {
      return Minecraft.getInstance().getResourceManager().getResource(var1).getInputStream();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
