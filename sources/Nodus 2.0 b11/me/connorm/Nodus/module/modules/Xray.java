/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import me.connorm.Nodus.Nodus;
/*  5:   */ import me.connorm.Nodus.module.core.Category;
/*  6:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ import net.minecraft.client.renderer.RenderGlobal;
/*  9:   */ 
/* 10:   */ public class Xray
/* 11:   */   extends NodusModule
/* 12:   */ {
/* 13:12 */   public ArrayList<String> xrayBlocks = new ArrayList();
/* 14:   */   
/* 15:   */   public Xray()
/* 16:   */   {
/* 17:16 */     super("Xray", Category.DISPLAY);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void onEnable()
/* 21:   */   {
/* 22:21 */     me.connorm.Nodus.module.modules.utils.XrayUtils.xrayEnabled = true;
/* 23:22 */     Nodus.theNodus.getMinecraft().gameSettings.gammaSetting = 10.0F;
/* 24:23 */     Nodus.theNodus.getMinecraft().renderGlobal.loadRenderers();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void onDisable()
/* 28:   */   {
/* 29:28 */     me.connorm.Nodus.module.modules.utils.XrayUtils.xrayEnabled = false;
/* 30:29 */     Nodus.theNodus.getMinecraft().gameSettings.gammaSetting = 1.0F;
/* 31:30 */     Nodus.theNodus.getMinecraft().renderGlobal.loadRenderers();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean isXrayBlock(String blockToCheck)
/* 35:   */   {
/* 36:35 */     if (this.xrayBlocks.contains(blockToCheck)) {
/* 37:37 */       return true;
/* 38:   */     }
/* 39:39 */     return false;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.Xray
 * JD-Core Version:    0.7.0.1
 */