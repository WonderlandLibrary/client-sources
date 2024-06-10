/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockDoor;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.pathfinding.PathEntity;
/*   8:    */ import net.minecraft.pathfinding.PathNavigate;
/*   9:    */ import net.minecraft.pathfinding.PathPoint;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public abstract class EntityAIDoorInteract
/*  14:    */   extends EntityAIBase
/*  15:    */ {
/*  16:    */   protected EntityLiving theEntity;
/*  17:    */   protected int entityPosX;
/*  18:    */   protected int entityPosY;
/*  19:    */   protected int entityPosZ;
/*  20:    */   protected BlockDoor field_151504_e;
/*  21:    */   boolean hasStoppedDoorInteraction;
/*  22:    */   float entityPositionX;
/*  23:    */   float entityPositionZ;
/*  24:    */   private static final String __OBFID = "CL_00001581";
/*  25:    */   
/*  26:    */   public EntityAIDoorInteract(EntityLiving par1EntityLiving)
/*  27:    */   {
/*  28: 30 */     this.theEntity = par1EntityLiving;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean shouldExecute()
/*  32:    */   {
/*  33: 38 */     if (!this.theEntity.isCollidedHorizontally) {
/*  34: 40 */       return false;
/*  35:    */     }
/*  36: 44 */     PathNavigate var1 = this.theEntity.getNavigator();
/*  37: 45 */     PathEntity var2 = var1.getPath();
/*  38: 47 */     if ((var2 != null) && (!var2.isFinished()) && (var1.getCanBreakDoors()))
/*  39:    */     {
/*  40: 49 */       for (int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2, var2.getCurrentPathLength()); var3++)
/*  41:    */       {
/*  42: 51 */         PathPoint var4 = var2.getPathPointFromIndex(var3);
/*  43: 52 */         this.entityPosX = var4.xCoord;
/*  44: 53 */         this.entityPosY = (var4.yCoord + 1);
/*  45: 54 */         this.entityPosZ = var4.zCoord;
/*  46: 56 */         if (this.theEntity.getDistanceSq(this.entityPosX, this.theEntity.posY, this.entityPosZ) <= 2.25D)
/*  47:    */         {
/*  48: 58 */           this.field_151504_e = func_151503_a(this.entityPosX, this.entityPosY, this.entityPosZ);
/*  49: 60 */           if (this.field_151504_e != null) {
/*  50: 62 */             return true;
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54: 67 */       this.entityPosX = MathHelper.floor_double(this.theEntity.posX);
/*  55: 68 */       this.entityPosY = MathHelper.floor_double(this.theEntity.posY + 1.0D);
/*  56: 69 */       this.entityPosZ = MathHelper.floor_double(this.theEntity.posZ);
/*  57: 70 */       this.field_151504_e = func_151503_a(this.entityPosX, this.entityPosY, this.entityPosZ);
/*  58: 71 */       return this.field_151504_e != null;
/*  59:    */     }
/*  60: 75 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean continueExecuting()
/*  64:    */   {
/*  65: 85 */     return !this.hasStoppedDoorInteraction;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void startExecuting()
/*  69:    */   {
/*  70: 93 */     this.hasStoppedDoorInteraction = false;
/*  71: 94 */     this.entityPositionX = ((float)(this.entityPosX + 0.5F - this.theEntity.posX));
/*  72: 95 */     this.entityPositionZ = ((float)(this.entityPosZ + 0.5F - this.theEntity.posZ));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void updateTask()
/*  76:    */   {
/*  77:103 */     float var1 = (float)(this.entityPosX + 0.5F - this.theEntity.posX);
/*  78:104 */     float var2 = (float)(this.entityPosZ + 0.5F - this.theEntity.posZ);
/*  79:105 */     float var3 = this.entityPositionX * var1 + this.entityPositionZ * var2;
/*  80:107 */     if (var3 < 0.0F) {
/*  81:109 */       this.hasStoppedDoorInteraction = true;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private BlockDoor func_151503_a(int p_151503_1_, int p_151503_2_, int p_151503_3_)
/*  86:    */   {
/*  87:115 */     Block var4 = this.theEntity.worldObj.getBlock(p_151503_1_, p_151503_2_, p_151503_3_);
/*  88:116 */     return var4 != Blocks.wooden_door ? null : (BlockDoor)var4;
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIDoorInteract
 * JD-Core Version:    0.7.0.1
 */