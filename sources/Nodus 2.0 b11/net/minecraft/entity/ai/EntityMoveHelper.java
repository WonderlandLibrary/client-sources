/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ import net.minecraft.entity.SharedMonsterAttributes;
/*  5:   */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ 
/*  9:   */ public class EntityMoveHelper
/* 10:   */ {
/* 11:   */   private EntityLiving entity;
/* 12:   */   private double posX;
/* 13:   */   private double posY;
/* 14:   */   private double posZ;
/* 15:   */   private double speed;
/* 16:   */   private boolean update;
/* 17:   */   private static final String __OBFID = "CL_00001573";
/* 18:   */   
/* 19:   */   public EntityMoveHelper(EntityLiving par1EntityLiving)
/* 20:   */   {
/* 21:22 */     this.entity = par1EntityLiving;
/* 22:23 */     this.posX = par1EntityLiving.posX;
/* 23:24 */     this.posY = par1EntityLiving.posY;
/* 24:25 */     this.posZ = par1EntityLiving.posZ;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean isUpdating()
/* 28:   */   {
/* 29:30 */     return this.update;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public double getSpeed()
/* 33:   */   {
/* 34:35 */     return this.speed;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setMoveTo(double par1, double par3, double par5, double par7)
/* 38:   */   {
/* 39:43 */     this.posX = par1;
/* 40:44 */     this.posY = par3;
/* 41:45 */     this.posZ = par5;
/* 42:46 */     this.speed = par7;
/* 43:47 */     this.update = true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void onUpdateMoveHelper()
/* 47:   */   {
/* 48:52 */     this.entity.setMoveForward(0.0F);
/* 49:54 */     if (this.update)
/* 50:   */     {
/* 51:56 */       this.update = false;
/* 52:57 */       int var1 = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5D);
/* 53:58 */       double var2 = this.posX - this.entity.posX;
/* 54:59 */       double var4 = this.posZ - this.entity.posZ;
/* 55:60 */       double var6 = this.posY - var1;
/* 56:61 */       double var8 = var2 * var2 + var6 * var6 + var4 * var4;
/* 57:63 */       if (var8 >= 2.500000277905201E-007D)
/* 58:   */       {
/* 59:65 */         float var10 = (float)(Math.atan2(var4, var2) * 180.0D / 3.141592653589793D) - 90.0F;
/* 60:66 */         this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, var10, 30.0F);
/* 61:67 */         this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/* 62:69 */         if ((var6 > 0.0D) && (var2 * var2 + var4 * var4 < 1.0D)) {
/* 63:71 */           this.entity.getJumpHelper().setJumping();
/* 64:   */         }
/* 65:   */       }
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   private float limitAngle(float par1, float par2, float par3)
/* 70:   */   {
/* 71:82 */     float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
/* 72:84 */     if (var4 > par3) {
/* 73:86 */       var4 = par3;
/* 74:   */     }
/* 75:89 */     if (var4 < -par3) {
/* 76:91 */       var4 = -par3;
/* 77:   */     }
/* 78:94 */     return par1 + var4;
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityMoveHelper
 * JD-Core Version:    0.7.0.1
 */