package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.settings.NumberSetting;
import com.example.editme.util.client.MathUtil;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;

public class NumberSettingComponent extends Component {
   private double tempValue;
   private final NumberSetting numberSetting;
   private boolean sliding;

   public void setSliding(boolean var1) {
      this.sliding = var1;
   }

   public NumberSetting getNumberSetting() {
      return this.numberSetting;
   }

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
      if (this.isSliding()) {
         this.setSliding(false);
      }

   }

   public void initialize() {
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public boolean isSliding() {
      return this.sliding;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      if (mouseWithinBounds(var1, var2, (double)this.getFinishedX(), (double)this.getFinishedY(), (double)this.getWidth(), (double)this.getHeight()) && var3 == 0) {
         this.setSliding(true);
      }

   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.getName()).append(": ").append(this.getNumberSetting().getValue())), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), -1);
      if (this.sliding) {
         if ((double)var1 >= (double)this.getPosX() + (double)this.getWidth() * 0.5D) {
            this.tempValue = (double)(((float)var1 - this.getPosX() - this.getWidth() / 2.0F) / this.getWidth()) * 0.5D;
            if (this.getNumberSetting().getValue() instanceof Float) {
               this.getNumberSetting().setValue(MathUtil.roundFloat(this.getNumberSetting().getValue().floatValue() + (float)this.tempValue, 2));
            } else if (this.getNumberSetting().getValue() instanceof Integer) {
               this.getNumberSetting().setValue(this.getNumberSetting().getValue().intValue() + 1);
            } else if (this.getNumberSetting().getValue() instanceof Double) {
               this.getNumberSetting().setValue(MathUtil.roundDouble(this.getNumberSetting().getValue().doubleValue() + this.tempValue, 2));
            } else if (this.getNumberSetting().getValue() instanceof Long) {
               this.getNumberSetting().setValue(this.getNumberSetting().getValue().intValue() + 1);
            }
         } else {
            this.tempValue = reverseNumber((double)((float)var1 - this.getPosX()), (double)this.getPosX(), (double)(this.getPosX() + this.getWidth() / 2.0F)) * 0.1D;
            if (this.getNumberSetting().getValue() instanceof Float) {
               this.getNumberSetting().setValue(MathUtil.roundFloat(this.getNumberSetting().getValue().floatValue() - 1.0F, 2));
            } else if (this.getNumberSetting().getValue() instanceof Integer) {
               this.getNumberSetting().setValue(this.getNumberSetting().getValue().intValue() - 1);
            } else if (this.getNumberSetting().getValue() instanceof Double) {
               this.getNumberSetting().setValue(MathUtil.roundDouble(this.getNumberSetting().getValue().doubleValue() - 1.0D, 2));
            } else if (this.getNumberSetting().getValue() instanceof Long) {
               this.getNumberSetting().setValue(this.getNumberSetting().getValue().intValue() - 1);
            }
         }
      }

   }

   public NumberSettingComponent(NumberSetting var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.numberSetting = var1;
   }

   private static double reverseNumber(double var0, double var2, double var4) {
      return var4 + var2 - var0;
   }
}
