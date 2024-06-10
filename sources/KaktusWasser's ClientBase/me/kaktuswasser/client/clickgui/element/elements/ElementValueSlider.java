// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.clickgui.element.elements;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.clickgui.element.Element;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.TimeHelper;
import me.kaktuswasser.client.utilities.NahrFont.FontType;
import me.kaktuswasser.client.values.ConstrainedValue;

public class ElementValueSlider extends Element {
   protected ConstrainedValue value;
   protected boolean slide;
   protected boolean reset = true;
   protected final TimeHelper time = new TimeHelper();
   protected float textWidth;
   protected String name;
   protected int slideX;
   protected int slideX2;
   protected int maxSlide;
   protected int minSlide;

   public ElementValueSlider(ConstrainedValue value) {
      Client.getFileManager().getFileByName("elementsliderconfiguration").loadFile();
      this.name = value.getName();
      this.value = value;
      this.slide = false;
   }

   public String getName() {
      return this.name;
   }

   public ConstrainedValue getValue() {
      return this.value;
   }

   public int getMaxSlide() {
      return this.maxSlide;
   }

   public void setMaxSlide(int maxSlide) {
      this.maxSlide = maxSlide;
   }

   public int getMinSlide() {
      return this.minSlide;
   }

   public void setMinSlide(int minSlide) {
      this.minSlide = minSlide;
   }

   public void setValue(ConstrainedValue value) {
      this.value = value;
   }

   public boolean isSliding() {
      return this.slide;
   }

   public void setSliding(boolean slide) {
      this.slide = slide;
   }

   public int getSlideX() {
      return this.slideX;
   }

   public void setSlideX(int slideX) {
      this.slideX = slideX;
   }

   public void slide(int mouseX, int mouseY) {
      if(this.slide) {
         this.slideX = this.slideX2 + mouseX;
         if(this.slideX > this.maxSlide) {
            this.slideX = this.maxSlide;
         }

         if(this.slideX < this.minSlide) {
            this.slideX = this.minSlide;
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if(mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
         this.slideX2 = this.slideX - mouseX;
         this.slide = true;
      }

   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      if(state == 0) {
         this.slide = false;
         Client.getFileManager().getFileByName("elementsliderconfiguration").saveFile();
      }

   }

   public void drawScreen(int mouseX, int mouseY, float button) {
      this.slide(mouseX, mouseY);
      if(this.reset) {
         this.time.reset();
         this.reset = false;
      }

      this.handleValue(this.value);
      RenderHelper.drawBorderedCorneredRect((float)this.getX(), (float)(this.getY() + 4), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.getHeight()), 1.0F, Integer.MIN_VALUE, 0x1071f442);
      if(this.isHovering(mouseX, mouseY)) {
         RenderHelper.drawRect((float)this.getX(), (float)(this.getY() + 4), (float)(this.getX() + this.getWidth()), (float)(this.getY() + this.getHeight()), 0x3071f442);
      }

      RenderHelper.drawRect((float)this.getX(), (float)(this.getY() + 4), (float)(this.getX() + this.getSlideX()), (float)(this.getY() + this.getHeight()), 0x9971f442);
      String text = RenderHelper.getPrettyName(this.value.getName().replace(this.value.getModule().getName().toLowerCase(), "").replace("_", ""), " ") + ": " + this.value.getValue();
      this.textWidth = RenderHelper.getNahrFont().getStringWidth(text);
      RenderHelper.getNahrFont().drawString(text, (float)(this.getX() + this.getWidth() / 2) - RenderHelper.getNahrFont().getStringWidth(text) / 2.0F, (float)(this.getY() + this.getHeight() / 4 - 2), FontType.SHADOW_THIN, -1, -16777216);
   }

   public boolean isHovering(int mouseX, int mouseY) {
      return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 4 && mouseY <= this.getY() + this.getHeight();
   }

   public float getTextWidth() {
      return this.textWidth;
   }

   public void setTextWidth(float textWidth) {
      this.textWidth = textWidth;
   }

   public void handleValue(ConstrainedValue v) {
      try {
         double e;
         double curValue;
         DecimalFormat f;
         NumberFormat n = NumberFormat.getInstance();
         n.setMaximumFractionDigits(2);
         if(v.getValue() instanceof Float && v.getMax() instanceof Float) {
            e = (double)((float)this.slideX / (Float.valueOf((float)this.getWidth()).floatValue() / 100.0F));
            float max3 = ((Float)v.getMax()).floatValue();
            curValue = (double)max3 * (e / 100.0D);
            f = new DecimalFormat(".0");
            n.format(curValue);
            v.setValue(Float.valueOf((new Double(curValue)).floatValue()));
         } else {
            double curValue1;
            DecimalFormat f1;
            if(v.getValue() instanceof Long && v.getMax() instanceof Long) {
               e = (double)((float)this.slideX / (Float.valueOf((float)this.getWidth()).floatValue() / 100.0F));
               long max2 = ((Long)v.getMax()).longValue();
               curValue1 = (double)max2 * (e / 100.0D);
               f1 = new DecimalFormat(".0");
               n.format(curValue1);
               v.setValue(Long.valueOf((new Double(curValue1)).longValue()));
            } else if(v.getValue() instanceof Integer && v.getMax() instanceof Integer) {
               e = (double)((float)this.slideX / (Float.valueOf((float)this.getWidth()).floatValue() / 100.0F));
               int max1 = ((Integer)v.getMax()).intValue();
               curValue = (double)max1 * (e / 100.0D);
               f = new DecimalFormat("");
               n.format(curValue);
               v.setValue(Integer.valueOf((new Double(curValue)).intValue()));
            } else if(v.getValue() instanceof Double && v.getMax() instanceof Double) {
               e = (double)((float)this.slideX / (Float.valueOf((float)this.getWidth()).floatValue() / 100.0F));
               double max = ((Double)v.getMax()).doubleValue();
               curValue1 = max * (e / 100.0D);
               f1 = new DecimalFormat(".0");
               n.format(curValue1);
               v.setValue(Double.valueOf((new Double(curValue1)).doubleValue()));
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }
}
