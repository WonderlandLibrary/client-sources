package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.theme.Renderer;

public class EnumComponent extends FocusableComponent {
   protected EnumSetting setting;

   public EnumComponent(String var1, String var2, Renderer var3, EnumSetting var4) {
      super(var1, var2, var3);
      this.setting = var4;
   }

   public void handleButton(Context var1, int var2) {
      super.handleButton(var1, var2);
      if (var2 == 0 && var1.isClicked()) {
         this.setting.increment();
      }

   }

   public void render(Context var1) {
      super.render(var1);
      this.renderer.renderTitle(var1, String.valueOf((new StringBuilder(String.valueOf(this.title))).append(": §7").append(this.setting.getValueName())), this.hasFocus(var1));
   }
}
