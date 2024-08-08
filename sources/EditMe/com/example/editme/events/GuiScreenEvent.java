package com.example.editme.events;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenEvent {
   private GuiScreen screen;

   public void setScreen(GuiScreen var1) {
      this.screen = var1;
   }

   public GuiScreen getScreen() {
      return this.screen;
   }

   public GuiScreenEvent(GuiScreen var1) {
      this.screen = var1;
   }

   public static class Displayed extends GuiScreenEvent {
      public Displayed(GuiScreen var1) {
         super(var1);
      }
   }

   public static class Closed extends GuiScreenEvent {
      public Closed(GuiScreen var1) {
         super(var1);
      }
   }
}
