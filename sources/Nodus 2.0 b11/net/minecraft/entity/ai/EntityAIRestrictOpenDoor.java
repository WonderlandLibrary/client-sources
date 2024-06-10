/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.pathfinding.PathNavigate;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ import net.minecraft.village.Village;
/*  7:   */ import net.minecraft.village.VillageCollection;
/*  8:   */ import net.minecraft.village.VillageDoorInfo;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class EntityAIRestrictOpenDoor
/* 12:   */   extends EntityAIBase
/* 13:   */ {
/* 14:   */   private EntityCreature entityObj;
/* 15:   */   private VillageDoorInfo frontDoor;
/* 16:   */   private static final String __OBFID = "CL_00001610";
/* 17:   */   
/* 18:   */   public EntityAIRestrictOpenDoor(EntityCreature par1EntityCreature)
/* 19:   */   {
/* 20:16 */     this.entityObj = par1EntityCreature;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean shouldExecute()
/* 24:   */   {
/* 25:24 */     if (this.entityObj.worldObj.isDaytime()) {
/* 26:26 */       return false;
/* 27:   */     }
/* 28:30 */     Village var1 = this.entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ), 16);
/* 29:32 */     if (var1 == null) {
/* 30:34 */       return false;
/* 31:   */     }
/* 32:38 */     this.frontDoor = var1.findNearestDoor(MathHelper.floor_double(this.entityObj.posX), MathHelper.floor_double(this.entityObj.posY), MathHelper.floor_double(this.entityObj.posZ));
/* 33:39 */     return this.frontDoor != null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean continueExecuting()
/* 37:   */   {
/* 38:49 */     return !this.entityObj.worldObj.isDaytime();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void startExecuting()
/* 42:   */   {
/* 43:57 */     this.entityObj.getNavigator().setBreakDoors(false);
/* 44:58 */     this.entityObj.getNavigator().setEnterDoors(false);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void resetTask()
/* 48:   */   {
/* 49:66 */     this.entityObj.getNavigator().setBreakDoors(true);
/* 50:67 */     this.entityObj.getNavigator().setEnterDoors(true);
/* 51:68 */     this.frontDoor = null;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void updateTask()
/* 55:   */   {
/* 56:76 */     this.frontDoor.incrementDoorOpeningRestrictionCounter();
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIRestrictOpenDoor
 * JD-Core Version:    0.7.0.1
 */