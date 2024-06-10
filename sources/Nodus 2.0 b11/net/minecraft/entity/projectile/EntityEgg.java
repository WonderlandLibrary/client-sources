/*  1:   */ package net.minecraft.entity.projectile;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.passive.EntityChicken;
/*  7:   */ import net.minecraft.util.DamageSource;
/*  8:   */ import net.minecraft.util.MovingObjectPosition;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityEgg
/* 12:   */   extends EntityThrowable
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00001724";
/* 15:   */   
/* 16:   */   public EntityEgg(World par1World)
/* 17:   */   {
/* 18:15 */     super(par1World);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public EntityEgg(World par1World, EntityLivingBase par2EntityLivingBase)
/* 22:   */   {
/* 23:20 */     super(par1World, par2EntityLivingBase);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public EntityEgg(World par1World, double par2, double par4, double par6)
/* 27:   */   {
/* 28:25 */     super(par1World, par2, par4, par6);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/* 32:   */   {
/* 33:33 */     if (par1MovingObjectPosition.entityHit != null) {
/* 34:35 */       par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
/* 35:   */     }
/* 36:38 */     if ((!this.worldObj.isClient) && (this.rand.nextInt(8) == 0))
/* 37:   */     {
/* 38:40 */       byte var2 = 1;
/* 39:42 */       if (this.rand.nextInt(32) == 0) {
/* 40:44 */         var2 = 4;
/* 41:   */       }
/* 42:47 */       for (int var3 = 0; var3 < var2; var3++)
/* 43:   */       {
/* 44:49 */         EntityChicken var4 = new EntityChicken(this.worldObj);
/* 45:50 */         var4.setGrowingAge(-24000);
/* 46:51 */         var4.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 47:52 */         this.worldObj.spawnEntityInWorld(var4);
/* 48:   */       }
/* 49:   */     }
/* 50:56 */     for (int var5 = 0; var5 < 8; var5++) {
/* 51:58 */       this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
/* 52:   */     }
/* 53:61 */     if (!this.worldObj.isClient) {
/* 54:63 */       setDead();
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityEgg
 * JD-Core Version:    0.7.0.1
 */