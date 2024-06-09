/*    */ package me.eagler.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.io.IOException;
/*    */ import me.eagler.clickgui.Colors;
/*    */ import me.eagler.font.FontHelper;
/*    */ import me.eagler.gui.stuff.HeraButton;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ public class GuiConfigs
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen oldScreen;
/*    */   private boolean noBackground;
/*    */   
/*    */   public GuiConfigs(GuiScreen oldScreen, boolean noBackground) {
/* 19 */     this.oldScreen = oldScreen;
/* 20 */     this.noBackground = noBackground;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 27 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 40, this.height / 2 + 80, 80, 15, "Back"));
/*    */     
/* 29 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 80, this.height / 2 - 65, 77, 15, "CubeCraft"));
/* 30 */     this.buttonList.add(new HeraButton(2, this.width / 2 + 3, this.height / 2 - 65, 77, 15, "Gomme"));
/*    */     
/* 32 */     this.buttonList.add(new HeraButton(3, this.width / 2 - 80, this.height / 2 - 45, 77, 15, "GommeQSG"));
/* 33 */     this.buttonList.add(new HeraButton(4, this.width / 2 + 3, this.height / 2 - 45, 77, 15, "Hive"));
/*    */     
/* 35 */     super.initGui();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 40 */     if (button.id == 0)
/*    */     {
/* 42 */       this.mc.displayGuiScreen(this.oldScreen);
/*    */     }
/*    */ 
/*    */     
/* 46 */     if (button.id == 1) {
/*    */       
/* 48 */       this.mc.thePlayer.sendChatMessage(".config load CubeCraft");
/* 49 */       this.mc.thePlayer.closeScreen();
/*    */     } 
/*    */ 
/*    */     
/* 53 */     if (button.id == 2) {
/*    */       
/* 55 */       this.mc.thePlayer.sendChatMessage(".config load Gomme");
/* 56 */       this.mc.thePlayer.closeScreen();
/*    */     } 
/*    */ 
/*    */     
/* 60 */     if (button.id == 3) {
/*    */       
/* 62 */       this.mc.thePlayer.sendChatMessage(".config load GommeQSG");
/* 63 */       this.mc.thePlayer.closeScreen();
/*    */     } 
/*    */ 
/*    */     
/* 67 */     if (button.id == 4) {
/*    */       
/* 69 */       this.mc.thePlayer.sendChatMessage(".config load Hive");
/* 70 */       this.mc.thePlayer.closeScreen();
/*    */     } 
/*    */ 
/*    */     
/* 74 */     super.actionPerformed(button);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 79 */     if (!this.noBackground)
/*    */     {
/* 81 */       drawBackground();
/*    */     }
/*    */ 
/*    */     
/* 85 */     drawRectWithEdge((this.width / 2 - 85), (this.height / 2 - 100), 170.0D, 200.0D, Colors.getPrimary());
/*    */     
/* 87 */     FontHelper.cfArrayList.drawStringWithBG("Configs:", (this.width / 2 - 
/* 88 */         FontHelper.cfArrayList.getStringWidth("Configs:") / 2), (this.height / 2 - 95), Color.white);
/*    */     
/* 90 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\GuiConfigs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */