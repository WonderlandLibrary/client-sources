/*  1:   */ package me.connorm.Nodus.ui.tab;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.Minecraft;
/*  4:   */ import org.lwjgl.input.Keyboard;
/*  5:   */ 
/*  6:   */ public class GuiHandler
/*  7:   */ {
/*  8:   */   private Minecraft mc;
/*  9:10 */   private boolean[] keyStates = new boolean[256];
/* 10:   */   public NodusTabGui gui;
/* 11:   */   
/* 12:   */   public GuiHandler(Minecraft var1, ClientModules var2)
/* 13:   */   {
/* 14:15 */     this.mc = var1;
/* 15:16 */     this.gui = new NodusTabGui(var2, this.mc);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean checkKey(int var1)
/* 19:   */   {
/* 20:21 */     if (this.mc.currentScreen != null) {
/* 21:23 */       return false;
/* 22:   */     }
/* 23:26 */     if (Keyboard.isKeyDown(var1) != this.keyStates[var1]) {
/* 24:28 */       return this.keyStates[var1] = this.keyStates[var1] != 0 ? 0 : 1;
/* 25:   */     }
/* 26:31 */     return false;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.tab.GuiHandler
 * JD-Core Version:    0.7.0.1
 */