/*  1:   */ package net.minecraft.world.gen.feature;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.block.material.Material;
/*  6:   */ import net.minecraft.init.Blocks;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class WorldGenReed
/* 10:   */   extends WorldGenerator
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000429";
/* 13:   */   
/* 14:   */   public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
/* 15:   */   {
/* 16:14 */     for (int var6 = 0; var6 < 20; var6++)
/* 17:   */     {
/* 18:16 */       int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 19:17 */       int var8 = par4;
/* 20:18 */       int var9 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);
/* 21:20 */       if ((par1World.isAirBlock(var7, par4, var9)) && ((par1World.getBlock(var7 - 1, par4 - 1, var9).getMaterial() == Material.water) || (par1World.getBlock(var7 + 1, par4 - 1, var9).getMaterial() == Material.water) || (par1World.getBlock(var7, par4 - 1, var9 - 1).getMaterial() == Material.water) || (par1World.getBlock(var7, par4 - 1, var9 + 1).getMaterial() == Material.water)))
/* 22:   */       {
/* 23:22 */         int var10 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1);
/* 24:24 */         for (int var11 = 0; var11 < var10; var11++) {
/* 25:26 */           if (Blocks.reeds.canBlockStay(par1World, var7, var8 + var11, var9)) {
/* 26:28 */             par1World.setBlock(var7, var8 + var11, var9, Blocks.reeds, 0, 2);
/* 27:   */           }
/* 28:   */         }
/* 29:   */       }
/* 30:   */     }
/* 31:34 */     return true;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.feature.WorldGenReed
 * JD-Core Version:    0.7.0.1
 */