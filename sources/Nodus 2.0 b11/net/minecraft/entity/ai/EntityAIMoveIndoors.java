/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.EntityCreature;
/*   5:    */ import net.minecraft.pathfinding.PathNavigate;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.util.Vec3;
/*   8:    */ import net.minecraft.util.Vec3Pool;
/*   9:    */ import net.minecraft.village.Village;
/*  10:    */ import net.minecraft.village.VillageCollection;
/*  11:    */ import net.minecraft.village.VillageDoorInfo;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.WorldProvider;
/*  14:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  15:    */ 
/*  16:    */ public class EntityAIMoveIndoors
/*  17:    */   extends EntityAIBase
/*  18:    */ {
/*  19:    */   private EntityCreature entityObj;
/*  20:    */   private VillageDoorInfo doorInfo;
/*  21: 13 */   private int insidePosX = -1;
/*  22: 14 */   private int insidePosZ = -1;
/*  23:    */   private static final String __OBFID = "CL_00001596";
/*  24:    */   
/*  25:    */   public EntityAIMoveIndoors(EntityCreature par1EntityCreature)
/*  26:    */   {
/*  27: 19 */     this.entityObj = par1EntityCreature;
/*  28: 20 */     setMutexBits(1);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean shouldExecute()
/*  32:    */   {
/*  33: 28 */     int var1 = MathHelper.floor_double(this.entityObj.posX);
/*  34: 29 */     int var2 = MathHelper.floor_double(this.entityObj.posY);
/*  35: 30 */     int var3 = MathHelper.floor_double(this.entityObj.posZ);
/*  36: 32 */     if (((!this.entityObj.worldObj.isDaytime()) || (this.entityObj.worldObj.isRaining()) || (!this.entityObj.worldObj.getBiomeGenForCoords(var1, var3).canSpawnLightningBolt())) && (!this.entityObj.worldObj.provider.hasNoSky))
/*  37:    */     {
/*  38: 34 */       if (this.entityObj.getRNG().nextInt(50) != 0) {
/*  39: 36 */         return false;
/*  40:    */       }
/*  41: 38 */       if ((this.insidePosX != -1) && (this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0D)) {
/*  42: 40 */         return false;
/*  43:    */       }
/*  44: 44 */       Village var4 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(var1, var2, var3, 14);
/*  45: 46 */       if (var4 == null) {
/*  46: 48 */         return false;
/*  47:    */       }
/*  48: 52 */       this.doorInfo = var4.findNearestDoorUnrestricted(var1, var2, var3);
/*  49: 53 */       return this.doorInfo != null;
/*  50:    */     }
/*  51: 59 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean continueExecuting()
/*  55:    */   {
/*  56: 68 */     return !this.entityObj.getNavigator().noPath();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void startExecuting()
/*  60:    */   {
/*  61: 76 */     this.insidePosX = -1;
/*  62: 78 */     if (this.entityObj.getDistanceSq(this.doorInfo.getInsidePosX(), this.doorInfo.posY, this.doorInfo.getInsidePosZ()) > 256.0D)
/*  63:    */     {
/*  64: 80 */       Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, this.entityObj.worldObj.getWorldVec3Pool().getVecFromPool(this.doorInfo.getInsidePosX() + 0.5D, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5D));
/*  65: 82 */       if (var1 != null) {
/*  66: 84 */         this.entityObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, 1.0D);
/*  67:    */       }
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 89 */       this.entityObj.getNavigator().tryMoveToXYZ(this.doorInfo.getInsidePosX() + 0.5D, this.doorInfo.getInsidePosY(), this.doorInfo.getInsidePosZ() + 0.5D, 1.0D);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void resetTask()
/*  76:    */   {
/*  77: 98 */     this.insidePosX = this.doorInfo.getInsidePosX();
/*  78: 99 */     this.insidePosZ = this.doorInfo.getInsidePosZ();
/*  79:100 */     this.doorInfo = null;
/*  80:    */   }
/*  81:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIMoveIndoors
 * JD-Core Version:    0.7.0.1
 */