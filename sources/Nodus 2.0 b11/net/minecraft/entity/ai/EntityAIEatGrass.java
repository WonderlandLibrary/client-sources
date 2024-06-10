/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.entity.EntityLiving;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.pathfinding.PathNavigate;
/*   8:    */ import net.minecraft.util.MathHelper;
/*   9:    */ import net.minecraft.world.GameRules;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityAIEatGrass
/*  13:    */   extends EntityAIBase
/*  14:    */ {
/*  15:    */   private EntityLiving field_151500_b;
/*  16:    */   private World field_151501_c;
/*  17:    */   int field_151502_a;
/*  18:    */   private static final String __OBFID = "CL_00001582";
/*  19:    */   
/*  20:    */   public EntityAIEatGrass(EntityLiving p_i45314_1_)
/*  21:    */   {
/*  22: 18 */     this.field_151500_b = p_i45314_1_;
/*  23: 19 */     this.field_151501_c = p_i45314_1_.worldObj;
/*  24: 20 */     setMutexBits(7);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean shouldExecute()
/*  28:    */   {
/*  29: 28 */     if (this.field_151500_b.getRNG().nextInt(this.field_151500_b.isChild() ? 50 : 1000) != 0) {
/*  30: 30 */       return false;
/*  31:    */     }
/*  32: 34 */     int var1 = MathHelper.floor_double(this.field_151500_b.posX);
/*  33: 35 */     int var2 = MathHelper.floor_double(this.field_151500_b.posY);
/*  34: 36 */     int var3 = MathHelper.floor_double(this.field_151500_b.posZ);
/*  35: 37 */     return (this.field_151501_c.getBlock(var1, var2, var3) == Blocks.tallgrass) && (this.field_151501_c.getBlockMetadata(var1, var2, var3) == 1);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void startExecuting()
/*  39:    */   {
/*  40: 46 */     this.field_151502_a = 40;
/*  41: 47 */     this.field_151501_c.setEntityState(this.field_151500_b, (byte)10);
/*  42: 48 */     this.field_151500_b.getNavigator().clearPathEntity();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void resetTask()
/*  46:    */   {
/*  47: 56 */     this.field_151502_a = 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean continueExecuting()
/*  51:    */   {
/*  52: 64 */     return this.field_151502_a > 0;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int func_151499_f()
/*  56:    */   {
/*  57: 69 */     return this.field_151502_a;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void updateTask()
/*  61:    */   {
/*  62: 77 */     this.field_151502_a = Math.max(0, this.field_151502_a - 1);
/*  63: 79 */     if (this.field_151502_a == 4)
/*  64:    */     {
/*  65: 81 */       int var1 = MathHelper.floor_double(this.field_151500_b.posX);
/*  66: 82 */       int var2 = MathHelper.floor_double(this.field_151500_b.posY);
/*  67: 83 */       int var3 = MathHelper.floor_double(this.field_151500_b.posZ);
/*  68: 85 */       if (this.field_151501_c.getBlock(var1, var2, var3) == Blocks.tallgrass)
/*  69:    */       {
/*  70: 87 */         if (this.field_151501_c.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
/*  71: 89 */           this.field_151501_c.func_147480_a(var1, var2, var3, false);
/*  72:    */         }
/*  73: 92 */         this.field_151500_b.eatGrassBonus();
/*  74:    */       }
/*  75: 94 */       else if (this.field_151501_c.getBlock(var1, var2 - 1, var3) == Blocks.grass)
/*  76:    */       {
/*  77: 96 */         if (this.field_151501_c.getGameRules().getGameRuleBooleanValue("mobGriefing"))
/*  78:    */         {
/*  79: 98 */           this.field_151501_c.playAuxSFX(2001, var1, var2 - 1, var3, Block.getIdFromBlock(Blocks.grass));
/*  80: 99 */           this.field_151501_c.setBlock(var1, var2 - 1, var3, Blocks.dirt, 0, 2);
/*  81:    */         }
/*  82:102 */         this.field_151500_b.eatGrassBonus();
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIEatGrass
 * JD-Core Version:    0.7.0.1
 */