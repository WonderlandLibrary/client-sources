/*     */ package me.eagler.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import me.eagler.clickgui.Colors;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiIngameMenu;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiAddFriend
/*     */   extends GuiScreen
/*     */ {
/*     */   private GuiScreen oldScreen;
/*     */   private boolean noBackground;
/*     */   private GuiTextField nameField;
/*     */   private GuiTextField nickField;
/*     */   
/*     */   public GuiAddFriend(GuiScreen oldScreen, boolean noBackground) {
/*  24 */     this.oldScreen = oldScreen;
/*  25 */     this.noBackground = noBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  31 */     this.nameField = new GuiTextField(1, this.mc.fontRendererObj, this.width / 2 - 75, this.height / 2 - 55, 150, 20);
/*  32 */     this.nickField = new GuiTextField(2, this.mc.fontRendererObj, this.width / 2 - 75, this.height / 2 - 14, 150, 20);
/*     */     
/*  34 */     this.buttonList.add(new HeraButton(0, this.width / 2 - 50, this.height / 2 + 20, 100, 20, "Add"));
/*  35 */     this.buttonList.add(new HeraButton(1, this.width / 2 - 50, this.height / 2 + 50, 100, 20, "Back"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  40 */     this.nameField.updateCursorCounter();
/*  41 */     this.nickField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  46 */     this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
/*  47 */     this.nickField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/*  49 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  54 */     this.nameField.textboxKeyTyped(typedChar, keyCode);
/*  55 */     this.nickField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/*  57 */     this.nickField.setText(this.nickField.getText().replaceAll("&", "§"));
/*     */     
/*  59 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  64 */     if (button.id == 0)
/*     */     {
/*  66 */       if (this.nameField.getText() != "") {
/*     */         
/*  68 */         if (GuiFriends.friends.size() != 8)
/*     */         {
/*  70 */           if (this.nickField.getText() != "") {
/*     */ 
/*     */             
/*  73 */             if (!GuiFriends.friends.contains(this.nameField.getText()))
/*     */             {
/*  75 */               GuiFriends.friends.add(this.nameField.getText());
/*     */             }
/*     */ 
/*     */             
/*  79 */             GuiFriends.friendsnick.put(this.nameField.getText(), this.nickField.getText());
/*     */           
/*     */           }
/*     */           else {
/*     */             
/*  84 */             if (!GuiFriends.friends.contains(this.nameField.getText()))
/*     */             {
/*  86 */               GuiFriends.friends.add(this.nameField.getText());
/*     */             }
/*     */ 
/*     */             
/*  90 */             GuiFriends.friendsnick.put(this.nameField.getText(), this.nameField.getText());
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  96 */         this.mc.displayGuiScreen(new GuiFriends((GuiScreen)new GuiIngameMenu(), true));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (button.id == 1)
/*     */     {
/* 104 */       this.mc.displayGuiScreen(this.oldScreen);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 109 */     super.actionPerformed(button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 114 */     if (!this.noBackground)
/*     */     {
/* 116 */       drawBackground();
/*     */     }
/*     */ 
/*     */     
/* 120 */     drawRectWithEdge((this.width / 2 - 85), (this.height / 2 - 100), 170.0D, 200.0D, Colors.getPrimary());
/*     */     
/* 122 */     this.nameField.drawTextBox();
/* 123 */     this.nickField.drawTextBox();
/*     */     
/* 125 */     FontHelper.cfArrayList.drawStringWithBG("Name:", (this.width / 2 - FontHelper.cfArrayList.getStringWidth("Name:") / 2), (
/* 126 */         this.height / 2 - 72), Color.white);
/*     */     
/* 128 */     FontHelper.cfArrayList.drawStringWithBG("Nick:", (this.width / 2 - FontHelper.cfArrayList.getStringWidth("Nick:") / 2), (
/* 129 */         this.height / 2 - 31), Color.white);
/*     */     
/* 131 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\GuiAddFriend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */