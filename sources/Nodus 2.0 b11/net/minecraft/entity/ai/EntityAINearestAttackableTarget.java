/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Random;
/*   7:    */ import net.minecraft.command.IEntitySelector;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityCreature;
/*  10:    */ import net.minecraft.entity.EntityLivingBase;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class EntityAINearestAttackableTarget
/*  15:    */   extends EntityAITarget
/*  16:    */ {
/*  17:    */   private final Class targetClass;
/*  18:    */   private final int targetChance;
/*  19:    */   private final Sorter theNearestAttackableTargetSorter;
/*  20:    */   private final IEntitySelector targetEntitySelector;
/*  21:    */   private EntityLivingBase targetEntity;
/*  22:    */   private static final String __OBFID = "CL_00001620";
/*  23:    */   
/*  24:    */   public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
/*  25:    */   {
/*  26: 29 */     this(par1EntityCreature, par2Class, par3, par4, false);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
/*  30:    */   {
/*  31: 34 */     this(par1EntityCreature, par2Class, par3, par4, par5, null);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, final IEntitySelector par6IEntitySelector)
/*  35:    */   {
/*  36: 39 */     super(par1EntityCreature, par4, par5);
/*  37: 40 */     this.targetClass = par2Class;
/*  38: 41 */     this.targetChance = par3;
/*  39: 42 */     this.theNearestAttackableTargetSorter = new Sorter(par1EntityCreature);
/*  40: 43 */     setMutexBits(1);
/*  41: 44 */     this.targetEntitySelector = new IEntitySelector()
/*  42:    */     {
/*  43:    */       private static final String __OBFID = "CL_00001621";
/*  44:    */       
/*  45:    */       public boolean isEntityApplicable(Entity par1Entity)
/*  46:    */       {
/*  47: 49 */         return (par6IEntitySelector != null) && (!par6IEntitySelector.isEntityApplicable(par1Entity)) ? false : !(par1Entity instanceof EntityLivingBase) ? false : EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)par1Entity, false);
/*  48:    */       }
/*  49:    */     };
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean shouldExecute()
/*  53:    */   {
/*  54: 59 */     if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(this.targetChance) != 0)) {
/*  55: 61 */       return false;
/*  56:    */     }
/*  57: 65 */     double var1 = getTargetDistance();
/*  58: 66 */     List var3 = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(var1, 4.0D, var1), this.targetEntitySelector);
/*  59: 67 */     Collections.sort(var3, this.theNearestAttackableTargetSorter);
/*  60: 69 */     if (var3.isEmpty()) {
/*  61: 71 */       return false;
/*  62:    */     }
/*  63: 75 */     this.targetEntity = ((EntityLivingBase)var3.get(0));
/*  64: 76 */     return true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void startExecuting()
/*  68:    */   {
/*  69: 86 */     this.taskOwner.setAttackTarget(this.targetEntity);
/*  70: 87 */     super.startExecuting();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static class Sorter
/*  74:    */     implements Comparator
/*  75:    */   {
/*  76:    */     private final Entity theEntity;
/*  77:    */     private static final String __OBFID = "CL_00001622";
/*  78:    */     
/*  79:    */     public Sorter(Entity par1Entity)
/*  80:    */     {
/*  81: 97 */       this.theEntity = par1Entity;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public int compare(Entity par1Entity, Entity par2Entity)
/*  85:    */     {
/*  86:102 */       double var3 = this.theEntity.getDistanceSqToEntity(par1Entity);
/*  87:103 */       double var5 = this.theEntity.getDistanceSqToEntity(par2Entity);
/*  88:104 */       return var3 > var5 ? 1 : var3 < var5 ? -1 : 0;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public int compare(Object par1Obj, Object par2Obj)
/*  92:    */     {
/*  93:109 */       return compare((Entity)par1Obj, (Entity)par2Obj);
/*  94:    */     }
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAINearestAttackableTarget
 * JD-Core Version:    0.7.0.1
 */