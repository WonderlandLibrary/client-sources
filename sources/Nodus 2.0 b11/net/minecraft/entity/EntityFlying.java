/*  1:   */ package net.minecraft.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.util.AxisAlignedBB;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public abstract class EntityFlying
/*  9:   */   extends EntityLiving
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00001545";
/* 12:   */   
/* 13:   */   public EntityFlying(World par1World)
/* 14:   */   {
/* 15:12 */     super(par1World);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void fall(float par1) {}
/* 19:   */   
/* 20:   */   protected void updateFallState(double par1, boolean par3) {}
/* 21:   */   
/* 22:   */   public void moveEntityWithHeading(float par1, float par2)
/* 23:   */   {
/* 24:31 */     if (isInWater())
/* 25:   */     {
/* 26:33 */       moveFlying(par1, par2, 0.02F);
/* 27:34 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 28:35 */       this.motionX *= 0.800000011920929D;
/* 29:36 */       this.motionY *= 0.800000011920929D;
/* 30:37 */       this.motionZ *= 0.800000011920929D;
/* 31:   */     }
/* 32:39 */     else if (handleLavaMovement())
/* 33:   */     {
/* 34:41 */       moveFlying(par1, par2, 0.02F);
/* 35:42 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 36:43 */       this.motionX *= 0.5D;
/* 37:44 */       this.motionY *= 0.5D;
/* 38:45 */       this.motionZ *= 0.5D;
/* 39:   */     }
/* 40:   */     else
/* 41:   */     {
/* 42:49 */       float var3 = 0.91F;
/* 43:51 */       if (this.onGround) {
/* 44:53 */         var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
/* 45:   */       }
/* 46:56 */       float var4 = 0.1627714F / (var3 * var3 * var3);
/* 47:57 */       moveFlying(par1, par2, this.onGround ? 0.1F * var4 : 0.02F);
/* 48:58 */       var3 = 0.91F;
/* 49:60 */       if (this.onGround) {
/* 50:62 */         var3 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
/* 51:   */       }
/* 52:65 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 53:66 */       this.motionX *= var3;
/* 54:67 */       this.motionY *= var3;
/* 55:68 */       this.motionZ *= var3;
/* 56:   */     }
/* 57:71 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 58:72 */     double var8 = this.posX - this.prevPosX;
/* 59:73 */     double var5 = this.posZ - this.prevPosZ;
/* 60:74 */     float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;
/* 61:76 */     if (var7 > 1.0F) {
/* 62:78 */       var7 = 1.0F;
/* 63:   */     }
/* 64:81 */     this.limbSwingAmount += (var7 - this.limbSwingAmount) * 0.4F;
/* 65:82 */     this.limbSwing += this.limbSwingAmount;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean isOnLadder()
/* 69:   */   {
/* 70:90 */     return false;
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityFlying
 * JD-Core Version:    0.7.0.1
 */