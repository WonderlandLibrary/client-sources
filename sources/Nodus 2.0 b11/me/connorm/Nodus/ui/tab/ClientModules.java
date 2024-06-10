/*  1:   */ package me.connorm.Nodus.ui.tab;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import me.connorm.Nodus.Nodus;
/*  6:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  7:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  8:   */ import net.minecraft.client.Minecraft;
/*  9:   */ import net.minecraft.client.gui.FontRenderer;
/* 10:   */ 
/* 11:   */ public class ClientModules
/* 12:   */ {
/* 13:   */   public boolean[] var;
/* 14:   */   public ArrayList hackNames;
/* 15:   */   public ArrayList enabledHacks;
/* 16:   */   public GuiHandler handler;
/* 17:   */   public Minecraft mc;
/* 18:   */   
/* 19:   */   public ClientModules(Minecraft var1)
/* 20:   */   {
/* 21:19 */     this.handler = new GuiHandler(var1, this);
/* 22:20 */     this.hackNames = new ArrayList();
/* 23:21 */     for (NodusModule m : Nodus.theNodus.moduleManager.getModules()) {
/* 24:23 */       this.hackNames.add(m.getName().toLowerCase());
/* 25:   */     }
/* 26:25 */     this.var = new boolean[256];
/* 27:27 */     for (int var2 = 0; var2 < this.hackNames.size(); var2++) {
/* 28:29 */       this.var[var2] = false;
/* 29:   */     }
/* 30:32 */     this.enabledHacks = new ArrayList();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void drawGui(FontRenderer var1)
/* 34:   */   {
/* 35:37 */     this.handler.gui.drawGui(var1);
/* 36:   */   }
/* 37:   */   
/* 38:   */   private boolean isOn(String var1)
/* 39:   */   {
/* 40:42 */     for (int var2 = 0; var2 < this.hackNames.size(); var2++) {
/* 41:44 */       if (this.hackNames.get(var2).equals(var1)) {
/* 42:46 */         return this.var[var2];
/* 43:   */       }
/* 44:   */     }
/* 45:50 */     System.out.println("The hack \"" + var1 + "\" does not exist.");
/* 46:51 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void toggleVar(String var1)
/* 50:   */   {
/* 51:56 */     for (NodusModule m : Nodus.theNodus.moduleManager.getModules()) {
/* 52:57 */       if (var1.equals(m.getName().toLowerCase())) {
/* 53:58 */         m.toggleModule();
/* 54:   */       }
/* 55:   */     }
/* 56:62 */     for (int var2 = 0; var2 < this.hackNames.size(); var2++) {
/* 57:64 */       if (this.hackNames.get(var2).equals(var1))
/* 58:   */       {
/* 59:66 */         this.var[var2] = ((this.var[var2] != 0 ? Integer.valueOf(0) : Boolean.valueOf(true)) != null ? 1 : false);
/* 60:67 */         relistEnabled();
/* 61:68 */         return;
/* 62:   */       }
/* 63:   */     }
/* 64:71 */     System.out.println("The hack \"" + var1 + "\" does not exist.");
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void executeHack(String var1)
/* 68:   */   {
/* 69:76 */     if (var1.equals("disableAll"))
/* 70:   */     {
/* 71:78 */       for (int var2 = 0; var2 < this.hackNames.size(); var2++) {
/* 72:80 */         this.var[var2] = false;
/* 73:   */       }
/* 74:83 */       relistEnabled();
/* 75:   */     }
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void relistEnabled()
/* 79:   */   {
/* 80:89 */     this.enabledHacks.clear();
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.ClientModules
 * JD-Core Version:    0.7.0.1
 */