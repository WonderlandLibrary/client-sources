/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ 
/*  6:   */ public class EntitySorter
/*  7:   */   implements Comparator
/*  8:   */ {
/*  9:   */   private double entityPosX;
/* 10:   */   private double entityPosY;
/* 11:   */   private double entityPosZ;
/* 12:   */   private static final String __OBFID = "CL_00000944";
/* 13:   */   
/* 14:   */   public EntitySorter(Entity par1Entity)
/* 15:   */   {
/* 16:20 */     this.entityPosX = (-par1Entity.posX);
/* 17:21 */     this.entityPosY = (-par1Entity.posY);
/* 18:22 */     this.entityPosZ = (-par1Entity.posZ);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int compare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
/* 22:   */   {
/* 23:27 */     double var3 = par1WorldRenderer.posXPlus + this.entityPosX;
/* 24:28 */     double var5 = par1WorldRenderer.posYPlus + this.entityPosY;
/* 25:29 */     double var7 = par1WorldRenderer.posZPlus + this.entityPosZ;
/* 26:30 */     double var9 = par2WorldRenderer.posXPlus + this.entityPosX;
/* 27:31 */     double var11 = par2WorldRenderer.posYPlus + this.entityPosY;
/* 28:32 */     double var13 = par2WorldRenderer.posZPlus + this.entityPosZ;
/* 29:33 */     return (int)((var3 * var3 + var5 * var5 + var7 * var7 - (var9 * var9 + var11 * var11 + var13 * var13)) * 1024.0D);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int compare(Object par1Obj, Object par2Obj)
/* 33:   */   {
/* 34:38 */     return compare((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.EntitySorter
 * JD-Core Version:    0.7.0.1
 */