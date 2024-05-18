/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ 
/*    */ public class GuiScreenWorking
/*    */   extends GuiScreen implements IProgressUpdate {
/*  7 */   private String field_146591_a = "";
/*  8 */   private String field_146589_f = "";
/*    */ 
/*    */   
/*    */   private int progress;
/*    */   
/*    */   private boolean doneWorking;
/*    */ 
/*    */   
/*    */   public void displaySavingString(String message) {
/* 17 */     resetProgressAndMessage(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetProgressAndMessage(String message) {
/* 26 */     this.field_146591_a = message;
/* 27 */     displayLoadingString("Working...");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displayLoadingString(String message) {
/* 35 */     this.field_146589_f = message;
/* 36 */     setLoadingProgress(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLoadingProgress(int progress) {
/* 44 */     this.progress = progress;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDoneWorking() {
/* 49 */     this.doneWorking = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 57 */     if (this.doneWorking) {
/*    */       
/* 59 */       if (!this.mc.func_181540_al())
/*    */       {
/* 61 */         this.mc.displayGuiScreen(null);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 66 */       drawDefaultBackground();
/* 67 */       drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
/* 68 */       drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + " " + this.progress + "%", this.width / 2, 90, 16777215);
/* 69 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenWorking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */