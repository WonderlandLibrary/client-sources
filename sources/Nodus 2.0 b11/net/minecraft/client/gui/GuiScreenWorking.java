/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import net.minecraft.util.IProgressUpdate;
/*  5:   */ 
/*  6:   */ public class GuiScreenWorking
/*  7:   */   extends GuiScreen
/*  8:   */   implements IProgressUpdate
/*  9:   */ {
/* 10: 7 */   private String field_146591_a = "";
/* 11: 8 */   private String field_146589_f = "";
/* 12:   */   private int field_146590_g;
/* 13:   */   private boolean field_146592_h;
/* 14:   */   private static final String __OBFID = "CL_00000707";
/* 15:   */   
/* 16:   */   public void displayProgressMessage(String par1Str)
/* 17:   */   {
/* 18:18 */     resetProgressAndMessage(par1Str);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void resetProgressAndMessage(String par1Str)
/* 22:   */   {
/* 23:27 */     this.field_146591_a = par1Str;
/* 24:28 */     resetProgresAndWorkingMessage("Working...");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void resetProgresAndWorkingMessage(String par1Str)
/* 28:   */   {
/* 29:36 */     this.field_146589_f = par1Str;
/* 30:37 */     setLoadingProgress(0);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setLoadingProgress(int par1)
/* 34:   */   {
/* 35:45 */     this.field_146590_g = par1;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void func_146586_a()
/* 39:   */   {
/* 40:50 */     this.field_146592_h = true;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void drawScreen(int par1, int par2, float par3)
/* 44:   */   {
/* 45:58 */     if (this.field_146592_h)
/* 46:   */     {
/* 47:60 */       this.mc.displayGuiScreen(null);
/* 48:   */     }
/* 49:   */     else
/* 50:   */     {
/* 51:64 */       drawDefaultBackground();
/* 52:65 */       drawCenteredString(this.fontRendererObj, this.field_146591_a, width / 2, 70, 16777215);
/* 53:66 */       drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.field_146590_g + "%", width / 2, 90, 16777215);
/* 54:67 */       super.drawScreen(par1, par2, par3);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenWorking
 * JD-Core Version:    0.7.0.1
 */