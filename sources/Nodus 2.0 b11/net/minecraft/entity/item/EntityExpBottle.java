/*  1:   */ package net.minecraft.entity.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.projectile.EntityThrowable;
/*  6:   */ import net.minecraft.util.MovingObjectPosition;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityExpBottle
/* 10:   */   extends EntityThrowable
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00001726";
/* 13:   */   
/* 14:   */   public EntityExpBottle(World par1World)
/* 15:   */   {
/* 16:14 */     super(par1World);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public EntityExpBottle(World par1World, EntityLivingBase par2EntityLivingBase)
/* 20:   */   {
/* 21:19 */     super(par1World, par2EntityLivingBase);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public EntityExpBottle(World par1World, double par2, double par4, double par6)
/* 25:   */   {
/* 26:24 */     super(par1World, par2, par4, par6);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected float getGravityVelocity()
/* 30:   */   {
/* 31:32 */     return 0.07F;
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected float func_70182_d()
/* 35:   */   {
/* 36:37 */     return 0.7F;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected float func_70183_g()
/* 40:   */   {
/* 41:42 */     return -20.0F;
/* 42:   */   }
/* 43:   */   
/* 44:   */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/* 45:   */   {
/* 46:50 */     if (!this.worldObj.isClient)
/* 47:   */     {
/* 48:52 */       this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
/* 49:53 */       int var2 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
/* 50:55 */       while (var2 > 0)
/* 51:   */       {
/* 52:57 */         int var3 = EntityXPOrb.getXPSplit(var2);
/* 53:58 */         var2 -= var3;
/* 54:59 */         this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var3));
/* 55:   */       }
/* 56:62 */       setDead();
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityExpBottle
 * JD-Core Version:    0.7.0.1
 */