package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.theme.Renderer;

public class BooleanComponent extends FocusableComponent {
   protected Toggleable setting;

   public BooleanComponent(String var1, String var2, Renderer var3, Toggleable var4) {
      super(var1, var2, var3);
      this.setting = var4;
   }

   public void render(Context var1) {
      super.render(var1);
      this.renderer.renderTitle(var1, this.title, this.hasFocus(var1), this.setting.isOn());
   }

   public void handleButton(Context var1, int var2) {
      super.handleButton(var1, var2);
      if (var2 == 0 && var1.isClicked()) {
         this.setting.toggle();
      }

   }
}
