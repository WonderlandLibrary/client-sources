/*   1:    */ package net.minecraft.entity.projectile;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.util.DamageSource;
/*   7:    */ import net.minecraft.util.MovingObjectPosition;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class EntitySmallFireball
/*  11:    */   extends EntityFireball
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00001721";
/*  14:    */   
/*  15:    */   public EntitySmallFireball(World par1World)
/*  16:    */   {
/*  17: 15 */     super(par1World);
/*  18: 16 */     setSize(0.3125F, 0.3125F);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public EntitySmallFireball(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
/*  22:    */   {
/*  23: 21 */     super(par1World, par2EntityLivingBase, par3, par5, par7);
/*  24: 22 */     setSize(0.3125F, 0.3125F);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public EntitySmallFireball(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
/*  28:    */   {
/*  29: 27 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*  30: 28 */     setSize(0.3125F, 0.3125F);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
/*  34:    */   {
/*  35: 36 */     if (!this.worldObj.isClient)
/*  36:    */     {
/*  37: 38 */       if (par1MovingObjectPosition.entityHit != null)
/*  38:    */       {
/*  39: 40 */         if ((!par1MovingObjectPosition.entityHit.isImmuneToFire()) && (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 5.0F))) {
/*  40: 42 */           par1MovingObjectPosition.entityHit.setFire(5);
/*  41:    */         }
/*  42:    */       }
/*  43:    */       else
/*  44:    */       {
/*  45: 47 */         int var2 = par1MovingObjectPosition.blockX;
/*  46: 48 */         int var3 = par1MovingObjectPosition.blockY;
/*  47: 49 */         int var4 = par1MovingObjectPosition.blockZ;
/*  48: 51 */         switch (par1MovingObjectPosition.sideHit)
/*  49:    */         {
/*  50:    */         case 0: 
/*  51: 54 */           var3--;
/*  52: 55 */           break;
/*  53:    */         case 1: 
/*  54: 58 */           var3++;
/*  55: 59 */           break;
/*  56:    */         case 2: 
/*  57: 62 */           var4--;
/*  58: 63 */           break;
/*  59:    */         case 3: 
/*  60: 66 */           var4++;
/*  61: 67 */           break;
/*  62:    */         case 4: 
/*  63: 70 */           var2--;
/*  64: 71 */           break;
/*  65:    */         case 5: 
/*  66: 74 */           var2++;
/*  67:    */         }
/*  68: 77 */         if (this.worldObj.isAirBlock(var2, var3, var4)) {
/*  69: 79 */           this.worldObj.setBlock(var2, var3, var4, Blocks.fire);
/*  70:    */         }
/*  71:    */       }
/*  72: 83 */       setDead();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean canBeCollidedWith()
/*  77:    */   {
/*  78: 92 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  82:    */   {
/*  83:100 */     return false;
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.projectile.EntitySmallFireball
 * JD-Core Version:    0.7.0.1
 */