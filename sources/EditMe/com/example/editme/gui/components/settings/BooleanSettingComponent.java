package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.settings.BooleanSetting;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;

public class BooleanSettingComponent extends Component {
   private final BooleanSetting booleanSetting;

   public void onKeyTyped(char var1, int var2) {
      super.onKeyTyped(var1, var2);
   }

   public BooleanSetting getBooleanSetting() {
      return this.booleanSetting;
   }

   public void onMouseClicked(int var1, int var2, int var3) {
      super.onMouseClicked(var1, var2, var3);
      boolean var4 = mouseWithinBounds(var1, var2, (double)this.getFinishedX(), (double)this.getFinishedY(), (double)this.getWidth(), (double)this.getHeight());
      if (var4 && var3 == 0) {
         this.getBooleanSetting().setValue(!(Boolean)this.getBooleanSetting().getValue());
      }

   }

   public void onMouseReleased(int var1, int var2, int var3) {
      super.onMouseReleased(var1, var2, var3);
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public void initialize() {
   }

   public BooleanSettingComponent(BooleanSetting var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.booleanSetting = var1;
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      fontRenderer.drawStringWithShadow(this.getName(), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), (Boolean)this.getBooleanSetting().getValue() ? -1 : -5592406);
   }
}
