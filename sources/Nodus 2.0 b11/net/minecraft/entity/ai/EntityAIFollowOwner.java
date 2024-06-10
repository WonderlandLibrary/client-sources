/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.passive.EntityTameable;
/*   6:    */ import net.minecraft.pathfinding.PathNavigate;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class EntityAIFollowOwner
/*  12:    */   extends EntityAIBase
/*  13:    */ {
/*  14:    */   private EntityTameable thePet;
/*  15:    */   private EntityLivingBase theOwner;
/*  16:    */   World theWorld;
/*  17:    */   private double field_75336_f;
/*  18:    */   private PathNavigate petPathfinder;
/*  19:    */   private int field_75343_h;
/*  20:    */   float maxDist;
/*  21:    */   float minDist;
/*  22:    */   private boolean field_75344_i;
/*  23:    */   private static final String __OBFID = "CL_00001585";
/*  24:    */   
/*  25:    */   public EntityAIFollowOwner(EntityTameable par1EntityTameable, double par2, float par4, float par5)
/*  26:    */   {
/*  27: 24 */     this.thePet = par1EntityTameable;
/*  28: 25 */     this.theWorld = par1EntityTameable.worldObj;
/*  29: 26 */     this.field_75336_f = par2;
/*  30: 27 */     this.petPathfinder = par1EntityTameable.getNavigator();
/*  31: 28 */     this.minDist = par4;
/*  32: 29 */     this.maxDist = par5;
/*  33: 30 */     setMutexBits(3);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean shouldExecute()
/*  37:    */   {
/*  38: 38 */     EntityLivingBase var1 = this.thePet.getOwner();
/*  39: 40 */     if (var1 == null) {
/*  40: 42 */       return false;
/*  41:    */     }
/*  42: 44 */     if (this.thePet.isSitting()) {
/*  43: 46 */       return false;
/*  44:    */     }
/*  45: 48 */     if (this.thePet.getDistanceSqToEntity(var1) < this.minDist * this.minDist) {
/*  46: 50 */       return false;
/*  47:    */     }
/*  48: 54 */     this.theOwner = var1;
/*  49: 55 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean continueExecuting()
/*  53:    */   {
/*  54: 64 */     return (!this.petPathfinder.noPath()) && (this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist) && (!this.thePet.isSitting());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void startExecuting()
/*  58:    */   {
/*  59: 72 */     this.field_75343_h = 0;
/*  60: 73 */     this.field_75344_i = this.thePet.getNavigator().getAvoidsWater();
/*  61: 74 */     this.thePet.getNavigator().setAvoidsWater(false);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void resetTask()
/*  65:    */   {
/*  66: 82 */     this.theOwner = null;
/*  67: 83 */     this.petPathfinder.clearPathEntity();
/*  68: 84 */     this.thePet.getNavigator().setAvoidsWater(this.field_75344_i);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void updateTask()
/*  72:    */   {
/*  73: 92 */     this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
/*  74: 94 */     if (!this.thePet.isSitting()) {
/*  75: 96 */       if (--this.field_75343_h <= 0)
/*  76:    */       {
/*  77: 98 */         this.field_75343_h = 10;
/*  78:100 */         if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.field_75336_f)) {
/*  79:102 */           if (!this.thePet.getLeashed()) {
/*  80:104 */             if (this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0D)
/*  81:    */             {
/*  82:106 */               int var1 = MathHelper.floor_double(this.theOwner.posX) - 2;
/*  83:107 */               int var2 = MathHelper.floor_double(this.theOwner.posZ) - 2;
/*  84:108 */               int var3 = MathHelper.floor_double(this.theOwner.boundingBox.minY);
/*  85:110 */               for (int var4 = 0; var4 <= 4; var4++) {
/*  86:112 */                 for (int var5 = 0; var5 <= 4; var5++) {
/*  87:114 */                   if (((var4 < 1) || (var5 < 1) || (var4 > 3) || (var5 > 3)) && (World.doesBlockHaveSolidTopSurface(this.theWorld, var1 + var4, var3 - 1, var2 + var5)) && (!this.theWorld.getBlock(var1 + var4, var3, var2 + var5).isNormalCube()) && (!this.theWorld.getBlock(var1 + var4, var3 + 1, var2 + var5).isNormalCube()))
/*  88:    */                   {
/*  89:116 */                     this.thePet.setLocationAndAngles(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
/*  90:117 */                     this.petPathfinder.clearPathEntity();
/*  91:118 */                     return;
/*  92:    */                   }
/*  93:    */                 }
/*  94:    */               }
/*  95:    */             }
/*  96:    */           }
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIFollowOwner
 * JD-Core Version:    0.7.0.1
 */