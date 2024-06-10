/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.entity.EntityCreature;
/*   7:    */ import net.minecraft.pathfinding.PathEntity;
/*   8:    */ import net.minecraft.pathfinding.PathNavigate;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.util.Vec3;
/*  11:    */ import net.minecraft.util.Vec3Pool;
/*  12:    */ import net.minecraft.village.Village;
/*  13:    */ import net.minecraft.village.VillageCollection;
/*  14:    */ import net.minecraft.village.VillageDoorInfo;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class EntityAIMoveThroughVillage
/*  18:    */   extends EntityAIBase
/*  19:    */ {
/*  20:    */   private EntityCreature theEntity;
/*  21:    */   private double movementSpeed;
/*  22:    */   private PathEntity entityPathNavigate;
/*  23:    */   private VillageDoorInfo doorInfo;
/*  24:    */   private boolean isNocturnal;
/*  25: 22 */   private List doorList = new ArrayList();
/*  26:    */   private static final String __OBFID = "CL_00001597";
/*  27:    */   
/*  28:    */   public EntityAIMoveThroughVillage(EntityCreature par1EntityCreature, double par2, boolean par4)
/*  29:    */   {
/*  30: 27 */     this.theEntity = par1EntityCreature;
/*  31: 28 */     this.movementSpeed = par2;
/*  32: 29 */     this.isNocturnal = par4;
/*  33: 30 */     setMutexBits(1);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean shouldExecute()
/*  37:    */   {
/*  38: 38 */     func_75414_f();
/*  39: 40 */     if ((this.isNocturnal) && (this.theEntity.worldObj.isDaytime())) {
/*  40: 42 */       return false;
/*  41:    */     }
/*  42: 46 */     Village var1 = this.theEntity.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ), 0);
/*  43: 48 */     if (var1 == null) {
/*  44: 50 */       return false;
/*  45:    */     }
/*  46: 54 */     this.doorInfo = func_75412_a(var1);
/*  47: 56 */     if (this.doorInfo == null) {
/*  48: 58 */       return false;
/*  49:    */     }
/*  50: 62 */     boolean var2 = this.theEntity.getNavigator().getCanBreakDoors();
/*  51: 63 */     this.theEntity.getNavigator().setBreakDoors(false);
/*  52: 64 */     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ);
/*  53: 65 */     this.theEntity.getNavigator().setBreakDoors(var2);
/*  54: 67 */     if (this.entityPathNavigate != null) {
/*  55: 69 */       return true;
/*  56:    */     }
/*  57: 73 */     Vec3 var3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ));
/*  58: 75 */     if (var3 == null) {
/*  59: 77 */       return false;
/*  60:    */     }
/*  61: 81 */     this.theEntity.getNavigator().setBreakDoors(false);
/*  62: 82 */     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(var3.xCoord, var3.yCoord, var3.zCoord);
/*  63: 83 */     this.theEntity.getNavigator().setBreakDoors(var2);
/*  64: 84 */     return this.entityPathNavigate != null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean continueExecuting()
/*  68:    */   {
/*  69: 97 */     if (this.theEntity.getNavigator().noPath()) {
/*  70: 99 */       return false;
/*  71:    */     }
/*  72:103 */     float var1 = this.theEntity.width + 4.0F;
/*  73:104 */     return this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) > var1 * var1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void startExecuting()
/*  77:    */   {
/*  78:113 */     this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void resetTask()
/*  82:    */   {
/*  83:121 */     if ((this.theEntity.getNavigator().noPath()) || (this.theEntity.getDistanceSq(this.doorInfo.posX, this.doorInfo.posY, this.doorInfo.posZ) < 16.0D)) {
/*  84:123 */       this.doorList.add(this.doorInfo);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private VillageDoorInfo func_75412_a(Village par1Village)
/*  89:    */   {
/*  90:129 */     VillageDoorInfo var2 = null;
/*  91:130 */     int var3 = 2147483647;
/*  92:131 */     List var4 = par1Village.getVillageDoorInfoList();
/*  93:132 */     Iterator var5 = var4.iterator();
/*  94:134 */     while (var5.hasNext())
/*  95:    */     {
/*  96:136 */       VillageDoorInfo var6 = (VillageDoorInfo)var5.next();
/*  97:137 */       int var7 = var6.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
/*  98:139 */       if ((var7 < var3) && (!func_75413_a(var6)))
/*  99:    */       {
/* 100:141 */         var2 = var6;
/* 101:142 */         var3 = var7;
/* 102:    */       }
/* 103:    */     }
/* 104:146 */     return var2;
/* 105:    */   }
/* 106:    */   
/* 107:    */   private boolean func_75413_a(VillageDoorInfo par1VillageDoorInfo)
/* 108:    */   {
/* 109:151 */     Iterator var2 = this.doorList.iterator();
/* 110:    */     VillageDoorInfo var3;
/* 111:    */     do
/* 112:    */     {
/* 113:156 */       if (!var2.hasNext()) {
/* 114:158 */         return false;
/* 115:    */       }
/* 116:161 */       var3 = (VillageDoorInfo)var2.next();
/* 117:163 */     } while ((par1VillageDoorInfo.posX != var3.posX) || (par1VillageDoorInfo.posY != var3.posY) || (par1VillageDoorInfo.posZ != var3.posZ));
/* 118:165 */     return true;
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void func_75414_f()
/* 122:    */   {
/* 123:170 */     if (this.doorList.size() > 15) {
/* 124:172 */       this.doorList.remove(0);
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIMoveThroughVillage
 * JD-Core Version:    0.7.0.1
 */