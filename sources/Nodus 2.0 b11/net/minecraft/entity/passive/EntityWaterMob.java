/*  1:   */ package net.minecraft.entity.passive;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityCreature;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.util.DamageSource;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public abstract class EntityWaterMob
/* 10:   */   extends EntityCreature
/* 11:   */   implements IAnimals
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00001653";
/* 14:   */   
/* 15:   */   public EntityWaterMob(World par1World)
/* 16:   */   {
/* 17:14 */     super(par1World);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean canBreatheUnderwater()
/* 21:   */   {
/* 22:19 */     return true;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean getCanSpawnHere()
/* 26:   */   {
/* 27:27 */     return this.worldObj.checkNoEntityCollision(this.boundingBox);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getTalkInterval()
/* 31:   */   {
/* 32:35 */     return 120;
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected boolean canDespawn()
/* 36:   */   {
/* 37:43 */     return true;
/* 38:   */   }
/* 39:   */   
/* 40:   */   protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
/* 41:   */   {
/* 42:51 */     return 1 + this.worldObj.rand.nextInt(3);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void onEntityUpdate()
/* 46:   */   {
/* 47:59 */     int var1 = getAir();
/* 48:60 */     super.onEntityUpdate();
/* 49:62 */     if ((isEntityAlive()) && (!isInWater()))
/* 50:   */     {
/* 51:64 */       var1--;
/* 52:65 */       setAir(var1);
/* 53:67 */       if (getAir() == -20)
/* 54:   */       {
/* 55:69 */         setAir(0);
/* 56:70 */         attackEntityFrom(DamageSource.drown, 2.0F);
/* 57:   */       }
/* 58:   */     }
/* 59:   */     else
/* 60:   */     {
/* 61:75 */       setAir(300);
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.passive.EntityWaterMob
 * JD-Core Version:    0.7.0.1
 */