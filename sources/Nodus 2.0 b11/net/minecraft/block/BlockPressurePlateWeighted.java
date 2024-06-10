/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.material.Material;
/*  5:   */ import net.minecraft.entity.Entity;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class BlockPressurePlateWeighted
/* 10:   */   extends BlockBasePressurePlate
/* 11:   */ {
/* 12:   */   private final int field_150068_a;
/* 13:   */   private static final String __OBFID = "CL_00000334";
/* 14:   */   
/* 15:   */   protected BlockPressurePlateWeighted(String p_i45436_1_, Material p_i45436_2_, int p_i45436_3_)
/* 16:   */   {
/* 17:15 */     super(p_i45436_1_, p_i45436_2_);
/* 18:16 */     this.field_150068_a = p_i45436_3_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected int func_150065_e(World p_150065_1_, int p_150065_2_, int p_150065_3_, int p_150065_4_)
/* 22:   */   {
/* 23:21 */     int var5 = Math.min(p_150065_1_.getEntitiesWithinAABB(Entity.class, func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_)).size(), this.field_150068_a);
/* 24:23 */     if (var5 <= 0) {
/* 25:25 */       return 0;
/* 26:   */     }
/* 27:29 */     float var6 = Math.min(this.field_150068_a, var5) / this.field_150068_a;
/* 28:30 */     return MathHelper.ceiling_float_int(var6 * 15.0F);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected int func_150060_c(int p_150060_1_)
/* 32:   */   {
/* 33:36 */     return p_150060_1_;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected int func_150066_d(int p_150066_1_)
/* 37:   */   {
/* 38:41 */     return p_150066_1_;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int func_149738_a(World p_149738_1_)
/* 42:   */   {
/* 43:46 */     return 10;
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPressurePlateWeighted
 * JD-Core Version:    0.7.0.1
 */