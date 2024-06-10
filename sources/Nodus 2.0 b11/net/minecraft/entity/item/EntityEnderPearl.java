/*  1:   */ package net.minecraft.entity.item;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.entity.player.EntityPlayerMP;
/*  7:   */ import net.minecraft.entity.projectile.EntityThrowable;
/*  8:   */ import net.minecraft.network.NetHandlerPlayServer;
/*  9:   */ import net.minecraft.network.NetworkManager;
/* 10:   */ import net.minecraft.util.DamageSource;
/* 11:   */ import net.minecraft.util.MovingObjectPosition;
/* 12:   */ import net.minecraft.world.World;
/* 13:   */ 
/* 14:   */ public class EntityEnderPearl
/* 15:   */   extends EntityThrowable
/* 16:   */ {
/* 17:   */   private static final String __OBFID = "CL_00001725";
/* 18:   */   
/* 19:   */   public EntityEnderPearl(World par1World)
/* 20:   */   {
/* 21:17 */     super(par1World);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public EntityEnderPearl(World par1World, EntityLivingBase par2EntityLivingBase)
/* 25:   */   {
/* 26:22 */     super(par1World, par2EntityLivingBase);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EntityEnderPearl(World par1World, double par2, double par4, double par6)
/* 30:   */   {
/* 31:27 */     super(par1World, par2, par4, par6);
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/* 35:   */   {
/* 36:35 */     if (par1MovingObjectPosition.entityHit != null) {
/* 37:37 */       par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
/* 38:   */     }
/* 39:40 */     for (int var2 = 0; var2 < 32; var2++) {
/* 40:42 */       this.worldObj.spawnParticle("portal", this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
/* 41:   */     }
/* 42:45 */     if (!this.worldObj.isClient)
/* 43:   */     {
/* 44:47 */       if ((getThrower() != null) && ((getThrower() instanceof EntityPlayerMP)))
/* 45:   */       {
/* 46:49 */         EntityPlayerMP var3 = (EntityPlayerMP)getThrower();
/* 47:51 */         if ((var3.playerNetServerHandler.func_147362_b().isChannelOpen()) && (var3.worldObj == this.worldObj))
/* 48:   */         {
/* 49:53 */           if (getThrower().isRiding()) {
/* 50:55 */             getThrower().mountEntity(null);
/* 51:   */           }
/* 52:58 */           getThrower().setPositionAndUpdate(this.posX, this.posY, this.posZ);
/* 53:59 */           getThrower().fallDistance = 0.0F;
/* 54:60 */           getThrower().attackEntityFrom(DamageSource.fall, 5.0F);
/* 55:   */         }
/* 56:   */       }
/* 57:64 */       setDead();
/* 58:   */     }
/* 59:   */   }
/* 60:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityEnderPearl
 * JD-Core Version:    0.7.0.1
 */