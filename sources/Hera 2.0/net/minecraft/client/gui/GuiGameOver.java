/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGameOver
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/*     */   private int enableButtonsTimer;
/*     */   private boolean field_146346_f = false;
/*     */   
/*     */   public void initGui() {
/*  23 */     this.buttonList.clear();
/*     */     
/*  25 */     if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */       
/*  27 */       if (this.mc.isIntegratedServerRunning())
/*     */       {
/*  29 */         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
/*     */       }
/*     */       else
/*     */       {
/*  33 */         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  38 */       this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
/*  39 */       this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
/*     */       
/*  41 */       if (this.mc.getSession() == null)
/*     */       {
/*  43 */         ((GuiButton)this.buttonList.get(1)).enabled = false;
/*     */       }
/*     */     } 
/*     */     
/*  47 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/*  49 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     GuiYesNo guiyesno;
/*  66 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  69 */         this.mc.thePlayer.respawnPlayer();
/*  70 */         this.mc.displayGuiScreen(null);
/*     */         break;
/*     */       
/*     */       case 1:
/*  74 */         if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */           
/*  76 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*     */           
/*     */           break;
/*     */         } 
/*  80 */         guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
/*  81 */         this.mc.displayGuiScreen(guiyesno);
/*  82 */         guiyesno.setButtonDelay(20);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  89 */     if (result) {
/*     */       
/*  91 */       this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  92 */       this.mc.loadWorld(null);
/*  93 */       this.mc.displayGuiScreen(new GuiMainMenu());
/*     */     }
/*     */     else {
/*     */       
/*  97 */       this.mc.thePlayer.respawnPlayer();
/*  98 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 107 */     drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
/* 108 */     GlStateManager.pushMatrix();
/* 109 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 110 */     boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
/* 111 */     String s = flag ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
/* 112 */     drawCenteredString(this.fontRendererObj, s, this.width / 2 / 2, 30, 16777215);
/* 113 */     GlStateManager.popMatrix();
/*     */     
/* 115 */     if (flag)
/*     */     {
/* 117 */       drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), this.width / 2, 144, 16777215);
/*     */     }
/*     */     
/* 120 */     drawCenteredString(this.fontRendererObj, String.valueOf(I18n.format("deathScreen.score", new Object[0])) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, 100, 16777215);
/* 121 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 137 */     super.updateScreen();
/* 138 */     this.enableButtonsTimer++;
/*     */     
/* 140 */     if (this.enableButtonsTimer == 20)
/*     */     {
/* 142 */       for (GuiButton guibutton : this.buttonList)
/*     */       {
/* 144 */         guibutton.enabled = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiGameOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */