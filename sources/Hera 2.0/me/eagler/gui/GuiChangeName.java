/*     */ package me.eagler.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.clickgui.Colors;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ 
/*     */ public class GuiChangeName
/*     */   extends GuiScreen
/*     */ {
/*     */   private boolean nobackground = false;
/*     */   private GuiTextField nameField;
/*  20 */   public static String name = "";
/*  21 */   private GuiScreen oldScreen = null;
/*  22 */   private String prename = "";
/*     */   
/*  24 */   private String status = "Current name: " + name;
/*     */   
/*  26 */   private ScaledResolution sr = new ScaledResolution(Client.instance.getMc());
/*     */ 
/*     */   
/*     */   public GuiChangeName(GuiScreen oldScreen, boolean nobackground) {
/*  30 */     this.nobackground = nobackground;
/*     */     
/*  32 */     this.oldScreen = oldScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiChangeName(GuiScreen oldScreen, boolean nobackground, String prename) {
/*  38 */     this.nobackground = nobackground;
/*     */     
/*  40 */     this.oldScreen = oldScreen;
/*     */     
/*  42 */     this.prename = prename;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  48 */     this.nameField = new GuiTextField(3, this.mc.fontRendererObj, this.width / 2 - 75, this.height / 2 - 35, 150, 20);
/*     */     
/*  50 */     this.nameField.setFocused(true);
/*     */     
/*  52 */     this.nameField.setMaxStringLength(20);
/*     */     
/*  54 */     this.nameField.setText(this.prename.replaceAll("&", "§"));
/*     */     
/*  56 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 50, this.height / 2 + 10, 100, 20, "Change"));
/*     */     
/*  58 */     if (this.oldScreen != null)
/*     */     {
/*  60 */       this.buttonList.add(new HeraButton(2, this.width / 2 - 50, this.height / 2 + 40, 100, 20, "Back"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  68 */     if (button.id == 1) {
/*     */       
/*  70 */       name = this.nameField.getText();
/*     */       
/*  72 */       this.status = "New name: " + name.replaceAll("&", "§");
/*     */       
/*  74 */       Client.instance.getFileManager().saveName(name.replaceAll("&", "§"));
/*     */     } 
/*     */ 
/*     */     
/*  78 */     if (button.id == 2)
/*     */     {
/*  80 */       this.mc.displayGuiScreen(this.oldScreen);
/*     */     }
/*     */ 
/*     */     
/*  84 */     super.actionPerformed(button);
/*     */   }
/*     */   
/*     */   public void updateScreen() {
/*  88 */     this.nameField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  93 */     this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/*  95 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 100 */     this.nameField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 102 */     this.nameField.setText(this.nameField.getText().replaceAll("&", "§"));
/*     */     
/* 104 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 109 */     if (!this.nobackground)
/*     */     {
/* 111 */       drawBackground();
/*     */     }
/*     */ 
/*     */     
/* 115 */     drawRectWithEdge((this.width / 2 - 85), (this.height / 2 - 100), 170.0D, 200.0D, Colors.getPrimary());
/*     */     
/* 117 */     this.nameField.drawTextBox();
/*     */     
/* 119 */     FontHelper.cfArrayList.drawStringWithBG(this.status, (this.width / 2 - FontHelper.cfArrayList.getStringWidth(this.status) / 2), (
/* 120 */         this.height / 2 - 45 - FontHelper.cfArrayList.getHeight() - 10), Color.white);
/*     */     
/* 122 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\GuiChangeName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */