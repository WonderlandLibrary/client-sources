/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.entity.EntityLiving;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.pathfinding.PathNavigate;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ 
/*  10:    */ public class EntityLookHelper
/*  11:    */ {
/*  12:    */   private EntityLiving entity;
/*  13:    */   private float deltaLookYaw;
/*  14:    */   private float deltaLookPitch;
/*  15:    */   private boolean isLooking;
/*  16:    */   private double posX;
/*  17:    */   private double posY;
/*  18:    */   private double posZ;
/*  19:    */   private static final String __OBFID = "CL_00001572";
/*  20:    */   
/*  21:    */   public EntityLookHelper(EntityLiving par1EntityLiving)
/*  22:    */   {
/*  23: 31 */     this.entity = par1EntityLiving;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setLookPositionWithEntity(Entity par1Entity, float par2, float par3)
/*  27:    */   {
/*  28: 39 */     this.posX = par1Entity.posX;
/*  29: 41 */     if ((par1Entity instanceof EntityLivingBase)) {
/*  30: 43 */       this.posY = (par1Entity.posY + par1Entity.getEyeHeight());
/*  31:    */     } else {
/*  32: 47 */       this.posY = ((par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D);
/*  33:    */     }
/*  34: 50 */     this.posZ = par1Entity.posZ;
/*  35: 51 */     this.deltaLookYaw = par2;
/*  36: 52 */     this.deltaLookPitch = par3;
/*  37: 53 */     this.isLooking = true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setLookPosition(double par1, double par3, double par5, float par7, float par8)
/*  41:    */   {
/*  42: 61 */     this.posX = par1;
/*  43: 62 */     this.posY = par3;
/*  44: 63 */     this.posZ = par5;
/*  45: 64 */     this.deltaLookYaw = par7;
/*  46: 65 */     this.deltaLookPitch = par8;
/*  47: 66 */     this.isLooking = true;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void onUpdateLook()
/*  51:    */   {
/*  52: 74 */     this.entity.rotationPitch = 0.0F;
/*  53: 76 */     if (this.isLooking)
/*  54:    */     {
/*  55: 78 */       this.isLooking = false;
/*  56: 79 */       double var1 = this.posX - this.entity.posX;
/*  57: 80 */       double var3 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
/*  58: 81 */       double var5 = this.posZ - this.entity.posZ;
/*  59: 82 */       double var7 = MathHelper.sqrt_double(var1 * var1 + var5 * var5);
/*  60: 83 */       float var9 = (float)(Math.atan2(var5, var1) * 180.0D / 3.141592653589793D) - 90.0F;
/*  61: 84 */       float var10 = (float)-(Math.atan2(var3, var7) * 180.0D / 3.141592653589793D);
/*  62: 85 */       this.entity.rotationPitch = updateRotation(this.entity.rotationPitch, var10, this.deltaLookPitch);
/*  63: 86 */       this.entity.rotationYawHead = updateRotation(this.entity.rotationYawHead, var9, this.deltaLookYaw);
/*  64:    */     }
/*  65:    */     else
/*  66:    */     {
/*  67: 90 */       this.entity.rotationYawHead = updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
/*  68:    */     }
/*  69: 93 */     float var11 = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
/*  70: 95 */     if (!this.entity.getNavigator().noPath())
/*  71:    */     {
/*  72: 97 */       if (var11 < -75.0F) {
/*  73: 99 */         this.entity.rotationYawHead = (this.entity.renderYawOffset - 75.0F);
/*  74:    */       }
/*  75:102 */       if (var11 > 75.0F) {
/*  76:104 */         this.entity.rotationYawHead = (this.entity.renderYawOffset + 75.0F);
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   private float updateRotation(float par1, float par2, float par3)
/*  82:    */   {
/*  83:111 */     float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
/*  84:113 */     if (var4 > par3) {
/*  85:115 */       var4 = par3;
/*  86:    */     }
/*  87:118 */     if (var4 < -par3) {
/*  88:120 */       var4 = -par3;
/*  89:    */     }
/*  90:123 */     return par1 + var4;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityLookHelper
 * JD-Core Version:    0.7.0.1
 */