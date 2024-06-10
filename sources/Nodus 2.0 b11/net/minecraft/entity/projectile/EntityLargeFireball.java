/*  1:   */ package net.minecraft.entity.projectile;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ import net.minecraft.util.DamageSource;
/*  7:   */ import net.minecraft.util.MovingObjectPosition;
/*  8:   */ import net.minecraft.world.GameRules;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityLargeFireball
/* 12:   */   extends EntityFireball
/* 13:   */ {
/* 14:12 */   public int field_92057_e = 1;
/* 15:   */   private static final String __OBFID = "CL_00001719";
/* 16:   */   
/* 17:   */   public EntityLargeFireball(World par1World)
/* 18:   */   {
/* 19:17 */     super(par1World);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public EntityLargeFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/* 23:   */   {
/* 24:22 */     super(par1World, par2, par4, par6, par8, par10, par12);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public EntityLargeFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
/* 28:   */   {
/* 29:27 */     super(par1World, par2EntityLivingBase, par3, par5, par7);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/* 33:   */   {
/* 34:35 */     if (!this.worldObj.isClient)
/* 35:   */     {
/* 36:37 */       if (par1MovingObjectPosition.entityHit != null) {
/* 37:39 */         par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
/* 38:   */       }
/* 39:42 */       this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.field_92057_e, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
/* 40:43 */       setDead();
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 45:   */   {
/* 46:52 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 47:53 */     par1NBTTagCompound.setInteger("ExplosionPower", this.field_92057_e);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 51:   */   {
/* 52:61 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 53:63 */     if (par1NBTTagCompound.func_150297_b("ExplosionPower", 99)) {
/* 54:65 */       this.field_92057_e = par1NBTTagCompound.getInteger("ExplosionPower");
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityLargeFireball
 * JD-Core Version:    0.7.0.1
 */