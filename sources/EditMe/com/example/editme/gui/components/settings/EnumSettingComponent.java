package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.gui.font.CFontRenderer;
import com.example.editme.settings.EnumSetting;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;
import java.awt.Font;

public class EnumSettingComponent extends Component {
   int index;
   private CFontRenderer enumCarouselFontRenderer = new CFontRenderer(new Font("Arial", 1, 10), true, true);
   String[] modes;
   int post;
   int pre;
   private final EnumSetting enumSetting;

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      boolean var4 = mouseWithinBounds(var1, var2, (double)this.getFinishedX(), (double)this.getFinishedY(), (double)this.getWidth(), (double)this.getHeight());
      if (var4) {
         double var5 = (double)this.getPosX() / (double)this.getWidth();
         if (var5 <= 0.5D) {
            this.increaseIndex(-1);
         } else {
            this.increaseIndex(1);
         }
      }

   }

   public void initialize() {
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());

      try {
         this.enumCarouselFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.getName()).append(": ").append(this.modes[this.index])), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(this.enumCarouselFontRenderer.getStringHeight(this.getName()) / 2)), -1);
      } catch (ArrayIndexOutOfBoundsException var5) {
         this.enumCarouselFontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.getName()).append(": ").append(this.modes[this.index - 1])), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(this.enumCarouselFontRenderer.getStringHeight(this.getName()) / 2)), -1);
      }

   }

   private void increaseIndex(int var1) {
      int var2 = this.index + var1;
      if (var2 < 0) {
         var2 = this.modes.length - Math.abs(var2);
      } else if (var2 >= this.modes.length) {
         var2 = Math.abs(var2 - this.modes.length);
      }

      this.index = Math.min(this.modes.length, Math.max(0, var2));
      this.getEnumSetting().setValueFromString(this.modes[this.index]);
   }

   public EnumSettingComponent(EnumSetting var1, String[] var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      super(var1.getName(), var3, var4, var5, var6, var7, var8);
      this.enumSetting = var1;
      this.modes = var2;
      this.index = 0;
      String[] var9 = var2;
      int var10 = var2.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         String var12 = var9[var11];
         if (var12.equals(var1.getValueAsString().replace("\"", ""))) {
            break;
         }

         ++this.index;
      }

   }

   public EnumSetting getEnumSetting() {
      return this.enumSetting;
   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
   }
}
