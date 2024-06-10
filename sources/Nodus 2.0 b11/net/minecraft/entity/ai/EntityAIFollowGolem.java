/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.entity.monster.EntityIronGolem;
/*   7:    */ import net.minecraft.entity.passive.EntityVillager;
/*   8:    */ import net.minecraft.pathfinding.PathNavigate;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityAIFollowGolem
/*  13:    */   extends EntityAIBase
/*  14:    */ {
/*  15:    */   private EntityVillager theVillager;
/*  16:    */   private EntityIronGolem theGolem;
/*  17:    */   private int takeGolemRoseTick;
/*  18:    */   private boolean tookGolemRose;
/*  19:    */   private static final String __OBFID = "CL_00001615";
/*  20:    */   
/*  21:    */   public EntityAIFollowGolem(EntityVillager par1EntityVillager)
/*  22:    */   {
/*  23: 18 */     this.theVillager = par1EntityVillager;
/*  24: 19 */     setMutexBits(3);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean shouldExecute()
/*  28:    */   {
/*  29: 27 */     if (this.theVillager.getGrowingAge() >= 0) {
/*  30: 29 */       return false;
/*  31:    */     }
/*  32: 31 */     if (!this.theVillager.worldObj.isDaytime()) {
/*  33: 33 */       return false;
/*  34:    */     }
/*  35: 37 */     List var1 = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.boundingBox.expand(6.0D, 2.0D, 6.0D));
/*  36: 39 */     if (var1.isEmpty()) {
/*  37: 41 */       return false;
/*  38:    */     }
/*  39: 45 */     Iterator var2 = var1.iterator();
/*  40: 47 */     while (var2.hasNext())
/*  41:    */     {
/*  42: 49 */       EntityIronGolem var3 = (EntityIronGolem)var2.next();
/*  43: 51 */       if (var3.getHoldRoseTick() > 0)
/*  44:    */       {
/*  45: 53 */         this.theGolem = var3;
/*  46: 54 */         break;
/*  47:    */       }
/*  48:    */     }
/*  49: 58 */     return this.theGolem != null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean continueExecuting()
/*  53:    */   {
/*  54: 68 */     return this.theGolem.getHoldRoseTick() > 0;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void startExecuting()
/*  58:    */   {
/*  59: 76 */     this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
/*  60: 77 */     this.tookGolemRose = false;
/*  61: 78 */     this.theGolem.getNavigator().clearPathEntity();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void resetTask()
/*  65:    */   {
/*  66: 86 */     this.theGolem = null;
/*  67: 87 */     this.theVillager.getNavigator().clearPathEntity();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void updateTask()
/*  71:    */   {
/*  72: 95 */     this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0F, 30.0F);
/*  73: 97 */     if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick)
/*  74:    */     {
/*  75: 99 */       this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5D);
/*  76:100 */       this.tookGolemRose = true;
/*  77:    */     }
/*  78:103 */     if ((this.tookGolemRose) && (this.theVillager.getDistanceSqToEntity(this.theGolem) < 4.0D))
/*  79:    */     {
/*  80:105 */       this.theGolem.setHoldingRose(false);
/*  81:106 */       this.theVillager.getNavigator().clearPathEntity();
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIFollowGolem
 * JD-Core Version:    0.7.0.1
 */