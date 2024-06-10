/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.entity.passive.EntityAnimal;
/*   6:    */ import net.minecraft.pathfinding.PathNavigate;
/*   7:    */ import net.minecraft.util.AxisAlignedBB;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class EntityAIFollowParent
/*  11:    */   extends EntityAIBase
/*  12:    */ {
/*  13:    */   EntityAnimal childAnimal;
/*  14:    */   EntityAnimal parentAnimal;
/*  15:    */   double field_75347_c;
/*  16:    */   private int field_75345_d;
/*  17:    */   private static final String __OBFID = "CL_00001586";
/*  18:    */   
/*  19:    */   public EntityAIFollowParent(EntityAnimal par1EntityAnimal, double par2)
/*  20:    */   {
/*  21: 18 */     this.childAnimal = par1EntityAnimal;
/*  22: 19 */     this.field_75347_c = par2;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean shouldExecute()
/*  26:    */   {
/*  27: 27 */     if (this.childAnimal.getGrowingAge() >= 0) {
/*  28: 29 */       return false;
/*  29:    */     }
/*  30: 33 */     List var1 = this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.boundingBox.expand(8.0D, 4.0D, 8.0D));
/*  31: 34 */     EntityAnimal var2 = null;
/*  32: 35 */     double var3 = 1.7976931348623157E+308D;
/*  33: 36 */     Iterator var5 = var1.iterator();
/*  34: 38 */     while (var5.hasNext())
/*  35:    */     {
/*  36: 40 */       EntityAnimal var6 = (EntityAnimal)var5.next();
/*  37: 42 */       if (var6.getGrowingAge() >= 0)
/*  38:    */       {
/*  39: 44 */         double var7 = this.childAnimal.getDistanceSqToEntity(var6);
/*  40: 46 */         if (var7 <= var3)
/*  41:    */         {
/*  42: 48 */           var3 = var7;
/*  43: 49 */           var2 = var6;
/*  44:    */         }
/*  45:    */       }
/*  46:    */     }
/*  47: 54 */     if (var2 == null) {
/*  48: 56 */       return false;
/*  49:    */     }
/*  50: 58 */     if (var3 < 9.0D) {
/*  51: 60 */       return false;
/*  52:    */     }
/*  53: 64 */     this.parentAnimal = var2;
/*  54: 65 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean continueExecuting()
/*  58:    */   {
/*  59: 75 */     if (!this.parentAnimal.isEntityAlive()) {
/*  60: 77 */       return false;
/*  61:    */     }
/*  62: 81 */     double var1 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
/*  63: 82 */     return (var1 >= 9.0D) && (var1 <= 256.0D);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void startExecuting()
/*  67:    */   {
/*  68: 91 */     this.field_75345_d = 0;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void resetTask()
/*  72:    */   {
/*  73: 99 */     this.parentAnimal = null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void updateTask()
/*  77:    */   {
/*  78:107 */     if (--this.field_75345_d <= 0)
/*  79:    */     {
/*  80:109 */       this.field_75345_d = 10;
/*  81:110 */       this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.field_75347_c);
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIFollowParent
 * JD-Core Version:    0.7.0.1
 */