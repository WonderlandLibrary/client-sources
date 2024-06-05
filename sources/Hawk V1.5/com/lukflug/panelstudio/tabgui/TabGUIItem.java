package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.settings.Toggleable;

public class TabGUIItem implements TabGUIComponent {
   protected String title;
   protected Toggleable toggle;

   public boolean isActive() {
      return this.toggle.isOn();
   }

   public boolean select() {
      this.toggle.toggle();
      return false;
   }

   public void releaseFocus() {
   }

   public void enter(Context var1) {
   }

   public void handleButton(Context var1, int var2) {
   }

   public void render(Context var1) {
   }

   public void getHeight(Context var1) {
   }

   public void handleScroll(Context var1, int var2) {
   }

   public void handleKey(Context var1, int var2) {
   }

   public TabGUIItem(String var1, Toggleable var2) {
      this.title = var1;
      this.toggle = var2;
   }

   public void exit(Context var1) {
   }

   public String getTitle() {
      return this.title;
   }
}
