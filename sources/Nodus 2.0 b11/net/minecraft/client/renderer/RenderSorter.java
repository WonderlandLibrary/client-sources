/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ 
/*  6:   */ public class RenderSorter
/*  7:   */   implements Comparator
/*  8:   */ {
/*  9:   */   private EntityLivingBase baseEntity;
/* 10:   */   private static final String __OBFID = "CL_00000943";
/* 11:   */   
/* 12:   */   public RenderSorter(EntityLivingBase par1EntityLivingBase)
/* 13:   */   {
/* 14:14 */     this.baseEntity = par1EntityLivingBase;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int compare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
/* 18:   */   {
/* 19:19 */     if ((par1WorldRenderer.isInFrustum) && (!par2WorldRenderer.isInFrustum)) {
/* 20:21 */       return 1;
/* 21:   */     }
/* 22:23 */     if ((par2WorldRenderer.isInFrustum) && (!par1WorldRenderer.isInFrustum)) {
/* 23:25 */       return -1;
/* 24:   */     }
/* 25:29 */     double var3 = par1WorldRenderer.distanceToEntitySquared(this.baseEntity);
/* 26:30 */     double var5 = par2WorldRenderer.distanceToEntitySquared(this.baseEntity);
/* 27:31 */     return par1WorldRenderer.chunkIndex < par2WorldRenderer.chunkIndex ? 1 : var3 > var5 ? -1 : var3 < var5 ? 1 : -1;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int compare(Object par1Obj, Object par2Obj)
/* 31:   */   {
/* 32:37 */     return compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.RenderSorter
 * JD-Core Version:    0.7.0.1
 */