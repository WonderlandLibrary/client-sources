/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.EntityCreature;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.entity.IEntityOwnable;
/*   7:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   8:    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  11:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  12:    */ import net.minecraft.pathfinding.PathEntity;
/*  13:    */ import net.minecraft.pathfinding.PathNavigate;
/*  14:    */ import net.minecraft.pathfinding.PathPoint;
/*  15:    */ import net.minecraft.server.management.ItemInWorldManager;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import org.apache.commons.lang3.StringUtils;
/*  18:    */ 
/*  19:    */ public abstract class EntityAITarget
/*  20:    */   extends EntityAIBase
/*  21:    */ {
/*  22:    */   protected EntityCreature taskOwner;
/*  23:    */   protected boolean shouldCheckSight;
/*  24:    */   private boolean nearbyOnly;
/*  25:    */   private int targetSearchStatus;
/*  26:    */   private int targetSearchDelay;
/*  27:    */   private int field_75298_g;
/*  28:    */   private static final String __OBFID = "CL_00001626";
/*  29:    */   
/*  30:    */   public EntityAITarget(EntityCreature par1EntityCreature, boolean par2)
/*  31:    */   {
/*  32: 44 */     this(par1EntityCreature, par2, false);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public EntityAITarget(EntityCreature par1EntityCreature, boolean par2, boolean par3)
/*  36:    */   {
/*  37: 49 */     this.taskOwner = par1EntityCreature;
/*  38: 50 */     this.shouldCheckSight = par2;
/*  39: 51 */     this.nearbyOnly = par3;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean continueExecuting()
/*  43:    */   {
/*  44: 59 */     EntityLivingBase var1 = this.taskOwner.getAttackTarget();
/*  45: 61 */     if (var1 == null) {
/*  46: 63 */       return false;
/*  47:    */     }
/*  48: 65 */     if (!var1.isEntityAlive()) {
/*  49: 67 */       return false;
/*  50:    */     }
/*  51: 71 */     double var2 = getTargetDistance();
/*  52: 73 */     if (this.taskOwner.getDistanceSqToEntity(var1) > var2 * var2) {
/*  53: 75 */       return false;
/*  54:    */     }
/*  55: 79 */     if (this.shouldCheckSight) {
/*  56: 81 */       if (this.taskOwner.getEntitySenses().canSee(var1)) {
/*  57: 83 */         this.field_75298_g = 0;
/*  58: 85 */       } else if (++this.field_75298_g > 60) {
/*  59: 87 */         return false;
/*  60:    */       }
/*  61:    */     }
/*  62: 91 */     return (!(var1 instanceof EntityPlayerMP)) || (!((EntityPlayerMP)var1).theItemInWorldManager.isCreative());
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected double getTargetDistance()
/*  66:    */   {
/*  67: 98 */     IAttributeInstance var1 = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
/*  68: 99 */     return var1 == null ? 16.0D : var1.getAttributeValue();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void startExecuting()
/*  72:    */   {
/*  73:107 */     this.targetSearchStatus = 0;
/*  74:108 */     this.targetSearchDelay = 0;
/*  75:109 */     this.field_75298_g = 0;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void resetTask()
/*  79:    */   {
/*  80:117 */     this.taskOwner.setAttackTarget(null);
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2)
/*  84:    */   {
/*  85:125 */     if (par1EntityLivingBase == null) {
/*  86:127 */       return false;
/*  87:    */     }
/*  88:129 */     if (par1EntityLivingBase == this.taskOwner) {
/*  89:131 */       return false;
/*  90:    */     }
/*  91:133 */     if (!par1EntityLivingBase.isEntityAlive()) {
/*  92:135 */       return false;
/*  93:    */     }
/*  94:137 */     if (!this.taskOwner.canAttackClass(par1EntityLivingBase.getClass())) {
/*  95:139 */       return false;
/*  96:    */     }
/*  97:143 */     if (((this.taskOwner instanceof IEntityOwnable)) && (StringUtils.isNotEmpty(((IEntityOwnable)this.taskOwner).getOwnerName())))
/*  98:    */     {
/*  99:145 */       if (((par1EntityLivingBase instanceof IEntityOwnable)) && (((IEntityOwnable)this.taskOwner).getOwnerName().equals(((IEntityOwnable)par1EntityLivingBase).getOwnerName()))) {
/* 100:147 */         return false;
/* 101:    */       }
/* 102:150 */       if (par1EntityLivingBase == ((IEntityOwnable)this.taskOwner).getOwner()) {
/* 103:152 */         return false;
/* 104:    */       }
/* 105:    */     }
/* 106:155 */     else if (((par1EntityLivingBase instanceof EntityPlayer)) && (!par2) && (((EntityPlayer)par1EntityLivingBase).capabilities.disableDamage))
/* 107:    */     {
/* 108:157 */       return false;
/* 109:    */     }
/* 110:160 */     if (!this.taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ))) {
/* 111:162 */       return false;
/* 112:    */     }
/* 113:164 */     if ((this.shouldCheckSight) && (!this.taskOwner.getEntitySenses().canSee(par1EntityLivingBase))) {
/* 114:166 */       return false;
/* 115:    */     }
/* 116:170 */     if (this.nearbyOnly)
/* 117:    */     {
/* 118:172 */       if (--this.targetSearchDelay <= 0) {
/* 119:174 */         this.targetSearchStatus = 0;
/* 120:    */       }
/* 121:177 */       if (this.targetSearchStatus == 0) {
/* 122:179 */         this.targetSearchStatus = (canEasilyReach(par1EntityLivingBase) ? 1 : 2);
/* 123:    */       }
/* 124:182 */       if (this.targetSearchStatus == 2) {
/* 125:184 */         return false;
/* 126:    */       }
/* 127:    */     }
/* 128:188 */     return true;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private boolean canEasilyReach(EntityLivingBase par1EntityLivingBase)
/* 132:    */   {
/* 133:198 */     this.targetSearchDelay = (10 + this.taskOwner.getRNG().nextInt(5));
/* 134:199 */     PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLivingBase);
/* 135:201 */     if (var2 == null) {
/* 136:203 */       return false;
/* 137:    */     }
/* 138:207 */     PathPoint var3 = var2.getFinalPathPoint();
/* 139:209 */     if (var3 == null) {
/* 140:211 */       return false;
/* 141:    */     }
/* 142:215 */     int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLivingBase.posX);
/* 143:216 */     int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLivingBase.posZ);
/* 144:217 */     return var4 * var4 + var5 * var5 <= 2.25D;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAITarget
 * JD-Core Version:    0.7.0.1
 */