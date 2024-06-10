/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.net.URI;
/*  5:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  6:   */ import org.apache.logging.log4j.LogManager;
/*  7:   */ import org.apache.logging.log4j.Logger;
/*  8:   */ 
/*  9:   */ public class GuiButtonLink
/* 10:   */   extends NodusGuiButton
/* 11:   */ {
/* 12:12 */   private static final Logger logger = ;
/* 13:   */   private static final String __OBFID = "CL_00000673";
/* 14:   */   
/* 15:   */   public GuiButtonLink(int par1, int par2, int par3, int par4, int par5, String par6Str)
/* 16:   */   {
/* 17:17 */     super(par1, par2, par3, par4, par5, par6Str);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void func_146138_a(String p_146138_1_)
/* 21:   */   {
/* 22:   */     try
/* 23:   */     {
/* 24:24 */       URI var2 = new URI(p_146138_1_);
/* 25:25 */       Class var3 = Class.forName("java.awt.Desktop");
/* 26:26 */       Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 27:27 */       var3.getMethod("browse", new Class[] { URI.class }).invoke(var4, new Object[] { var2 });
/* 28:   */     }
/* 29:   */     catch (Throwable var5)
/* 30:   */     {
/* 31:31 */       logger.error("Couldn't open link", var5);
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiButtonLink
 * JD-Core Version:    0.7.0.1
 */