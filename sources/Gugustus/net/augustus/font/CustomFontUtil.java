package net.augustus.font;

import java.awt.Font;
import net.augustus.events.EventChangeGuiScale;
import net.augustus.utils.interfaces.MC;
import net.lenni0451.eventapi.manager.EventManager;
import net.lenni0451.eventapi.reflection.EventTarget;

public class CustomFontUtil implements MC {
   private UnicodeFontRenderer fontRenderer;
   private final String fontName;
   private float fontHeight = 0.0F;
   private Font font;
   private int size;

   public CustomFontUtil(String FontName, int size) {
      this.fontName = FontName;
      this.size = size;
      this.font = new Font(FontName, 0, size);
      this.fontRenderer = new UnicodeFontRenderer(this.font);
      this.fontHeight = (float)this.fontRenderer.getStringHeight("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqurstuvwxyz1234567890");
      EventManager.register(this);
   }

   public CustomFontUtil(String fontName, Font font) {
      this.fontName = fontName;
      this.size = font.getSize();
      this.font = font;
      this.fontRenderer = new UnicodeFontRenderer(font);
      this.fontHeight = (float)this.fontRenderer.getStringHeight("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqurstuvwxyz1234567890");
      EventManager.register(this);
   }

   @EventTarget
   public void onEventChangeGuiScale(EventChangeGuiScale eventChangeGuiScale) {
      this.fontRenderer = new UnicodeFontRenderer(this.font);
      this.fontHeight = (float)this.fontRenderer.getStringHeight("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqurstuvwxyz1234567890");
   }

   public void drawString(String text, double x, double y, int color) {
      this.fontRenderer.drawString(text, (float)x, (float)y, color);
   }

   public void drawStringWithShadow(String text, double x, double y, int color) {
      this.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
   }

   public void drawStringXCenter(String text, double x, double y, double width, int color) {
      this.fontRenderer.drawString(text, (float)(x + width / 2.0 - (double)((float)this.fontRenderer.getStringWidth(text) / 2.0F)), (float)y, color);
   }

   public void drawStringYCenter(String text, double x, double y, double height, int color) {
      this.fontRenderer.drawString(text, (float)x, (float)(y + height / 2.0 - (double)((float)this.fontRenderer.getStringHeight(text) / 2.0F)), color);
   }

   public void drawStringYCenterMax(String text, double x, double y, double height, int color) {
      this.fontRenderer.drawString(text, (float)x, (float)(y + height / 2.0 - (double)((float)this.fontRenderer.getStringHeight("Dj") / 2.0F)), color);
   }

   public void drawStringTotalCenter(String text, double x, double y, double width, double height, int color) {
      this.fontRenderer
         .drawString(
            text,
            (float)(x + width / 2.0 - (double)((float)this.fontRenderer.getStringWidth(text) / 2.0F)),
            (float)(y + height / 2.0 - (double)((float)this.fontRenderer.getStringHeight(text) / 2.0F)),
            color
         );
   }

   public void drawStringTotalCenterWithShadow(String text, double x, double y, double width, double height, int color) {
      this.fontRenderer
         .drawStringWithShadow(
            text,
            (float)(x + width / 2.0 - (double)((float)this.fontRenderer.getStringWidth(text) / 2.0F)),
            (float)(y + height / 2.0 - (double)((float)this.fontRenderer.getStringHeight(text) / 2.0F)),
            color
         );
   }

   public float getStringWidth(String text) {
      return (float)this.fontRenderer.getStringWidth(text);
   }

   public float getHeight() {
      return this.fontHeight;
   }

   public float getHeight(String string) {
      return (float)this.fontRenderer.getStringHeight(string);
   }

   public String getFontName() {
      return this.fontName;
   }
}
