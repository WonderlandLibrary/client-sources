/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.command.IEntitySelector;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityCreature;
/*   7:    */ import net.minecraft.entity.passive.EntityTameable;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.pathfinding.PathEntity;
/*  10:    */ import net.minecraft.pathfinding.PathNavigate;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.Vec3;
/*  13:    */ import net.minecraft.util.Vec3Pool;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class EntityAIAvoidEntity
/*  17:    */   extends EntityAIBase
/*  18:    */ {
/*  19: 15 */   public final IEntitySelector field_98218_a = new IEntitySelector()
/*  20:    */   {
/*  21:    */     private static final String __OBFID = "CL_00001575";
/*  22:    */     
/*  23:    */     public boolean isEntityApplicable(Entity par1Entity)
/*  24:    */     {
/*  25: 20 */       return (par1Entity.isEntityAlive()) && (EntityAIAvoidEntity.this.theEntity.getEntitySenses().canSee(par1Entity));
/*  26:    */     }
/*  27:    */   };
/*  28:    */   private EntityCreature theEntity;
/*  29:    */   private double farSpeed;
/*  30:    */   private double nearSpeed;
/*  31:    */   private Entity closestLivingEntity;
/*  32:    */   private float distanceFromEntity;
/*  33:    */   private PathEntity entityPathEntity;
/*  34:    */   private PathNavigate entityPathNavigate;
/*  35:    */   private Class targetEntityClass;
/*  36:    */   private static final String __OBFID = "CL_00001574";
/*  37:    */   
/*  38:    */   public EntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6)
/*  39:    */   {
/*  40: 43 */     this.theEntity = par1EntityCreature;
/*  41: 44 */     this.targetEntityClass = par2Class;
/*  42: 45 */     this.distanceFromEntity = par3;
/*  43: 46 */     this.farSpeed = par4;
/*  44: 47 */     this.nearSpeed = par6;
/*  45: 48 */     this.entityPathNavigate = par1EntityCreature.getNavigator();
/*  46: 49 */     setMutexBits(1);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean shouldExecute()
/*  50:    */   {
/*  51: 57 */     if (this.targetEntityClass == EntityPlayer.class)
/*  52:    */     {
/*  53: 59 */       if (((this.theEntity instanceof EntityTameable)) && (((EntityTameable)this.theEntity).isTamed())) {
/*  54: 61 */         return false;
/*  55:    */       }
/*  56: 64 */       this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, this.distanceFromEntity);
/*  57: 66 */       if (this.closestLivingEntity == null) {
/*  58: 68 */         return false;
/*  59:    */       }
/*  60:    */     }
/*  61:    */     else
/*  62:    */     {
/*  63: 73 */       List var1 = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand(this.distanceFromEntity, 3.0D, this.distanceFromEntity), this.field_98218_a);
/*  64: 75 */       if (var1.isEmpty()) {
/*  65: 77 */         return false;
/*  66:    */       }
/*  67: 80 */       this.closestLivingEntity = ((Entity)var1.get(0));
/*  68:    */     }
/*  69: 83 */     Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
/*  70: 85 */     if (var2 == null) {
/*  71: 87 */       return false;
/*  72:    */     }
/*  73: 89 */     if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
/*  74: 91 */       return false;
/*  75:    */     }
/*  76: 95 */     this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
/*  77: 96 */     return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(var2);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean continueExecuting()
/*  81:    */   {
/*  82:105 */     return !this.entityPathNavigate.noPath();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void startExecuting()
/*  86:    */   {
/*  87:113 */     this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void resetTask()
/*  91:    */   {
/*  92:121 */     this.closestLivingEntity = null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void updateTask()
/*  96:    */   {
/*  97:129 */     if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
/*  98:131 */       this.theEntity.getNavigator().setSpeed(this.nearSpeed);
/*  99:    */     } else {
/* 100:135 */       this.theEntity.getNavigator().setSpeed(this.farSpeed);
/* 101:    */     }
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIAvoidEntity
 * JD-Core Version:    0.7.0.1
 */