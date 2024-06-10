/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.BlockDoublePlant;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ import net.minecraft.world.WorldProvider;
/*  8:   */ 
/*  9:   */ public class WorldGenDoublePlant
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private int field_150549_a;
/* 13:   */   private static final String __OBFID = "CL_00000408";
/* 14:   */   
/* 15:   */   public void func_150548_a(int p_150548_1_)
/* 16:   */   {
/* 17:14 */     this.field_150549_a = p_150548_1_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 21:   */   {
/* 22:19 */     boolean var6 = false;
/* 23:21 */     for (int var7 = 0; var7 < 64; var7++)
/* 24:   */     {
/* 25:23 */       int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 26:24 */       int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 27:25 */       int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 28:27 */       if ((par1World.isAirBlock(var8, var9, var10)) && ((!par1World.provider.hasNoSky) || (var9 < 254)) && (Blocks.double_plant.canPlaceBlockAt(par1World, var8, var9, var10)))
/* 29:   */       {
/* 30:29 */         Blocks.double_plant.func_149889_c(par1World, var8, var9, var10, this.field_150549_a, 2);
/* 31:30 */         var6 = true;
/* 32:   */       }
/* 33:   */     }
/* 34:34 */     return var6;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenDoublePlant
 * JD-Core Version:    0.7.0.1
 */