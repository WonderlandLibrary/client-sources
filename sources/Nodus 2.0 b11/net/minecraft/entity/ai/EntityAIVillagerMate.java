/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.passive.EntityVillager;
/*   6:    */ import net.minecraft.pathfinding.PathNavigate;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.village.Village;
/*  10:    */ import net.minecraft.village.VillageCollection;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class EntityAIVillagerMate
/*  14:    */   extends EntityAIBase
/*  15:    */ {
/*  16:    */   private EntityVillager villagerObj;
/*  17:    */   private EntityVillager mate;
/*  18:    */   private World worldObj;
/*  19:    */   private int matingTimeout;
/*  20:    */   Village villageObj;
/*  21:    */   private static final String __OBFID = "CL_00001594";
/*  22:    */   
/*  23:    */   public EntityAIVillagerMate(EntityVillager par1EntityVillager)
/*  24:    */   {
/*  25: 20 */     this.villagerObj = par1EntityVillager;
/*  26: 21 */     this.worldObj = par1EntityVillager.worldObj;
/*  27: 22 */     setMutexBits(3);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean shouldExecute()
/*  31:    */   {
/*  32: 30 */     if (this.villagerObj.getGrowingAge() != 0) {
/*  33: 32 */       return false;
/*  34:    */     }
/*  35: 34 */     if (this.villagerObj.getRNG().nextInt(500) != 0) {
/*  36: 36 */       return false;
/*  37:    */     }
/*  38: 40 */     this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.villagerObj.posX), MathHelper.floor_double(this.villagerObj.posY), MathHelper.floor_double(this.villagerObj.posZ), 0);
/*  39: 42 */     if (this.villageObj == null) {
/*  40: 44 */       return false;
/*  41:    */     }
/*  42: 46 */     if (!checkSufficientDoorsPresentForNewVillager()) {
/*  43: 48 */       return false;
/*  44:    */     }
/*  45: 52 */     Entity var1 = this.worldObj.findNearestEntityWithinAABB(EntityVillager.class, this.villagerObj.boundingBox.expand(8.0D, 3.0D, 8.0D), this.villagerObj);
/*  46: 54 */     if (var1 == null) {
/*  47: 56 */       return false;
/*  48:    */     }
/*  49: 60 */     this.mate = ((EntityVillager)var1);
/*  50: 61 */     return this.mate.getGrowingAge() == 0;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void startExecuting()
/*  54:    */   {
/*  55: 72 */     this.matingTimeout = 300;
/*  56: 73 */     this.villagerObj.setMating(true);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void resetTask()
/*  60:    */   {
/*  61: 81 */     this.villageObj = null;
/*  62: 82 */     this.mate = null;
/*  63: 83 */     this.villagerObj.setMating(false);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean continueExecuting()
/*  67:    */   {
/*  68: 91 */     return (this.matingTimeout >= 0) && (checkSufficientDoorsPresentForNewVillager()) && (this.villagerObj.getGrowingAge() == 0);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void updateTask()
/*  72:    */   {
/*  73: 99 */     this.matingTimeout -= 1;
/*  74:100 */     this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);
/*  75:102 */     if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25D) {
/*  76:104 */       this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
/*  77:106 */     } else if ((this.matingTimeout == 0) && (this.mate.isMating())) {
/*  78:108 */       giveBirth();
/*  79:    */     }
/*  80:111 */     if (this.villagerObj.getRNG().nextInt(35) == 0) {
/*  81:113 */       this.worldObj.setEntityState(this.villagerObj, (byte)12);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private boolean checkSufficientDoorsPresentForNewVillager()
/*  86:    */   {
/*  87:119 */     if (!this.villageObj.isMatingSeason()) {
/*  88:121 */       return false;
/*  89:    */     }
/*  90:125 */     int var1 = (int)(this.villageObj.getNumVillageDoors() * 0.35D);
/*  91:126 */     return this.villageObj.getNumVillagers() < var1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void giveBirth()
/*  95:    */   {
/*  96:132 */     EntityVillager var1 = this.villagerObj.createChild(this.mate);
/*  97:133 */     this.mate.setGrowingAge(6000);
/*  98:134 */     this.villagerObj.setGrowingAge(6000);
/*  99:135 */     var1.setGrowingAge(-24000);
/* 100:136 */     var1.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
/* 101:137 */     this.worldObj.spawnEntityInWorld(var1);
/* 102:138 */     this.worldObj.setEntityState(var1, (byte)12);
/* 103:    */   }
/* 104:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIVillagerMate
 * JD-Core Version:    0.7.0.1
 */