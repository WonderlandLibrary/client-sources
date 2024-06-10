/*  1:   */ package me.connorm.Nodus.ui.tab;
/*  2:   */ 
/*  3:   */ public class ItemModule
/*  4:   */ {
/*  5:   */   public String text;
/*  6:   */   public String hack;
/*  7:   */   private ClientModules ch;
/*  8:   */   public boolean toggleButton;
/*  9:   */   
/* 10:   */   public ItemModule(String var1, String var2, boolean var3, ClientModules var4)
/* 11:   */   {
/* 12:11 */     this.text = var1;
/* 13:12 */     this.hack = var2;
/* 14:13 */     this.toggleButton = var3;
/* 15:14 */     this.ch = var4;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void exeHack()
/* 19:   */   {
/* 20:19 */     if (this.toggleButton) {
/* 21:21 */       this.ch.toggleVar(this.hack);
/* 22:   */     } else {
/* 23:25 */       this.ch.executeHack(this.hack);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.ItemModule
 * JD-Core Version:    0.7.0.1
 */