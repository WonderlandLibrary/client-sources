/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.EntityCreature;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.pathfinding.PathEntity;
/*   7:    */ import net.minecraft.pathfinding.PathNavigate;
/*   8:    */ import net.minecraft.util.AxisAlignedBB;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityAIAttackOnCollide
/*  13:    */   extends EntityAIBase
/*  14:    */ {
/*  15:    */   World worldObj;
/*  16:    */   EntityCreature attacker;
/*  17:    */   int attackTick;
/*  18:    */   double speedTowardsTarget;
/*  19:    */   boolean longMemory;
/*  20:    */   PathEntity entityPathEntity;
/*  21:    */   Class classTarget;
/*  22:    */   private int field_75445_i;
/*  23:    */   private double field_151497_i;
/*  24:    */   private double field_151495_j;
/*  25:    */   private double field_151496_k;
/*  26:    */   private static final String __OBFID = "CL_00001595";
/*  27:    */   
/*  28:    */   public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, Class par2Class, double par3, boolean par5)
/*  29:    */   {
/*  30: 38 */     this(par1EntityCreature, par3, par5);
/*  31: 39 */     this.classTarget = par2Class;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, double par2, boolean par4)
/*  35:    */   {
/*  36: 44 */     this.attacker = par1EntityCreature;
/*  37: 45 */     this.worldObj = par1EntityCreature.worldObj;
/*  38: 46 */     this.speedTowardsTarget = par2;
/*  39: 47 */     this.longMemory = par4;
/*  40: 48 */     setMutexBits(3);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean shouldExecute()
/*  44:    */   {
/*  45: 56 */     EntityLivingBase var1 = this.attacker.getAttackTarget();
/*  46: 58 */     if (var1 == null) {
/*  47: 60 */       return false;
/*  48:    */     }
/*  49: 62 */     if (!var1.isEntityAlive()) {
/*  50: 64 */       return false;
/*  51:    */     }
/*  52: 66 */     if ((this.classTarget != null) && (!this.classTarget.isAssignableFrom(var1.getClass()))) {
/*  53: 68 */       return false;
/*  54:    */     }
/*  55: 72 */     this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(var1);
/*  56: 73 */     return this.entityPathEntity != null;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean continueExecuting()
/*  60:    */   {
/*  61: 82 */     EntityLivingBase var1 = this.attacker.getAttackTarget();
/*  62: 83 */     return !this.longMemory ? true : this.attacker.getNavigator().noPath() ? false : !var1.isEntityAlive() ? false : var1 == null ? false : this.attacker.isWithinHomeDistance(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void startExecuting()
/*  66:    */   {
/*  67: 91 */     this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
/*  68: 92 */     this.field_75445_i = 0;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void resetTask()
/*  72:    */   {
/*  73:100 */     this.attacker.getNavigator().clearPathEntity();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void updateTask()
/*  77:    */   {
/*  78:108 */     EntityLivingBase var1 = this.attacker.getAttackTarget();
/*  79:109 */     this.attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
/*  80:110 */     double var2 = this.attacker.getDistanceSq(var1.posX, var1.boundingBox.minY, var1.posZ);
/*  81:111 */     double var4 = this.attacker.width * 2.0F * this.attacker.width * 2.0F + var1.width;
/*  82:112 */     this.field_75445_i -= 1;
/*  83:114 */     if (((this.longMemory) || (this.attacker.getEntitySenses().canSee(var1))) && (this.field_75445_i <= 0) && (((this.field_151497_i == 0.0D) && (this.field_151495_j == 0.0D) && (this.field_151496_k == 0.0D)) || (var1.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D) || (this.attacker.getRNG().nextFloat() < 0.05F)))
/*  84:    */     {
/*  85:116 */       this.field_151497_i = var1.posX;
/*  86:117 */       this.field_151495_j = var1.boundingBox.minY;
/*  87:118 */       this.field_151496_k = var1.posZ;
/*  88:119 */       this.field_75445_i = (4 + this.attacker.getRNG().nextInt(7));
/*  89:121 */       if (var2 > 1024.0D) {
/*  90:123 */         this.field_75445_i += 10;
/*  91:125 */       } else if (var2 > 256.0D) {
/*  92:127 */         this.field_75445_i += 5;
/*  93:    */       }
/*  94:130 */       if (!this.attacker.getNavigator().tryMoveToEntityLiving(var1, this.speedTowardsTarget)) {
/*  95:132 */         this.field_75445_i += 15;
/*  96:    */       }
/*  97:    */     }
/*  98:136 */     this.attackTick = Math.max(this.attackTick - 1, 0);
/*  99:138 */     if ((var2 <= var4) && (this.attackTick <= 20))
/* 100:    */     {
/* 101:140 */       this.attackTick = 20;
/* 102:142 */       if (this.attacker.getHeldItem() != null) {
/* 103:144 */         this.attacker.swingItem();
/* 104:    */       }
/* 105:147 */       this.attacker.attackEntityAsMob(var1);
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIAttackOnCollide
 * JD-Core Version:    0.7.0.1
 */