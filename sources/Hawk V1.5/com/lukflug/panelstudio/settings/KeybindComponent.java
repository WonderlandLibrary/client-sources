package com.lukflug.panelstudio.settings;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.FocusableComponent;
import com.lukflug.panelstudio.theme.Renderer;

public class KeybindComponent extends FocusableComponent {
   protected KeybindSetting keybind;

   public void exit(Context var1) {
      super.exit(var1);
      if (this.hasFocus(var1)) {
         this.keybind.setKey(0);
         this.releaseFocus();
      }

   }

   public void handleKey(Context var1, int var2) {
      super.handleKey(var1, var2);
      if (this.hasFocus(var1)) {
         this.keybind.setKey(var2);
         this.releaseFocus();
      }

   }

   public void render(Context var1) {
      super.render(var1);
      String var2 = String.valueOf((new StringBuilder(String.valueOf(this.title))).append(this.keybind.getKeyName()));
      if (this.hasFocus(var1)) {
         var2 = String.valueOf((new StringBuilder(String.valueOf(this.title))).append("..."));
      }

      this.renderer.renderTitle(var1, var2, this.hasFocus(var1));
   }

   public void handleButton(Context var1, int var2) {
      super.handleButton(var1, var2);
      var1.setHeight(this.renderer.getHeight(false));
      boolean var3 = this.hasFocus(var1);
      super.handleButton(var1, var2);
      if (var3 && !this.hasFocus(var1)) {
         this.keybind.setKey(0);
      }

   }

   public KeybindComponent(Renderer var1, KeybindSetting var2) {
      super("Keybind: §7", (String)null, var1);
      this.keybind = var2;
   }
}
