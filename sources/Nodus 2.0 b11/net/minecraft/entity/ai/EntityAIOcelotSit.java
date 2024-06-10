/*   1:    */ package net.minecraft.entity.ai;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockBed;
/*   6:    */ import net.minecraft.entity.passive.EntityOcelot;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.pathfinding.PathNavigate;
/*   9:    */ import net.minecraft.tileentity.TileEntityChest;
/*  10:    */ import net.minecraft.world.World;
/*  11:    */ 
/*  12:    */ public class EntityAIOcelotSit
/*  13:    */   extends EntityAIBase
/*  14:    */ {
/*  15:    */   private final EntityOcelot field_151493_a;
/*  16:    */   private final double field_151491_b;
/*  17:    */   private int field_151492_c;
/*  18:    */   private int field_151489_d;
/*  19:    */   private int field_151490_e;
/*  20:    */   private int field_151487_f;
/*  21:    */   private int field_151488_g;
/*  22:    */   private int field_151494_h;
/*  23:    */   private static final String __OBFID = "CL_00001601";
/*  24:    */   
/*  25:    */   public EntityAIOcelotSit(EntityOcelot p_i45315_1_, double p_i45315_2_)
/*  26:    */   {
/*  27: 24 */     this.field_151493_a = p_i45315_1_;
/*  28: 25 */     this.field_151491_b = p_i45315_2_;
/*  29: 26 */     setMutexBits(5);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean shouldExecute()
/*  33:    */   {
/*  34: 34 */     return (this.field_151493_a.isTamed()) && (!this.field_151493_a.isSitting()) && (this.field_151493_a.getRNG().nextDouble() <= 0.006500000134110451D) && (func_151485_f());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean continueExecuting()
/*  38:    */   {
/*  39: 42 */     return (this.field_151492_c <= this.field_151490_e) && (this.field_151489_d <= 60) && (func_151486_a(this.field_151493_a.worldObj, this.field_151487_f, this.field_151488_g, this.field_151494_h));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void startExecuting()
/*  43:    */   {
/*  44: 50 */     this.field_151493_a.getNavigator().tryMoveToXYZ(this.field_151487_f + 0.5D, this.field_151488_g + 1, this.field_151494_h + 0.5D, this.field_151491_b);
/*  45: 51 */     this.field_151492_c = 0;
/*  46: 52 */     this.field_151489_d = 0;
/*  47: 53 */     this.field_151490_e = (this.field_151493_a.getRNG().nextInt(this.field_151493_a.getRNG().nextInt(1200) + 1200) + 1200);
/*  48: 54 */     this.field_151493_a.func_70907_r().setSitting(false);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void resetTask()
/*  52:    */   {
/*  53: 62 */     this.field_151493_a.setSitting(false);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void updateTask()
/*  57:    */   {
/*  58: 70 */     this.field_151492_c += 1;
/*  59: 71 */     this.field_151493_a.func_70907_r().setSitting(false);
/*  60: 73 */     if (this.field_151493_a.getDistanceSq(this.field_151487_f, this.field_151488_g + 1, this.field_151494_h) > 1.0D)
/*  61:    */     {
/*  62: 75 */       this.field_151493_a.setSitting(false);
/*  63: 76 */       this.field_151493_a.getNavigator().tryMoveToXYZ(this.field_151487_f + 0.5D, this.field_151488_g + 1, this.field_151494_h + 0.5D, this.field_151491_b);
/*  64: 77 */       this.field_151489_d += 1;
/*  65:    */     }
/*  66: 79 */     else if (!this.field_151493_a.isSitting())
/*  67:    */     {
/*  68: 81 */       this.field_151493_a.setSitting(true);
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72: 85 */       this.field_151489_d -= 1;
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   private boolean func_151485_f()
/*  77:    */   {
/*  78: 91 */     int var1 = (int)this.field_151493_a.posY;
/*  79: 92 */     double var2 = 2147483647.0D;
/*  80: 94 */     for (int var4 = (int)this.field_151493_a.posX - 8; var4 < this.field_151493_a.posX + 8.0D; var4++) {
/*  81: 96 */       for (int var5 = (int)this.field_151493_a.posZ - 8; var5 < this.field_151493_a.posZ + 8.0D; var5++) {
/*  82: 98 */         if ((func_151486_a(this.field_151493_a.worldObj, var4, var1, var5)) && (this.field_151493_a.worldObj.isAirBlock(var4, var1 + 1, var5)))
/*  83:    */         {
/*  84:100 */           double var6 = this.field_151493_a.getDistanceSq(var4, var1, var5);
/*  85:102 */           if (var6 < var2)
/*  86:    */           {
/*  87:104 */             this.field_151487_f = var4;
/*  88:105 */             this.field_151488_g = var1;
/*  89:106 */             this.field_151494_h = var5;
/*  90:107 */             var2 = var6;
/*  91:    */           }
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:113 */     return var2 < 2147483647.0D;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private boolean func_151486_a(World p_151486_1_, int p_151486_2_, int p_151486_3_, int p_151486_4_)
/*  99:    */   {
/* 100:118 */     Block var5 = p_151486_1_.getBlock(p_151486_2_, p_151486_3_, p_151486_4_);
/* 101:119 */     int var6 = p_151486_1_.getBlockMetadata(p_151486_2_, p_151486_3_, p_151486_4_);
/* 102:121 */     if (var5 == Blocks.chest)
/* 103:    */     {
/* 104:123 */       TileEntityChest var7 = (TileEntityChest)p_151486_1_.getTileEntity(p_151486_2_, p_151486_3_, p_151486_4_);
/* 105:125 */       if (var7.field_145987_o < 1) {
/* 106:127 */         return true;
/* 107:    */       }
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:132 */       if (var5 == Blocks.lit_furnace) {
/* 112:134 */         return true;
/* 113:    */       }
/* 114:137 */       if ((var5 == Blocks.bed) && (!BlockBed.func_149975_b(var6))) {
/* 115:139 */         return true;
/* 116:    */       }
/* 117:    */     }
/* 118:143 */     return false;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIOcelotSit
 * JD-Core Version:    0.7.0.1
 */