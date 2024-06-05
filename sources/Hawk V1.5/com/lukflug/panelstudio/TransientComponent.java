package com.lukflug.panelstudio;

import com.lukflug.panelstudio.settings.Toggleable;
import com.lukflug.panelstudio.theme.Renderer;

public class TransientComponent extends FocusableComponent {
   protected FixedComponent component;
   protected PanelManager manager;
   protected Toggleable toggle;

   public TransientComponent(String var1, String var2, Renderer var3, Toggleable var4, FixedComponent var5, PanelManager var6) {
      super(var1, var2, var3);
      this.toggle = var4;
      this.component = var5;
      this.manager = var6;
   }

   public void handleButton(Context var1, int var2) {
      super.handleButton(var1, var2);
      if (var2 == 0 && var1.isClicked()) {
         this.toggle.toggle();
      } else if (var1.isHovered() && var2 == 1 && var1.getInterface().getButton(1)) {
         this.manager.getComponentToggleable(this.component).toggle();
         var1.releaseFocus();
      }

   }

   public void render(Context var1) {
      super.render(var1);
      this.renderer.renderTitle(var1, this.title, this.hasFocus(var1), this.toggle.isOn(), this.manager.getComponentToggleable(this.component).isOn());
   }
}
