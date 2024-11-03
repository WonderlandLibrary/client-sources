package net.augustus.utils;

import java.awt.Color;
import java.util.function.Consumer;
import net.augustus.utils.interfaces.MC;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

public class ColorPicker implements MC {
   private final double radius;
   private final Consumer<Color> color;
   private double selectedX;
   private double selectedY;
   private float brightness = 1.0F;
   private float alpha = 1.0F;
   private ScaledResolution sr;

   public ColorPicker(double radius, Color selectedColor, Consumer<Color> color) {
      this.radius = radius;
      this.alpha = (float)selectedColor.getAlpha() / 255.0F;
      this.brightness = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null)[2];
      this.setColor(selectedColor);
      this.color = color;
   }

   public void click(double mouseX, double mouseY, int button) {
      this.sr = new ScaledResolution(mc);
      if (button == 0 && this.isPointInCircle((double)this.sr.getScaledWidth() / 2.0, (double)this.sr.getScaledHeight() / 2.0, this.radius, mouseX, mouseY)) {
         this.selectedX = mouseX - (double)this.sr.getScaledWidth() / 2.0;
         this.selectedY = mouseY - (double)this.sr.getScaledHeight() / 2.0;
      }

      int width = this.sr.getScaledWidth() / 2;
      int height = this.sr.getScaledHeight() / 2;
      double sliderX = (double)(width - 70);
      double sliderWidth = 138.0;
      if (button == 0 && this.mouseOver(mouseX, mouseY, sliderX, (double)(height + 75), 140.0, 20.0)) {
         double var1 = MathHelper.clamp_double((mouseX - sliderX) / sliderWidth, 0.0, 1.0) * 1.0;
         this.setBrightness((float)var1);
      }

      if (button == 0 && this.mouseOver(mouseX, mouseY, sliderX, (double)(height + 100), 140.0, 20.0)) {
         double var1 = MathHelper.clamp_double((mouseX - sliderX) / sliderWidth, 0.0, 1.0) * 1.0;
         this.setAlpha((float)var1);
      }
   }

   public void draw(int mouseX, int mouseY) {
      this.sr = new ScaledResolution(mc);
      FontRenderer fr = mc.fontRendererObj;
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      RenderUtil.drawColoredCircle((double)this.sr.getScaledWidth() / 2.0, (double)this.sr.getScaledHeight() / 2.0, this.radius, this.getBrightness());
      RenderUtil.drawCircle((double)this.sr.getScaledWidth() / 2.0 + this.selectedX, (double)this.sr.getScaledHeight() / 2.0 + this.selectedY, 2.5, -15592942);
      GlStateManager.disableBlend();
      GlStateManager.enableTexture2D();
      int width = this.sr.getScaledWidth() / 2;
      int height = this.sr.getScaledHeight() / 2;
      double percentageOfCurrentValue = (double)this.getBrightness();
      double sliderX = (double)(width - 70);
      double sliderWidth = 138.0;
      double value1 = sliderX + 2.0 + percentageOfCurrentValue * sliderWidth;
      RenderUtil.drawRect(width - 70, height + 75, width + 70, height + 90, this.getColor().getRGB());
      RenderUtil.drawRect((int)(value1 - 2.0), height + 75, (int)value1, height + 90, Color.black.getRGB());
      double percentageOfCurrentValue2 = (double)this.getAlpha();
      double value12 = sliderX + 2.0 + percentageOfCurrentValue2 * sliderWidth;
      RenderUtil.drawRect(width - 70, height + 100, width + 70, height + 115, this.getColor().getRGB());
      RenderUtil.drawRect((int)(value12 - 2.0), height + 100, (int)value12, height + 115, Color.black.getRGB());
      this.color.accept(this.getColor());
   }

   private float getNormalized() {
      return (float)((-Math.toDegrees(Math.atan2(this.selectedY, this.selectedX)) + 450.0) % 360.0) / 360.0F;
   }

   private Color getColor() {
      Color color1 = Color.getHSBColor(this.getNormalized(), (float)(Math.hypot(this.selectedX, this.selectedY) / this.radius), this.getBrightness());
      return new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), (int)(this.getAlpha() * 255.0F));
   }

   private void setColor(Color selectedColor) {
      float[] hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
      this.selectedX = (double)hsb[1] * this.radius * (Math.sin(Math.toRadians((double)(hsb[0] * 360.0F))) / Math.sin(Math.toRadians(90.0)));
      this.selectedY = (double)hsb[1] * this.radius * (Math.sin(Math.toRadians((double)(90.0F - hsb[0] * 360.0F))) / Math.sin(Math.toRadians(90.0)));
   }

   private boolean isPointInCircle(double x, double y, double radius, double pX, double pY) {
      return (pX - x) * (pX - x) + (pY - y) * (pY - y) <= radius * radius;
   }

   public boolean mouseOver(double mouseX, double mouseY, double posX, double posY, double width, double height) {
      return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
   }

   public float getBrightness() {
      return this.brightness;
   }

   public void setBrightness(float brightness) {
      this.brightness = brightness;
   }

   public float getAlpha() {
      return this.alpha;
   }

   public void setAlpha(float alpha) {
      this.alpha = alpha;
   }
}
