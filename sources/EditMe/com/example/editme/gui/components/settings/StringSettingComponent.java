package com.example.editme.gui.components.settings;

import com.example.editme.gui.components.Component;
import com.example.editme.settings.StringSetting;
import com.example.editme.util.render.RenderUtil;
import java.awt.Color;

public class StringSettingComponent extends Component {
   private final StringSetting stringSetting;

   public void initialize() {
   }

   public void onMove(float var1, float var2) {
      super.onMove(var1, var2);
   }

   public StringSetting getStringSetting() {
      return this.stringSetting;
   }

   public StringSettingComponent(StringSetting var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      super(var1.getName(), var2, var3, var4, var5, var6, var7);
      this.stringSetting = var1;
   }

   public void drawComponent(int var1, int var2, float var3) {
      super.drawComponent(var1, var2, var3);
      RenderUtil.drawRect(this.getFinishedX(), this.getFinishedY(), this.getWidth(), this.getHeight(), (new Color(50, 50, 50, 200)).getRGB());
      fontRenderer.drawStringWithShadow(String.valueOf((new StringBuilder()).append(this.getName()).append(": ").append((String)this.getStringSetting().getValue())), (double)(this.getFinishedX() + 5.0F), (double)(this.getFinishedY() + this.getHeight() / 2.0F - (float)(fontRenderer.getStringHeight(this.getName()) / 2)), -1);
   }
}
