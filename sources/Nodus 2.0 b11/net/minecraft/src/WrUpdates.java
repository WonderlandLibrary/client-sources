/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.client.renderer.RenderGlobal;
/*   5:    */ import net.minecraft.client.renderer.WorldRenderer;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class WrUpdates
/*  10:    */ {
/*  11: 11 */   private static IWrUpdater wrUpdater = null;
/*  12:    */   
/*  13:    */   public static void setWrUpdater(IWrUpdater updater)
/*  14:    */   {
/*  15: 15 */     if (wrUpdater != null) {
/*  16: 17 */       wrUpdater.terminate();
/*  17:    */     }
/*  18: 20 */     wrUpdater = updater;
/*  19: 22 */     if (wrUpdater != null) {
/*  20:    */       try
/*  21:    */       {
/*  22: 26 */         wrUpdater.initialize();
/*  23:    */       }
/*  24:    */       catch (Exception var2)
/*  25:    */       {
/*  26: 30 */         wrUpdater = null;
/*  27: 31 */         var2.printStackTrace();
/*  28:    */       }
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static boolean hasWrUpdater()
/*  33:    */   {
/*  34: 38 */     return wrUpdater != null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static IWrUpdater getWrUpdater()
/*  38:    */   {
/*  39: 43 */     return wrUpdater;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static WorldRenderer makeWorldRenderer(World worldObj, List tileEntities, int x, int y, int z, int glRenderListBase, int rendererIndex)
/*  43:    */   {
/*  44: 48 */     return wrUpdater == null ? new WorldRenderer(worldObj, tileEntities, x, y, z, glRenderListBase) : wrUpdater.makeWorldRenderer(worldObj, tileEntities, x, y, z, glRenderListBase, rendererIndex);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static boolean updateRenderers(RenderGlobal rg, EntityLivingBase entityliving, boolean flag)
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 55 */       return wrUpdater.updateRenderers(rg, entityliving, flag);
/*  52:    */     }
/*  53:    */     catch (Exception var4)
/*  54:    */     {
/*  55: 59 */       var4.printStackTrace();
/*  56: 60 */       setWrUpdater(null);
/*  57:    */     }
/*  58: 61 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void resumeBackgroundUpdates()
/*  62:    */   {
/*  63: 67 */     if (wrUpdater != null) {
/*  64: 69 */       wrUpdater.resumeBackgroundUpdates();
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void pauseBackgroundUpdates()
/*  69:    */   {
/*  70: 75 */     if (wrUpdater != null) {
/*  71: 77 */       wrUpdater.pauseBackgroundUpdates();
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void finishCurrentUpdate()
/*  76:    */   {
/*  77: 83 */     if (wrUpdater != null) {
/*  78: 85 */       wrUpdater.finishCurrentUpdate();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static void preRender(RenderGlobal rg, EntityLivingBase player)
/*  83:    */   {
/*  84: 91 */     if (wrUpdater != null) {
/*  85: 93 */       wrUpdater.preRender(rg, player);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void postRender()
/*  90:    */   {
/*  91: 99 */     if (wrUpdater != null) {
/*  92:101 */       wrUpdater.postRender();
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void clearAllUpdates()
/*  97:    */   {
/*  98:107 */     if (wrUpdater != null) {
/*  99:109 */       wrUpdater.clearAllUpdates();
/* 100:    */     }
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.WrUpdates
 * JD-Core Version:    0.7.0.1
 */