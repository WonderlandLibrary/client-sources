/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.EntityCreature;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.item.Item;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.pathfinding.PathNavigate;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class EntityAITempt
/*  11:    */   extends EntityAIBase
/*  12:    */ {
/*  13:    */   private EntityCreature temptedEntity;
/*  14:    */   private double field_75282_b;
/*  15:    */   private double targetX;
/*  16:    */   private double targetY;
/*  17:    */   private double targetZ;
/*  18:    */   private double field_75278_f;
/*  19:    */   private double field_75279_g;
/*  20:    */   private EntityPlayer temptingPlayer;
/*  21:    */   private int delayTemptCounter;
/*  22:    */   private boolean isRunning;
/*  23:    */   private Item field_151484_k;
/*  24:    */   private boolean scaredByPlayerMovement;
/*  25:    */   private boolean field_75286_m;
/*  26:    */   private static final String __OBFID = "CL_00001616";
/*  27:    */   
/*  28:    */   public EntityAITempt(EntityCreature p_i45316_1_, double p_i45316_2_, Item p_i45316_4_, boolean p_i45316_5_)
/*  29:    */   {
/*  30: 47 */     this.temptedEntity = p_i45316_1_;
/*  31: 48 */     this.field_75282_b = p_i45316_2_;
/*  32: 49 */     this.field_151484_k = p_i45316_4_;
/*  33: 50 */     this.scaredByPlayerMovement = p_i45316_5_;
/*  34: 51 */     setMutexBits(3);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean shouldExecute()
/*  38:    */   {
/*  39: 59 */     if (this.delayTemptCounter > 0)
/*  40:    */     {
/*  41: 61 */       this.delayTemptCounter -= 1;
/*  42: 62 */       return false;
/*  43:    */     }
/*  44: 66 */     this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0D);
/*  45: 68 */     if (this.temptingPlayer == null) {
/*  46: 70 */       return false;
/*  47:    */     }
/*  48: 74 */     ItemStack var1 = this.temptingPlayer.getCurrentEquippedItem();
/*  49: 75 */     return var1 != null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean continueExecuting()
/*  53:    */   {
/*  54: 85 */     if (this.scaredByPlayerMovement)
/*  55:    */     {
/*  56: 87 */       if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0D)
/*  57:    */       {
/*  58: 89 */         if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.01D) {
/*  59: 91 */           return false;
/*  60:    */         }
/*  61: 94 */         if ((Math.abs(this.temptingPlayer.rotationPitch - this.field_75278_f) > 5.0D) || (Math.abs(this.temptingPlayer.rotationYaw - this.field_75279_g) > 5.0D)) {
/*  62: 96 */           return false;
/*  63:    */         }
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67:101 */         this.targetX = this.temptingPlayer.posX;
/*  68:102 */         this.targetY = this.temptingPlayer.posY;
/*  69:103 */         this.targetZ = this.temptingPlayer.posZ;
/*  70:    */       }
/*  71:106 */       this.field_75278_f = this.temptingPlayer.rotationPitch;
/*  72:107 */       this.field_75279_g = this.temptingPlayer.rotationYaw;
/*  73:    */     }
/*  74:110 */     return shouldExecute();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void startExecuting()
/*  78:    */   {
/*  79:118 */     this.targetX = this.temptingPlayer.posX;
/*  80:119 */     this.targetY = this.temptingPlayer.posY;
/*  81:120 */     this.targetZ = this.temptingPlayer.posZ;
/*  82:121 */     this.isRunning = true;
/*  83:122 */     this.field_75286_m = this.temptedEntity.getNavigator().getAvoidsWater();
/*  84:123 */     this.temptedEntity.getNavigator().setAvoidsWater(false);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void resetTask()
/*  88:    */   {
/*  89:131 */     this.temptingPlayer = null;
/*  90:132 */     this.temptedEntity.getNavigator().clearPathEntity();
/*  91:133 */     this.delayTemptCounter = 100;
/*  92:134 */     this.isRunning = false;
/*  93:135 */     this.temptedEntity.getNavigator().setAvoidsWater(this.field_75286_m);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void updateTask()
/*  97:    */   {
/*  98:143 */     this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0F, this.temptedEntity.getVerticalFaceSpeed());
/*  99:145 */     if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25D) {
/* 100:147 */       this.temptedEntity.getNavigator().clearPathEntity();
/* 101:    */     } else {
/* 102:151 */       this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.field_75282_b);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean isRunning()
/* 107:    */   {
/* 108:160 */     return this.isRunning;
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAITempt
 * JD-Core Version:    0.7.0.1
 */