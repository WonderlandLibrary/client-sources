/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.entity.EntityLiving;
/*  7:   */ import net.minecraft.profiler.Profiler;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntitySenses
/* 11:   */ {
/* 12:   */   EntityLiving entityObj;
/* 13:13 */   List seenEntities = new ArrayList();
/* 14:16 */   List unseenEntities = new ArrayList();
/* 15:   */   private static final String __OBFID = "CL_00001628";
/* 16:   */   
/* 17:   */   public EntitySenses(EntityLiving par1EntityLiving)
/* 18:   */   {
/* 19:21 */     this.entityObj = par1EntityLiving;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void clearSensingCache()
/* 23:   */   {
/* 24:29 */     this.seenEntities.clear();
/* 25:30 */     this.unseenEntities.clear();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean canSee(Entity par1Entity)
/* 29:   */   {
/* 30:38 */     if (this.seenEntities.contains(par1Entity)) {
/* 31:40 */       return true;
/* 32:   */     }
/* 33:42 */     if (this.unseenEntities.contains(par1Entity)) {
/* 34:44 */       return false;
/* 35:   */     }
/* 36:48 */     this.entityObj.worldObj.theProfiler.startSection("canSee");
/* 37:49 */     boolean var2 = this.entityObj.canEntityBeSeen(par1Entity);
/* 38:50 */     this.entityObj.worldObj.theProfiler.endSection();
/* 39:52 */     if (var2) {
/* 40:54 */       this.seenEntities.add(par1Entity);
/* 41:   */     } else {
/* 42:58 */       this.unseenEntities.add(par1Entity);
/* 43:   */     }
/* 44:61 */     return var2;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntitySenses
 * JD-Core Version:    0.7.0.1
 */