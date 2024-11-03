package net.augustus.font;

import java.awt.Color;
import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontRenderer {
   private final UnicodeFont font;
   private int FONT_HEIGHT = 9;
   ScaledResolution sr;

   public UnicodeFontRenderer(Font awtFont) {
      HieroSettings hieroSettings = new HieroSettings();
      hieroSettings.setGlyphPageWidth(2048);
      hieroSettings.setGlyphPageHeight(2048);
      this.sr = new ScaledResolution(Minecraft.getMinecraft());
      hieroSettings.setFontSize((int)((float)awtFont.getSize() / 2.0F * (float)this.sr.getScaleFactor()));
      this.font = new UnicodeFont(awtFont, hieroSettings);
      this.font.addAsciiGlyphs();
      this.font.getEffects().add(new ColorEffect(Color.WHITE));

      try {
         this.font.loadGlyphs();
      } catch (SlickException var4) {
         throw new RuntimeException(var4);
      }

      this.FONT_HEIGHT = this.font.getHeight("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789") / this.sr.getScaleFactor();
   }

   public float drawString(String string, float x, float y, int color) {
      if (string == null) {
         return 0.0F;
      } else {
         GL11.glPushMatrix();
         GL11.glScaled(
            (double)(1.0F / (float)this.sr.getScaleFactor()),
            (double)(1.0F / (float)this.sr.getScaleFactor()),
            (double)(1.0F / (float)this.sr.getScaleFactor())
         );
         boolean blend = GL11.glIsEnabled(3042);
         boolean lighting = GL11.glIsEnabled(2896);
         boolean texture = GL11.glIsEnabled(3553);
         if (!blend) {
            GL11.glEnable(3042);
         }

         if (lighting) {
            GL11.glDisable(2896);
         }

         if (texture) {
            GL11.glDisable(3553);
         }

         x *= (float)this.sr.getScaleFactor();
         y *= (float)this.sr.getScaleFactor();
         this.font.drawString(x, y, string, new org.newdawn.slick.Color(color));
         if (texture) {
            GL11.glEnable(3553);
         }

         if (lighting) {
            GL11.glEnable(2896);
         }

         if (!blend) {
            GL11.glDisable(3042);
         }

         GlStateManager.color(0.0F, 0.0F, 0.0F);
         GL11.glPopMatrix();
         GlStateManager.bindTexture(0);
         return x;
      }
   }

   public float drawStringWithShadow(String text, float x, float y, int color) {
      this.drawString(text, x + 0.5F, y + 0.5F, -16777216);
      return this.drawString(text, x, y, color);
   }

   public int getCharWidth(char c) {
      return this.getStringWidth(Character.toString(c));
   }

   public int getStringWidth(String string) {
      return this.font.getWidth(string) / this.sr.getScaleFactor();
   }

   public int getStringHeight(String string) {
      return this.font.getHeight(string) / this.sr.getScaleFactor();
   }

   public void drawCenteredString(String text, float x, float y, int color) {
      this.drawString(text, x - (float)(this.getStringWidth(text) / 2), y, color);
   }
}
