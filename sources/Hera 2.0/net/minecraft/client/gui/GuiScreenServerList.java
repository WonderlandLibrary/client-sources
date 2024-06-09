/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ public class GuiScreenServerList
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146303_a;
/*     */   private final ServerData field_146301_f;
/*     */   private GuiTextField field_146302_g;
/*     */   
/*     */   public GuiScreenServerList(GuiScreen p_i1031_1_, ServerData p_i1031_2_) {
/*  18 */     this.field_146303_a = p_i1031_1_;
/*  19 */     this.field_146301_f = p_i1031_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  27 */     this.field_146302_g.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     Keyboard.enableRepeatEvents(true);
/*  37 */     this.buttonList.clear();
/*  38 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20, I18n.format("selectServer.select", new Object[0])));
/*  39 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, I18n.format("gui.cancel", new Object[0])));
/*  40 */     this.field_146302_g = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
/*  41 */     this.field_146302_g.setMaxStringLength(128);
/*  42 */     this.field_146302_g.setFocused(true);
/*  43 */     this.field_146302_g.setText(this.mc.gameSettings.lastServer);
/*  44 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146302_g.getText().length() > 0 && (this.field_146302_g.getText().split(":")).length > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  52 */     Keyboard.enableRepeatEvents(false);
/*  53 */     this.mc.gameSettings.lastServer = this.field_146302_g.getText();
/*  54 */     this.mc.gameSettings.saveOptions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  62 */     if (button.enabled)
/*     */     {
/*  64 */       if (button.id == 1) {
/*     */         
/*  66 */         this.field_146303_a.confirmClicked(false, 0);
/*     */       }
/*  68 */       else if (button.id == 0) {
/*     */         
/*  70 */         this.field_146301_f.serverIP = this.field_146302_g.getText();
/*  71 */         this.field_146303_a.confirmClicked(true, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  82 */     if (this.field_146302_g.textboxKeyTyped(typedChar, keyCode)) {
/*     */       
/*  84 */       ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146302_g.getText().length() > 0 && (this.field_146302_g.getText().split(":")).length > 0);
/*     */     }
/*  86 */     else if (keyCode == 28 || keyCode == 156) {
/*     */       
/*  88 */       actionPerformed(this.buttonList.get(0));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  97 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  98 */     this.field_146302_g.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 106 */     drawDefaultBackground();
/* 107 */     drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
/* 108 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100, 10526880);
/* 109 */     this.field_146302_g.drawTextBox();
/* 110 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenServerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */