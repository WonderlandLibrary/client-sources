/*     */ package me.eagler.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import me.eagler.clickgui.Colors;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.gui.stuff.HeraButton;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ 
/*     */ public class GuiFriends
/*     */   extends GuiScreen
/*     */ {
/*  16 */   public static ArrayList<String> friends = new ArrayList<String>();
/*  17 */   public static HashMap<String, String> friendsnick = new HashMap<String, String>();
/*     */   
/*     */   private GuiScreen oldScreen;
/*     */   
/*     */   private boolean noBackground;
/*     */   
/*     */   public GuiFriends(GuiScreen oldScreen, boolean noBackground) {
/*  24 */     this.oldScreen = oldScreen;
/*  25 */     this.noBackground = noBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  31 */     this.buttonList.add(new HeraButton(22, this.width / 2 - 80, this.height / 2 + 80, 50, 15, "Add"));
/*  32 */     this.buttonList.add(new HeraButton(44, this.width / 2 - 25, this.height / 2 + 80, 50, 15, "Clear"));
/*  33 */     this.buttonList.add(new HeraButton(33, this.width / 2 + 30, this.height / 2 + 80, 50, 15, "Back"));
/*     */     
/*  35 */     if (friends.size() != 0)
/*     */     {
/*  37 */       for (int i = 0; i < friends.size(); i++) {
/*     */         
/*  39 */         int height = this.height / 2 - 90 + (FontHelper.cfArrayList.getHeight() + 7) * (i + 1);
/*     */         
/*  41 */         this.buttonList.add(new HeraButton(i, this.width / 2 + 80 - FontHelper.cfArrayList.getHeight() + 1, 
/*  42 */               height + 1, FontHelper.cfArrayList.getHeight() + 1, FontHelper.cfArrayList.getHeight() + 1, "§c§lX"));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  48 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  53 */     if (friends.size() != 0)
/*     */     {
/*  55 */       for (int i = 0; i < friends.size(); i++) {
/*     */         
/*  57 */         if (button.id == i) {
/*     */           
/*  59 */           friendsnick.remove(friends.get(i));
/*  60 */           friends.remove(i);
/*     */           
/*  62 */           this.mc.displayGuiScreen(new GuiFriends(this.oldScreen, true));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     if (button.id == 22)
/*     */     {
/*  72 */       this.mc.displayGuiScreen(new GuiAddFriend(this, true));
/*     */     }
/*     */ 
/*     */     
/*  76 */     if (button.id == 33)
/*     */     {
/*  78 */       this.mc.displayGuiScreen(this.oldScreen);
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (button.id == 44) {
/*     */       
/*  84 */       friends.clear();
/*  85 */       this.mc.displayGuiScreen(new GuiFriends(this.oldScreen, true));
/*     */     } 
/*     */ 
/*     */     
/*  89 */     super.actionPerformed(button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  94 */     if (!this.noBackground)
/*     */     {
/*  96 */       drawBackground();
/*     */     }
/*     */ 
/*     */     
/* 100 */     drawRectWithEdge((this.width / 2 - 85), (this.height / 2 - 100), 170.0D, 200.0D, Colors.getPrimary());
/*     */     
/* 102 */     FontHelper.cfArrayList.drawStringWithBG("Friends (" + friends.size() + "/8):", (this.width / 2 - 77), (this.height / 2 - 95), Color.white);
/*     */     
/* 104 */     if (friends.size() != 0)
/*     */     {
/* 106 */       for (int i = 0; i < friends.size(); i++) {
/*     */         
/* 108 */         float height = (this.height / 2 - 90 + (FontHelper.cfArrayList.getHeight() + 7) * (i + 1));
/*     */         
/* 110 */         String text = "§f§lName: " + (String)friends.get(i) + " | Nick: " + (String)friendsnick.get(friends.get(i));
/*     */         
/* 112 */         if (FontHelper.cfArrayList.getStringWidth(text) > 130)
/*     */         {
/* 114 */           text = "§f§lName: " + ((String)friends.get(i)).substring(0, 5) + "... | Nick: " + ((String)friendsnick.get(friends.get(i))).substring(0, 5) + "...";
/*     */         }
/*     */ 
/*     */         
/* 118 */         FontHelper.cfArrayList.drawStringWithBG(text, (this.width / 2 - 77), height, Color.white);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 124 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\GuiFriends.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */