/*  1:   */ package me.connorm.Nodus.ui.tab;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  5:   */ import me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils;
/*  6:   */ import net.minecraft.client.gui.FontRenderer;
/*  7:   */ 
/*  8:   */ public class GuiTab
/*  9:   */ {
/* 10:   */   private NodusTabGui gui;
/* 11:   */   public ArrayList hacks;
/* 12:   */   public String tabName;
/* 13:14 */   public int selectedItem = 0;
/* 14:15 */   public int menuHeight = 0;
/* 15:16 */   public int menuWidth = 0;
/* 16:   */   
/* 17:   */   public GuiTab(NodusTabGui var1, String var2)
/* 18:   */   {
/* 19:20 */     this.tabName = var2;
/* 20:21 */     this.gui = var1;
/* 21:22 */     this.hacks = new ArrayList();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void countMenuSize(FontRenderer var1)
/* 25:   */   {
/* 26:27 */     int var2 = 0;
/* 27:29 */     for (int var3 = 0; var3 < this.hacks.size(); var3++) {
/* 28:31 */       if (var1.getStringWidth(((ItemModule)this.hacks.get(var3)).text) > var2) {
/* 29:33 */         var2 = var1.getStringWidth(((ItemModule)this.hacks.get(var3)).text) + 7;
/* 30:   */       }
/* 31:   */     }
/* 32:36 */     this.menuWidth = var2;
/* 33:37 */     this.menuHeight = (this.hacks.size() * this.gui.tabHeight - 1);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void drawTabMenu(int var1, int var2, FontRenderer var3)
/* 37:   */   {
/* 38:42 */     countMenuSize(var3);
/* 39:43 */     var1 += 2;
/* 40:44 */     var2 += 2;
/* 41:   */     
/* 42:46 */     NodusUtils.drawNodusTabRect(var1 - 2, var2 - 2, var1 + this.menuWidth + 6, var2 + this.menuHeight + 2);
/* 43:48 */     for (int var4 = 0; var4 < this.hacks.size(); var4++)
/* 44:   */     {
/* 45:50 */       var3.drawString((var4 == this.gui.selectedItem ? this.gui.selectedItemChar : "") + ((ItemModule)this.hacks.get(var4)).text, var1, var2 + this.gui.tabHeight * var4 + 2, var4 == this.gui.selectedItem ? ColorUtils.secondaryColor : ColorUtils.primaryColor);
/* 46:   */       
/* 47:52 */       this.hacks.size();
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.GuiTab
 * JD-Core Version:    0.7.0.1
 */