package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.settings.NumberSetting;
import com.example.editme.util.client.MathUtil;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.util.math.MathHelper;

public class SliderSettingComponent extends Component {
   private boolean sliding;
   private final NumberSetting numberSetting;

   public void setSliding(boolean var1) {
      this.sliding = var1;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      float var4 = (float)MathHelper.func_76141_d((this.getNumberSetting().getValue().floatValue() - this.getNumberSetting().getMin().floatValue()) / (this.getNumberSetting().getMax().floatValue() - this.getNumberSetting().getMin().floatValue()) * (this.getWidth() - 10.0F));
      if (mouseWithinBounds(var1, var2, (double)(this.getFinishedX() + 4.0F), (double)(this.getFinishedY() + 2.0F), (double)var4, (double)(this.getHeight() - 4.0F)) && var3 == 0) {
         this.setSliding(true);
      }

   }

   public SliderSettingComponent(NumberSetting var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.numberSetting = var1;
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
      if (this.isSliding()) {
         this.setSliding(false);
      }

   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public boolean isSliding() {
      return this.sliding;
   }

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
   }

   public void initialize() {
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      float var4 = (float)MathHelper.func_76141_d((this.getNumberSetting().getValue().floatValue() - this.getNumberSetting().getMin().floatValue()) / (this.getNumberSetting().getMax().floatValue() - this.getNumberSetting().getMin().floatValue()) * (this.getWidth() - 10.0F));
      RenderUtil.drawRect(this.getFinishedX() + 5.0F, this.getFinishedY() + 2.0F, var4, this.getHeight() - 4.0F, (new Color(0, 102, 204, 204)).getRGB());
      fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.getName()).append(": ").append(this.getNumberSetting().getValue())), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), -1);
      if (this.sliding) {
         if (this.getNumberSetting().getValue() instanceof Float) {
            float var5 = ((float)var1 - (this.getFinishedX() + 4.0F)) * (this.getNumberSetting().getMax().floatValue() - this.getNumberSetting().getMin().floatValue()) / (this.getWidth() - 10.0F) + this.getNumberSetting().getMin().floatValue();
            this.getNumberSetting().setValue(MathUtil.roundFloat(var5, 2));
         } else if (this.getNumberSetting().getValue() instanceof Integer) {
            int var7 = (int)(((float)var1 - (this.getFinishedX() + 4.0F)) * (float)(this.getNumberSetting().getMax().intValue() - this.getNumberSetting().getMin().intValue()) / (this.getWidth() - 10.0F) + (float)this.getNumberSetting().getMin().intValue());
            this.getNumberSetting().setValue(var7);
         } else if (this.getNumberSetting().getValue() instanceof Double) {
            double var8 = (double)((float)var1 - (this.getFinishedX() + 4.0F)) * (this.getNumberSetting().getMax().doubleValue() - this.getNumberSetting().getMin().doubleValue()) / (double)(this.getWidth() - 10.0F) + this.getNumberSetting().getMin().doubleValue();
            this.getNumberSetting().setValue(MathUtil.roundDouble(var8, 2));
         } else if (this.getNumberSetting().getValue() instanceof Long) {
            long var9 = (long)((double)((float)var1 - (this.getFinishedX() + 4.0F)) * (this.getNumberSetting().getMax().doubleValue() - this.getNumberSetting().getMin().doubleValue()) / (double)(this.getWidth() - 10.0F) + this.getNumberSetting().getMin().doubleValue());
            this.getNumberSetting().setValue(MathUtil.roundDouble((double)var9, 2));
         }
      }

   }

   public NumberSetting getNumberSetting() {
      return this.numberSetting;
   }

   private static double reverseNumber(double var0, double var2, double var4) {
      return var4 + var2 - var0;
   }
}
