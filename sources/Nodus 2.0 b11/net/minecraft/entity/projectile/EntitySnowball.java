/*  1:   */ package net.minecraft.entity.projectile;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.monster.EntityBlaze;
/*  6:   */ import net.minecraft.util.DamageSource;
/*  7:   */ import net.minecraft.util.MovingObjectPosition;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntitySnowball
/* 11:   */   extends EntityThrowable
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001722";
/* 14:   */   
/* 15:   */   public EntitySnowball(World par1World)
/* 16:   */   {
/* 17:15 */     super(par1World);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public EntitySnowball(World par1World, EntityLivingBase par2EntityLivingBase)
/* 21:   */   {
/* 22:20 */     super(par1World, par2EntityLivingBase);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public EntitySnowball(World par1World, double par2, double par4, double par6)
/* 26:   */   {
/* 27:25 */     super(par1World, par2, par4, par6);
/* 28:   */   }
/* 29:   */   
/* 30:   */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/* 31:   */   {
/* 32:33 */     if (par1MovingObjectPosition.entityHit != null)
/* 33:   */     {
/* 34:35 */       byte var2 = 0;
/* 35:37 */       if ((par1MovingObjectPosition.entityHit instanceof EntityBlaze)) {
/* 36:39 */         var2 = 3;
/* 37:   */       }
/* 38:42 */       par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
/* 39:   */     }
/* 40:45 */     for (int var3 = 0; var3 < 8; var3++) {
/* 41:47 */       this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
/* 42:   */     }
/* 43:50 */     if (!this.worldObj.isClient) {
/* 44:52 */       setDead();
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntitySnowball
 * JD-Core Version:    0.7.0.1
 */