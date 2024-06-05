package com.lukflug.panelstudio.mc8forge;

import com.lukflug.panelstudio.ClickGUI;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import net.minecraft.client.Minecraft;

public abstract class MinecraftHUDGUI extends MinecraftGUI {
   protected boolean hudEditor = false;

   public void handleKeyEvent(int var1) {
      if (var1 != 1 && !this.getHUDGUI().isOn() && !this.hudEditor) {
         this.getHUDGUI().handleKey(var1);
      }

   }

   public void enterHUDEditor() {
      this.hudEditor = true;
      if (this.getHUDGUI().isOn()) {
         this.getHUDGUI().toggle();
      }

      Minecraft.getMinecraft().displayGuiScreen(this);
   }

   protected abstract HUDClickGUI getHUDGUI();

   public void exitGUI() {
      this.hudEditor = false;
      super.exitGUI();
   }

   public void enterGUI() {
      this.hudEditor = false;
      super.enterGUI();
   }

   public void render() {
      if (!this.getHUDGUI().isOn() && !this.hudEditor) {
         this.renderGUI();
      }

   }

   protected ClickGUI getGUI() {
      return this.getHUDGUI();
   }
}
