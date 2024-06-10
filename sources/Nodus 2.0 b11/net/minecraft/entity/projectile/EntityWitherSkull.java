/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.entity.DataWatcher;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.potion.Potion;
/*   9:    */ import net.minecraft.potion.PotionEffect;
/*  10:    */ import net.minecraft.util.DamageSource;
/*  11:    */ import net.minecraft.util.MovingObjectPosition;
/*  12:    */ import net.minecraft.world.EnumDifficulty;
/*  13:    */ import net.minecraft.world.Explosion;
/*  14:    */ import net.minecraft.world.GameRules;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class EntityWitherSkull
/*  18:    */   extends EntityFireball
/*  19:    */ {
/*  20:    */   private static final String __OBFID = "CL_00001728";
/*  21:    */   
/*  22:    */   public EntityWitherSkull(World par1World)
/*  23:    */   {
/*  24: 20 */     super(par1World);
/*  25: 21 */     setSize(0.3125F, 0.3125F);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public EntityWitherSkull(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
/*  29:    */   {
/*  30: 26 */     super(par1World, par2EntityLivingBase, par3, par5, par7);
/*  31: 27 */     setSize(0.3125F, 0.3125F);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected float getMotionFactor()
/*  35:    */   {
/*  36: 35 */     return isInvulnerable() ? 0.73F : super.getMotionFactor();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public EntityWitherSkull(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/*  40:    */   {
/*  41: 40 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  42: 41 */     setSize(0.3125F, 0.3125F);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isBurning()
/*  46:    */   {
/*  47: 49 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_)
/*  51:    */   {
/*  52: 54 */     float var7 = super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
/*  53: 56 */     if ((isInvulnerable()) && (p_145772_6_ != Blocks.bedrock) && (p_145772_6_ != Blocks.end_portal) && (p_145772_6_ != Blocks.end_portal_frame) && (p_145772_6_ != Blocks.command_block)) {
/*  54: 58 */       var7 = Math.min(0.8F, var7);
/*  55:    */     }
/*  56: 61 */     return var7;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/*  60:    */   {
/*  61: 69 */     if (!this.worldObj.isClient)
/*  62:    */     {
/*  63: 71 */       if (par1MovingObjectPosition.entityHit != null)
/*  64:    */       {
/*  65: 73 */         if (this.shootingEntity != null)
/*  66:    */         {
/*  67: 75 */           if ((par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F)) && (!par1MovingObjectPosition.entityHit.isEntityAlive())) {
/*  68: 77 */             this.shootingEntity.heal(5.0F);
/*  69:    */           }
/*  70:    */         }
/*  71:    */         else {
/*  72: 82 */           par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
/*  73:    */         }
/*  74: 85 */         if ((par1MovingObjectPosition.entityHit instanceof EntityLivingBase))
/*  75:    */         {
/*  76: 87 */           byte var2 = 0;
/*  77: 89 */           if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
/*  78: 91 */             var2 = 10;
/*  79: 93 */           } else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
/*  80: 95 */             var2 = 40;
/*  81:    */           }
/*  82: 98 */           if (var2 > 0) {
/*  83:100 */             ((EntityLivingBase)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
/*  84:    */           }
/*  85:    */         }
/*  86:    */       }
/*  87:105 */       this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
/*  88:106 */       setDead();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean canBeCollidedWith()
/*  93:    */   {
/*  94:115 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  98:    */   {
/*  99:123 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void entityInit()
/* 103:    */   {
/* 104:128 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isInvulnerable()
/* 108:    */   {
/* 109:136 */     return this.dataWatcher.getWatchableObjectByte(10) == 1;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setInvulnerable(boolean par1)
/* 113:    */   {
/* 114:144 */     this.dataWatcher.updateObject(10, Byte.valueOf((byte)(par1 ? 1 : 0)));
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntityWitherSkull
 * JD-Core Version:    0.7.0.1
 */