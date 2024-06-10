/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.EntityLiving;
/*   4:    */ import net.minecraft.entity.EntityLivingBase;
/*   5:    */ import net.minecraft.entity.IRangedAttackMob;
/*   6:    */ import net.minecraft.pathfinding.PathNavigate;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ 
/*  10:    */ public class EntityAIArrowAttack
/*  11:    */   extends EntityAIBase
/*  12:    */ {
/*  13:    */   private final EntityLiving entityHost;
/*  14:    */   private final IRangedAttackMob rangedAttackEntityHost;
/*  15:    */   private EntityLivingBase attackTarget;
/*  16:    */   private int rangedAttackTime;
/*  17:    */   private double entityMoveSpeed;
/*  18:    */   private int field_75318_f;
/*  19:    */   private int field_96561_g;
/*  20:    */   private int maxRangedAttackTime;
/*  21:    */   private float field_96562_i;
/*  22:    */   private float field_82642_h;
/*  23:    */   private static final String __OBFID = "CL_00001609";
/*  24:    */   
/*  25:    */   public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, float par5)
/*  26:    */   {
/*  27: 38 */     this(par1IRangedAttackMob, par2, par4, par4, par5);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, double par2, int par4, int par5, float par6)
/*  31:    */   {
/*  32: 43 */     this.rangedAttackTime = -1;
/*  33: 45 */     if (!(par1IRangedAttackMob instanceof EntityLivingBase)) {
/*  34: 47 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*  35:    */     }
/*  36: 51 */     this.rangedAttackEntityHost = par1IRangedAttackMob;
/*  37: 52 */     this.entityHost = ((EntityLiving)par1IRangedAttackMob);
/*  38: 53 */     this.entityMoveSpeed = par2;
/*  39: 54 */     this.field_96561_g = par4;
/*  40: 55 */     this.maxRangedAttackTime = par5;
/*  41: 56 */     this.field_96562_i = par6;
/*  42: 57 */     this.field_82642_h = (par6 * par6);
/*  43: 58 */     setMutexBits(3);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean shouldExecute()
/*  47:    */   {
/*  48: 67 */     EntityLivingBase var1 = this.entityHost.getAttackTarget();
/*  49: 69 */     if (var1 == null) {
/*  50: 71 */       return false;
/*  51:    */     }
/*  52: 75 */     this.attackTarget = var1;
/*  53: 76 */     return true;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean continueExecuting()
/*  57:    */   {
/*  58: 85 */     return (shouldExecute()) || (!this.entityHost.getNavigator().noPath());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void resetTask()
/*  62:    */   {
/*  63: 93 */     this.attackTarget = null;
/*  64: 94 */     this.field_75318_f = 0;
/*  65: 95 */     this.rangedAttackTime = -1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void updateTask()
/*  69:    */   {
/*  70:103 */     double var1 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
/*  71:104 */     boolean var3 = this.entityHost.getEntitySenses().canSee(this.attackTarget);
/*  72:106 */     if (var3) {
/*  73:108 */       this.field_75318_f += 1;
/*  74:    */     } else {
/*  75:112 */       this.field_75318_f = 0;
/*  76:    */     }
/*  77:115 */     if ((var1 <= this.field_82642_h) && (this.field_75318_f >= 20)) {
/*  78:117 */       this.entityHost.getNavigator().clearPathEntity();
/*  79:    */     } else {
/*  80:121 */       this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
/*  81:    */     }
/*  82:124 */     this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
/*  83:127 */     if (--this.rangedAttackTime == 0)
/*  84:    */     {
/*  85:129 */       if ((var1 > this.field_82642_h) || (!var3)) {
/*  86:131 */         return;
/*  87:    */       }
/*  88:134 */       float var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
/*  89:135 */       float var5 = var4;
/*  90:137 */       if (var4 < 0.1F) {
/*  91:139 */         var5 = 0.1F;
/*  92:    */       }
/*  93:142 */       if (var5 > 1.0F) {
/*  94:144 */         var5 = 1.0F;
/*  95:    */       }
/*  96:147 */       this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, var5);
/*  97:148 */       this.rangedAttackTime = MathHelper.floor_float(var4 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/*  98:    */     }
/*  99:150 */     else if (this.rangedAttackTime < 0)
/* 100:    */     {
/* 101:152 */       float var4 = MathHelper.sqrt_double(var1) / this.field_96562_i;
/* 102:153 */       this.rangedAttackTime = MathHelper.floor_float(var4 * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
/* 103:    */     }
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIArrowAttack
 * JD-Core Version:    0.7.0.1
 */