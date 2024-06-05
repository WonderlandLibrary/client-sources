package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
   private int field_146590_g;
   private static final String __OBFID = "CL_00000707";
   private boolean field_146592_h;
   private String field_146589_f = "";
   private String field_146591_a = "";

   public void setDoneWorking() {
      this.field_146592_h = true;
   }

   public void displaySavingString(String var1) {
      this.resetProgressAndMessage(var1);
   }

   public void resetProgressAndMessage(String var1) {
      this.field_146591_a = var1;
      this.displayLoadingString("Working...");
   }

   public void displayLoadingString(String var1) {
      this.field_146589_f = var1;
      this.setLoadingProgress(0);
   }

   public void setLoadingProgress(int var1) {
      this.field_146590_g = var1;
   }

   public void drawScreen(int var1, int var2, float var3) {
      if (this.field_146592_h) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else {
         this.drawDefaultBackground();
         this.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
         this.drawCenteredString(this.fontRendererObj, String.valueOf((new StringBuilder(String.valueOf(this.field_146589_f))).append(" ").append(this.field_146590_g).append("%")), this.width / 2, 90, 16777215);
         super.drawScreen(var1, var2, var3);
      }

   }
}
