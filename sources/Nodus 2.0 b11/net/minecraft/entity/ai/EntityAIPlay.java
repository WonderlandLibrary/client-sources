/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.passive.EntityVillager;
/*   8:    */ import net.minecraft.pathfinding.PathNavigate;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.Vec3;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class EntityAIPlay
/*  14:    */   extends EntityAIBase
/*  15:    */ {
/*  16:    */   private EntityVillager villagerObj;
/*  17:    */   private EntityLivingBase targetVillager;
/*  18:    */   private double field_75261_c;
/*  19:    */   private int playTime;
/*  20:    */   private static final String __OBFID = "CL_00001605";
/*  21:    */   
/*  22:    */   public EntityAIPlay(EntityVillager par1EntityVillager, double par2)
/*  23:    */   {
/*  24: 19 */     this.villagerObj = par1EntityVillager;
/*  25: 20 */     this.field_75261_c = par2;
/*  26: 21 */     setMutexBits(1);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean shouldExecute()
/*  30:    */   {
/*  31: 29 */     if (this.villagerObj.getGrowingAge() >= 0) {
/*  32: 31 */       return false;
/*  33:    */     }
/*  34: 33 */     if (this.villagerObj.getRNG().nextInt(400) != 0) {
/*  35: 35 */       return false;
/*  36:    */     }
/*  37: 39 */     List var1 = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.boundingBox.expand(6.0D, 3.0D, 6.0D));
/*  38: 40 */     double var2 = 1.7976931348623157E+308D;
/*  39: 41 */     Iterator var4 = var1.iterator();
/*  40: 43 */     while (var4.hasNext())
/*  41:    */     {
/*  42: 45 */       EntityVillager var5 = (EntityVillager)var4.next();
/*  43: 47 */       if ((var5 != this.villagerObj) && (!var5.isPlaying()) && (var5.getGrowingAge() < 0))
/*  44:    */       {
/*  45: 49 */         double var6 = var5.getDistanceSqToEntity(this.villagerObj);
/*  46: 51 */         if (var6 <= var2)
/*  47:    */         {
/*  48: 53 */           var2 = var6;
/*  49: 54 */           this.targetVillager = var5;
/*  50:    */         }
/*  51:    */       }
/*  52:    */     }
/*  53: 59 */     if (this.targetVillager == null)
/*  54:    */     {
/*  55: 61 */       Vec3 var8 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
/*  56: 63 */       if (var8 == null) {
/*  57: 65 */         return false;
/*  58:    */       }
/*  59:    */     }
/*  60: 69 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean continueExecuting()
/*  64:    */   {
/*  65: 78 */     return this.playTime > 0;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void startExecuting()
/*  69:    */   {
/*  70: 86 */     if (this.targetVillager != null) {
/*  71: 88 */       this.villagerObj.setPlaying(true);
/*  72:    */     }
/*  73: 91 */     this.playTime = 1000;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void resetTask()
/*  77:    */   {
/*  78: 99 */     this.villagerObj.setPlaying(false);
/*  79:100 */     this.targetVillager = null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void updateTask()
/*  83:    */   {
/*  84:108 */     this.playTime -= 1;
/*  85:110 */     if (this.targetVillager != null)
/*  86:    */     {
/*  87:112 */       if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0D) {
/*  88:114 */         this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.field_75261_c);
/*  89:    */       }
/*  90:    */     }
/*  91:117 */     else if (this.villagerObj.getNavigator().noPath())
/*  92:    */     {
/*  93:119 */       Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);
/*  94:121 */       if (var1 == null) {
/*  95:123 */         return;
/*  96:    */       }
/*  97:126 */       this.villagerObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, this.field_75261_c);
/*  98:    */     }
/*  99:    */   }
/* 100:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIPlay
 * JD-Core Version:    0.7.0.1
 */