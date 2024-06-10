/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import me.connorm.Nodus.ui.NodusGuiConsole;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ 
/*  9:   */ public class Console
/* 10:   */   extends NodusModule
/* 11:   */ {
/* 12:   */   public Console()
/* 13:   */   {
/* 14:14 */     super("Console", Category.OTHER);
/* 15:15 */     setKeyBind(22);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void onToggle()
/* 19:   */   {
/* 20:20 */     Nodus.theNodus.getMinecraft().displayGuiScreen(new NodusGuiConsole());
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Console
 * JD-Core Version:    0.7.0.1
 */