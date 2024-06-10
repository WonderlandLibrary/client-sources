/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import net.minecraft.client.renderer.WorldRenderer;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ 
/*  7:   */ public class EntitySorterFast
/*  8:   */   implements Comparator
/*  9:   */ {
/* 10:   */   private Entity entity;
/* 11:   */   
/* 12:   */   public EntitySorterFast(Entity par1Entity)
/* 13:   */   {
/* 14:13 */     this.entity = par1Entity;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void prepareToSort(WorldRenderer[] renderers, int countWorldRenderers)
/* 18:   */   {
/* 19:18 */     for (int i = 0; i < countWorldRenderers; i++)
/* 20:   */     {
/* 21:20 */       WorldRenderer wr = renderers[i];
/* 22:21 */       wr.sortDistanceToEntitySquared = wr.distanceToEntitySquared(this.entity);
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int compare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
/* 27:   */   {
/* 28:27 */     float dist1 = par1WorldRenderer.sortDistanceToEntitySquared;
/* 29:28 */     float dist2 = par2WorldRenderer.sortDistanceToEntitySquared;
/* 30:29 */     return dist1 > dist2 ? 1 : dist1 == dist2 ? 0 : -1;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int compare(Object par1Obj, Object par2Obj)
/* 34:   */   {
/* 35:34 */     return compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.EntitySorterFast
 * JD-Core Version:    0.7.0.1
 */