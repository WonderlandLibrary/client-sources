/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.gui.GuiChangeName;
/*     */ import me.eagler.gui.GuiConfigs;
/*     */ import me.eagler.gui.GuiFriends;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.realms.RealmsBridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiIngameMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   private int field_146445_a;
/*     */   private int field_146444_f;
/*     */   
/*     */   public void initGui() {
/*  29 */     this.field_146445_a = 0;
/*  30 */     this.buttonList.clear();
/*  31 */     int i = -16;
/*  32 */     int j = 98;
/*  33 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, 200, 20, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     
/*  35 */     if (!this.mc.isIntegratedServerRunning())
/*     */     {
/*  37 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     }
/*     */     
/*  40 */     this.buttonList.add(new HeraButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, 200, 20, I18n.format("menu.returnToGame", new Object[0])));
/*  41 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
/*     */     HeraButton heraButton;
/*  43 */     this.buttonList.add(heraButton = new HeraButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/*  44 */     this.buttonList.add(new HeraButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
/*  45 */     this.buttonList.add(new HeraButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
/*  46 */     this.buttonList.add(new HeraButton(22, 2, this.height - 22, 80, 20, "Name"));
/*  47 */     this.buttonList.add(new HeraButton(33, this.width - 82, this.height - 22, 80, 20, "Friends"));
/*  48 */     this.buttonList.add(new HeraButton(44, 2, 2, 80, 20, "Config"));
/*  49 */     ((GuiButton)heraButton).enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     boolean flag, flag1;
/*  57 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  60 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/*  64 */         flag = this.mc.isIntegratedServerRunning();
/*  65 */         flag1 = this.mc.func_181540_al();
/*  66 */         button.enabled = false;
/*  67 */         this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  68 */         this.mc.loadWorld(null);
/*     */         
/*  70 */         if (flag) {
/*     */           
/*  72 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*     */         }
/*  74 */         else if (flag1) {
/*     */           
/*  76 */           RealmsBridge realmsbridge = new RealmsBridge();
/*  77 */           realmsbridge.switchToRealms(new GuiMainMenu());
/*     */         }
/*     */         else {
/*     */           
/*  81 */           this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
/*     */         } 
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/*  90 */         this.mc.displayGuiScreen(null);
/*  91 */         this.mc.setIngameFocus();
/*     */ 
/*     */       
/*     */       case 5:
/*  95 */         this.mc.displayGuiScreen((GuiScreen)new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 6:
/*  99 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 22:
/* 103 */         this.mc.displayGuiScreen((GuiScreen)new GuiChangeName(this, true));
/*     */ 
/*     */       
/*     */       case 33:
/* 107 */         this.mc.displayGuiScreen((GuiScreen)new GuiFriends(this, true));
/*     */ 
/*     */       
/*     */       case 44:
/* 111 */         this.mc.displayGuiScreen((GuiScreen)new GuiConfigs(this, true));
/*     */       case 7:
/*     */         break;
/*     */     } 
/* 115 */     this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 124 */     super.updateScreen();
/* 125 */     this.field_146444_f++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 133 */     drawDefaultBackground();
/* 134 */     drawCenteredString(this.fontRendererObj, "Hera v" + Client.instance.VERSION, this.width / 2, 60, 16777215);
/* 135 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiIngameMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */