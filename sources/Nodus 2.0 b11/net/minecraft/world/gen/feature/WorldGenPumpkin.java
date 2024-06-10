/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.init.Blocks;
/*  6:   */ import net.minecraft.world.World;
/*  7:   */ 
/*  8:   */ public class WorldGenPumpkin
/*  9:   */   extends WorldGenerator
/* 10:   */ {
/* 11:   */   private static final String __OBFID = "CL_00000428";
/* 12:   */   
/* 13:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 14:   */   {
/* 15:13 */     for (int var6 = 0; var6 < 64; var6++)
/* 16:   */     {
/* 17:15 */       int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 18:16 */       int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 19:17 */       int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
/* 20:19 */       if ((par1World.isAirBlock(var7, var8, var9)) && (par1World.getBlock(var7, var8 - 1, var9) == Blocks.grass) && (Blocks.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))) {
/* 21:21 */         par1World.setBlock(var7, var8, var9, Blocks.pumpkin, par2Random.nextInt(4), 2);
/* 22:   */       }
/* 23:   */     }
/* 24:25 */     return true;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenPumpkin
 * JD-Core Version:    0.7.0.1
 */